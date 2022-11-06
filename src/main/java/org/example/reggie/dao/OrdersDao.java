package org.example.reggie.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.reggie.entity.Orders;

/**
 * @description:
 * @Title: OrdersDao
 * @Author: 刘成龙
 * @Version:1.0
 * @time: 2022/11/04 20:07
 */
@Mapper
public interface OrdersDao extends BaseMapper<Orders> {
}
