package org.example.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.example.reggie.common.R;
import org.example.reggie.entity.Orders;
import org.example.reggie.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * @description:
 * @Title: OrdersController
 * @Author: 刘成龙
 * @Version:1.0
 * @time: 2022/11/04 20:10
 */
@RestController
@Slf4j
@RequestMapping("/order")
public class OrdersController {

    @Autowired
    private OrdersService ordersService;

    /**
     * 分页查询
     * @param page
     * @param pageSize
     * @param number
     * @param beginTime
     * @param endTime
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, Long number, String beginTime, String endTime){
        //1、创建分页表信息对象
        Page<Orders> pageInfo = new Page<>(page,pageSize);
        //2、根据条件查询
        LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper<>();
        //2.1、添加订单号查询条件
        queryWrapper.eq(number!=null,Orders::getNumber,number);
        //2.2、添加订单时间条件
        queryWrapper.between(beginTime!=null&&endTime!=null,Orders::getOrderTime,beginTime,endTime);
        //3、分页查询
        ordersService.page(pageInfo,queryWrapper);
        return R.success(pageInfo);
    }

    /**
     * 用户下单
     * @param orders
     * @return
     */
    @PostMapping("/submit")
    public R<String> submit(@RequestBody Orders orders){
        ordersService.submit(orders);
        return R.success("下单成功！");
    }

    /**
     *设置快捷
     * @return
     */
    @GetMapping
    public R<String> upload(){


        return null;
    }



}
