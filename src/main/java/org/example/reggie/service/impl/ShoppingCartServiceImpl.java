package org.example.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.reggie.dao.ShoppingCartDao;
import org.example.reggie.entity.ShoppingCart;
import org.example.reggie.service.ShoppingCartService;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @Title: ShoppingCartServiceImpl
 * @Author: 刘成龙
 * @Version:1.0
 * @time: 2022/11/05 15:23
 */
@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartDao, ShoppingCart> implements ShoppingCartService {
}
