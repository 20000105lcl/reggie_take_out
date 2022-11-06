package org.example.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.example.reggie.common.R;
import org.example.reggie.entity.Employee;
import org.example.reggie.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * @Title: EmployeeController
 * @Author: 刘成龙
 * @Version:1.0
 * @time: 2022/10/30 17:09
 * @description:
 */
@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    /**
     * 员工登录
     * @param request
     * @param employee
     * @return
     */
    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee){

        //对密码进行md5加密
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());

        //根据页面提交的用户名查询用户
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername,employee.getUsername());
        Employee emp = employeeService.getOne(queryWrapper);

        //如果没有查到用户返回登录失败
        if(emp==null){
            return R.error("登陆失败！");
        }

        //密码比对，如果不一致返回登陆失败
//        System.out.println("前端发来的密码："+password);
//        System.out.println("数据库查到的密码："+emp.getPassword());
        if(!emp.getPassword().equals(password)){
            return R.error("登录失败！");
        }

        //查看员工状态，是否被禁用
        if (emp.getStatus()==0){
            return R.error("账号已禁用！");
        }

        //登录成功，将员工id存入Session中，并返回登录成功结果
        request.getSession().setAttribute("employee",emp.getId());
        return R.success(emp);
    }

    /**
     * 员工退出登录
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request){
        request.getSession().removeAttribute("employee");
        return R.success("退出成功！");
    }

    /**
     *  新增员工信息
     * @param employee
     * @return
     */
    @PostMapping
    public R<String> addEmployee(HttpServletRequest request,@RequestBody Employee employee){
        System.out.println("新增员工信息："+employee.toString());
        //设置初始密码123456，并进行md5加密
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));

        /**
         * 这些字段已经设置为自动填充
        //设置员工创建时间和更新时间
        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());

        //获取当前的登录人id
        long empId = (long) request.getSession().getAttribute("employee");

        //设置员工的创建人和更新人
        employee.setCreateUser(empId);
        employee.setUpdateUser(empId);
        */

        employeeService.save(employee);

        return R.success("添加成功！");
    }

    /**
     * 分页查询 + 条件
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page,int pageSize,String name){
        log.info("分页信息：页码：{}，每页条目数：{}，查询的名字：{}",page,pageSize,name);

        //创建分页对象
        Page pageInfo = new Page(page,pageSize);

        //创建分页条件对象
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        //设置查询条件
        queryWrapper.like(StringUtils.isNotEmpty(name),Employee::getName,name);
        //添加排序条件
        queryWrapper.orderByDesc(Employee::getUpdateTime);

        //执行查询
        employeeService.page(pageInfo,queryWrapper);

        return R.success(pageInfo);
    }

    /**
     * 更新员工信息
     * @param employee
     * @return
     */
    @PutMapping
    public R<String> updateStatus(HttpServletRequest request, @RequestBody Employee employee){

        log.info("修改数据：employee{}",employee.toString());

        /**
         * 这些字段，已经由设置为自动处理（填充） - MyMetaObjectHandler
        Long empId = (Long) request.getSession().getAttribute("employee");
        employee.setUpdateUser(empId);
        employee.setUpdateTime(LocalDateTime.now());
        */

        employeeService.updateById(employee);

        return R.success("修改成功！");

    }

    /**
     * 编辑员工信息 ———— 根据id查询员工信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<Employee> getById(@PathVariable Long id){

        Employee employee = employeeService.getById(id);
        return R.success(employee);

    }

}
