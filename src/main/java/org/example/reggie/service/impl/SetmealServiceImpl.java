package org.example.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.example.reggie.common.CustomException;
import org.example.reggie.dao.SetmealDao;
import org.example.reggie.dto.SetmealDto;
import org.example.reggie.entity.Setmeal;
import org.example.reggie.entity.SetmealDish;
import org.example.reggie.service.SetmealDishService;
import org.example.reggie.service.SetmealService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yaml.snakeyaml.events.Event;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Title: SetmealServiceImpl
 * @Author: 刘成龙
 * @Version:1.0
 * @time: 2022/11/01 21:53
 * @description:
 */
@Service
@Slf4j
public class SetmealServiceImpl extends ServiceImpl<SetmealDao, Setmeal> implements SetmealService {

    @Autowired
    private SetmealDishService setmealDishService;

    /**
     * 新增套餐，保存套餐信息以及套餐中的菜品信息
     * @param setmealDto
     */
    @Override
    @Transactional
    public void saveSetmealWithDish(SetmealDto setmealDto) {

        //保存套餐基本信息
        this.save(setmealDto);

        //保存套餐与菜品关系的信息
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();

        setmealDishes.stream().map((item)->{
            item.setSetmealId(setmealDto.getId());
            return item;
        }).collect(Collectors.toList());

        setmealDishService.saveBatch(setmealDishes);

    }

    /**
     * 删除套餐，并删除套餐关联的套餐与菜品关联数据信息
     * @param ids
     */
    @Override
    @Transactional
    public void deleteSetmealWithDish(List<Long> ids) {

        //select count(*) from setmeal where id in(...) and status = 1;
        // 查询套餐的状态信息，判断是否可以删除（启售的不能删除）
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(Setmeal::getId,ids);
        queryWrapper.eq(Setmeal::getStatus,1);

        long count = this.count(queryWrapper);

        log.info("查询套餐信息数：{}",count);


        //如果不能删除，则抛出一个业务异常
        if (count>0){
            throw new CustomException("当前要删除的套餐正在起售。不能删除！");
        }

        //如果可以删除，先删除套餐表中数据
        this.removeBatchByIds(ids);

        //删除套餐-菜品关系表(删除当前套餐关联的套餐-菜品信息)
        LambdaQueryWrapper<SetmealDish> queryWrapper1 = new LambdaQueryWrapper<>();
        queryWrapper1.in(SetmealDish::getSetmealId,ids);
        setmealDishService.remove(queryWrapper1);


    }

    /**
     * 更新套餐信息--套餐基本信息-setmeal表，套餐关联的菜品信息-setmeal_dish表
     * @param setmealDto
     */
    @Override
    @Transactional
    public void updateSetmealWithDish(SetmealDto setmealDto) {

        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDto,setmeal);

        //1、更新套餐基本信息 -- 更新setmeal表
        this.updateById(setmeal);

        //2、删除原始的关联菜品信息 -- 删除 - setmeal_dish表
        LambdaQueryWrapper<SetmealDish> queryWrapper1 = new LambdaQueryWrapper<>();
        queryWrapper1.eq(SetmealDish::getSetmealId, setmeal.getId());
        setmealDishService.remove(queryWrapper1);

        //3、新增更新后的关联菜品表
        List<SetmealDish> setmealDishList = setmealDto.getSetmealDishes();//新的菜品列表

        //3.1把套餐的id设置到关联菜品表信息中
        setmealDishList.stream().map((item) -> {
            item.setSetmealId(setmeal.getId());
            return item;
        }).collect(Collectors.toList());

        //3.2、将关联菜品表信息添加到setmael_dish表中
        setmealDishService.saveBatch(setmealDishList);

    }
}
