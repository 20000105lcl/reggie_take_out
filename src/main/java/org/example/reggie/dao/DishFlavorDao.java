package org.example.reggie.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.reggie.entity.DishFlavor;

/**
 * @description:
 * @Title: DishFlavorDao
 * @Author: 刘成龙
 * @Version:1.0
 * @time: 2022/11/02 19:38
 */
@Mapper
public interface DishFlavorDao extends BaseMapper<DishFlavor> {
}
