package org.example.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.example.reggie.common.R;
import org.example.reggie.entity.User;
import org.example.reggie.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @description:
 * @Title: UserController
 * @Author: 刘成龙
 * @Version:1.0
 * @time: 2022/11/05 09:56
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 用户登录
     * @param request
     * @param map
     * @return
     */
    @PostMapping("/login")
    public R<User> login(HttpServletRequest request, @RequestBody Map map){

        log.info("map={}",map);

        String phone = (String) map.get("phone");
        //先判断该用户是否是新用户，是-自动注册该用户，否-直接登录

        //1、条件查询
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<User>();
        //1.1、添加手机号的条件
        queryWrapper.eq(User::getPhone,phone);
        //查询
        User user = userService.getOne(queryWrapper);

        //判断是否是新用户
        if (user==null){
            //注册新用户
            user = new User();
            user.setPhone(phone);
            userService.save(user);
        }

        request.getSession().setAttribute("user",user.getId());

        return R.success(user);
    }

    /**
     * 用户退出
     * @param request
     * @return
     */
    @PostMapping("logout")
    public R<String> logout(HttpServletRequest request){
        request.getSession().removeAttribute("user");
        return R.success("退出成功！");
    }

}
