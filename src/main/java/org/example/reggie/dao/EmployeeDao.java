package org.example.reggie.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.reggie.entity.Employee;

/**
 * @Title: EmployeeDao
 * @Author: 刘成龙
 * @Version:1.0
 * @time: 2022/10/30 17:05
 * @description:
 */
@Mapper
public interface EmployeeDao extends BaseMapper<Employee> {
}
