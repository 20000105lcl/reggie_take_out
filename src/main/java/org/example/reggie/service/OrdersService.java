package org.example.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.reggie.entity.Orders;

/**
 * @description:
 * @Title: OrdersService
 * @Author: 刘成龙
 * @Version:1.0
 * @time: 2022/11/04 20:08
 */
public interface OrdersService extends IService<Orders> {

    /**
     * 用户下单
     * @param orders
     */
    void submit(Orders orders);

}
