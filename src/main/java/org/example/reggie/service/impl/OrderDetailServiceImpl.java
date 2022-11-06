package org.example.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.reggie.dao.OrderDetailDao;
import org.example.reggie.entity.OrderDetail;
import org.example.reggie.service.OrderDetailService;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @Title: OrderDetailServiceImpl
 * @Author: 刘成龙
 * @Version:1.0
 * @time: 2022/11/05 16:36
 */
@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailDao, OrderDetail> implements OrderDetailService {

}
