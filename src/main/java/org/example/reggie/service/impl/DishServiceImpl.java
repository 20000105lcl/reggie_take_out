package org.example.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.reggie.common.CustomException;
import org.example.reggie.dao.DishDao;
import org.example.reggie.dto.DishDto;
import org.example.reggie.entity.Dish;
import org.example.reggie.entity.DishFlavor;
import org.example.reggie.entity.Setmeal;
import org.example.reggie.entity.SetmealDish;
import org.example.reggie.service.DishFlavorService;
import org.example.reggie.service.DishService;
import org.example.reggie.service.SetmealDishService;
import org.example.reggie.service.SetmealService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Title: DishServiceImpl
 * @Author: 刘成龙
 * @Version:1.0
 * @time: 2022/11/01 21:51
 * @description:
 */
@Service
public class DishServiceImpl extends ServiceImpl<DishDao, Dish> implements DishService {

    @Autowired
    private DishFlavorService dishFlavorService;

    @Autowired
    private SetmealDishService setmealDishService;

    @Autowired
    private SetmealService setmealService;

    /**
     * 新增菜品，同时保存口味数据
     * @param dishDto
     */
    @Override
    @Transactional //由于要进行多次数据库操作，所以要添加事务管理
    public void saveDishWithFlavor(DishDto dishDto) {

        //保存菜品的基本信息
        this.save(dishDto);

        Long dishId = dishDto.getId();//菜品id

        //菜品口味
        List<DishFlavor> flavors = dishDto.getFlavors();

        //将id加入到每一种口味中
        flavors = flavors.stream().map((item)->{
            item.setDishId(dishId);
            return item;
        }).collect(Collectors.toList());

        //保存菜品口味数据到菜品口味表
        dishFlavorService.saveBatch(flavors);

    }

    /**
     * 根据id查询菜品信息和口味信息
     * @param id
     * @return
     */
    @Override
    public DishDto getByIdWithFlavor(Long id) {

        Dish dish = this.getById(id);

        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId,id);

        List<DishFlavor> flavors = dishFlavorService.list(queryWrapper);

        DishDto dishDto = new DishDto();

        BeanUtils.copyProperties(dish,dishDto);

        dishDto.setFlavors(flavors);

        return dishDto;
    }

    /**
     * 更新菜品信息和口味信息
     * @param dishDto
     */
    @Override
    @Transactional
    public void updateDishWithFlavor(DishDto dishDto) {

        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDto,dish);
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Dish::getId,dish.getId());

        //上面几行可简写为下面一行，因为DishDao时Dish的子类，只会更新父类有的字段。
        //this.updateById(dishDto,null);

        //更新菜品信息
        this.update(dish,queryWrapper);

        List<DishFlavor> flavors = dishDto.getFlavors(); //口味信息

        LambdaQueryWrapper<DishFlavor> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DishFlavor::getDishId,dish.getId());

        //更新口味信息 --先清理当前菜品的口味信息，在提交新的口味信息
        dishFlavorService.remove(wrapper);

        Long dishId = dish.getId();

        //将id加入到每一种口味中
        flavors = flavors.stream().map((item)->{
            item.setDishId(dishId);
            return item;
        }).collect(Collectors.toList());

        dishFlavorService.saveBatch(flavors);

    }

    /**
     * 删除菜品信息，判断是否停售，判断是否关联套餐
     * @param ids
     */
    @Override
    @Transactional
    public void delete(List<Long> ids) {

        //1.1、查询需要删除的菜品集合，判断是否停售
        LambdaQueryWrapper<Dish> queryWrapper1 = new LambdaQueryWrapper<>();
        queryWrapper1.in(Dish::getId,ids); //根据id查询
        queryWrapper1.eq(Dish::getStatus,1);//查询未停售的
        List<Dish> dishList = this.list(queryWrapper1);

        //1.2、当查询的菜品信息中数据>0，则说明存在菜品未停售，抛出业务异常
        if (dishList.size()>0){
            throw new CustomException("要删除的菜品正在火热销售！");
        }

        //2.1、判断是否关联套餐，查询菜品-套餐关系表（stemeal_dish表）判断是否存在包含id的套餐关系
        LambdaQueryWrapper<SetmealDish> queryWrapper2 = new LambdaQueryWrapper<>();
        queryWrapper2.in(SetmealDish::getDishId,ids);
        List<SetmealDish> setmealDishList = setmealDishService.list(queryWrapper2);

        //2.2、当查询到的菜品-套餐关系表数据>0，则说明菜品关联了套餐。
        if (setmealDishList.size()>0){
            throw new CustomException("要删除的菜品关联了套餐，不能删除！");
        }

        //3、正常删除
        this.removeBatchByIds(ids);

    }

    /**
     * 根据id起售与停售菜品 -- （当停售菜品时，判断对应的套餐是否是停售）
     * @param status
     * @param ids
     */
    @Override
    @Transactional
    public void updateStatus(int status, List<Long> ids) {

        //根据id查询菜品信息
        List<Dish> dishList = this.listByIds(ids);

        //修改菜品的状态
        dishList.stream().map((item) -> {
            item.setStatus(status);
            return item;
        }).collect(Collectors.toList());

        //修改菜品表数据
        this.updateBatchById(dishList);

        //当停售菜品时，判断对应的套餐是否是停售
        if (status==0){
            //查询当前菜品关联的套餐信息
            LambdaQueryWrapper<SetmealDish> queryWrapper1 = new LambdaQueryWrapper<>();
            //1、添加菜品id条件
            queryWrapper1.in(SetmealDish::getDishId,ids);
            //2、查询关联的套餐
            List<SetmealDish> list = setmealDishService.list(queryWrapper1);
            //3、去出关联套餐的id
            List<Long> setmealIds = list.stream().map((item) -> item.getSetmealId()).collect(Collectors.toList());
            //添加条件查询，查询对应套餐是否停售
            LambdaQueryWrapper<Setmeal> queryWrapper2 = new LambdaQueryWrapper<>();
            //4、添加id查询条件
            queryWrapper2.in(Setmeal::getId,ids);
            //5、添加状态查询条件，查询启售的套餐，status=1
            queryWrapper2.eq(Setmeal::getStatus,1);
            //6、查询
            List<Setmeal> setmealList = setmealService.list(queryWrapper2);
            //当查询的数据>0，说明要停售的菜品关联的套餐正在销售
            if (setmealList.size()>0){
                throw new CustomException("当前需要停售的菜品关联的套餐正在火热销售，不能停售！");
            }
        }
    }
}
