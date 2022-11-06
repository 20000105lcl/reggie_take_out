package org.example.reggie.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.reggie.entity.SetmealDish;

/**
 * @description: 套餐-菜品 关系表
 * @Title: SetmealDishDao
 * @Author: 刘成龙
 * @Version:1.0
 * @time: 2022/11/03 20:16
 */
@Mapper
public interface SetmealDishDao extends BaseMapper<SetmealDish> {
}
