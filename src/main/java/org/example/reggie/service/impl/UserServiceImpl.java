package org.example.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.reggie.dao.UserDao;
import org.example.reggie.entity.User;
import org.example.reggie.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @Title: UserServiceImpl
 * @Author: 刘成龙
 * @Version:1.0
 * @time: 2022/11/05 09:56
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {

}
