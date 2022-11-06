package org.example.reggie.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.reggie.entity.User;

/**
 * @description:
 * @Title: UserDao
 * @Author: 刘成龙
 * @Version:1.0
 * @time: 2022/11/05 09:54
 */
@Mapper
public interface UserDao extends BaseMapper<User> {
}
