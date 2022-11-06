package org.example.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.reggie.dao.EmployeeDao;
import org.example.reggie.entity.Employee;
import org.example.reggie.service.EmployeeService;
import org.springframework.stereotype.Service;

/**
 * @Title: EmployeeServiceImpl
 * @Author: 刘成龙
 * @Version:1.0
 * @time: 2022/10/30 17:07
 * @description:
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeDao, Employee> implements EmployeeService {
}
