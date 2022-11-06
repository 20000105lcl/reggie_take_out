package org.example.reggie.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.reggie.entity.ShoppingCart;

/**
 * @description:
 * @Title: ShoppingCartDao
 * @Author: 刘成龙
 * @Version:1.0
 * @time: 2022/11/05 15:22
 */
@Mapper
public interface ShoppingCartDao extends BaseMapper<ShoppingCart> {
}
