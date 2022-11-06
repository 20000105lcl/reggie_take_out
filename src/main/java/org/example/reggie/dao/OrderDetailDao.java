package org.example.reggie.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.reggie.entity.OrderDetail;

/**
 * @description:
 * @Title: OrderDetailDao
 * @Author: 刘成龙
 * @Version:1.0
 * @time: 2022/11/05 16:34
 */
@Mapper
public interface OrderDetailDao extends BaseMapper<OrderDetail> {
}
