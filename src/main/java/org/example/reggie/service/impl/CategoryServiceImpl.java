package org.example.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.reggie.common.CustomException;
import org.example.reggie.dao.CategoryDao;
import org.example.reggie.entity.Category;
import org.example.reggie.entity.Dish;
import org.example.reggie.entity.Setmeal;
import org.example.reggie.service.CategoryService;
import org.example.reggie.service.DishService;
import org.example.reggie.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Title: CategoryServiceImpl
 * @Author: 刘成龙
 * @Version:1.0
 * @time: 2022/11/01 20:02
 * @description:
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, Category> implements CategoryService {

    @Autowired
    private DishService dishService;
    @Autowired
    private SetmealService setmealService;

    /**
     * 根据id删除，要先判断是否关联了菜品或套餐
     * @param id
     */
    @Override
    public void remove(Long id) {

        //添加查询条件，根据ID进行查询
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dishLambdaQueryWrapper.eq(Dish::getCategoryId,id);
        int count1 = (int) dishService.count(dishLambdaQueryWrapper);
        //查询当前分类是否关联了菜品
        if (count1 > 0){
            //当前分类关联了菜品，抛出一个业务异常
            throw new CustomException("当前分类下关联了菜品，不能删除！");
        }

        //添加查询条件，根据ID进行查询
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId,id);
        int count2 = (int) setmealService.count(setmealLambdaQueryWrapper);
        if (count2 > 0){
            //当前分类已经关联了套餐，抛出一个业务异常，不能删除
            throw new CustomException("当前分类下关联了套餐，不能删除！");
        }

        super.removeById(id);


    }
}
