package org.example.reggie.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.reggie.entity.Dish;

/**
 * @Title: DishDao
 * @Author: 刘成龙
 * @Version:1.0
 * @time: 2022/11/01 21:49
 * @description:
 */
@Mapper
public interface DishDao extends BaseMapper<Dish> {
}
