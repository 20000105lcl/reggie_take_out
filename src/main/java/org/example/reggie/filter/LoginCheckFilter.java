package org.example.reggie.filter;

import com.alibaba.fastjson.JSON;
import org.example.reggie.common.BaseContext;
import org.example.reggie.common.R;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Title: LoginCheckFilter
 * @Author: 刘成龙
 * @Version:1.0
 * @time: 2022/10/31 17:24
 * @description:
 */
@WebFilter(filterName = "loginCheckFilter" , urlPatterns = "/*")
public class LoginCheckFilter implements Filter {

    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();
    public static final String[] urls = new String[]{
            "/employee/login",
            "/employee/logout",
            "/backend/**",
            "/front/**",
            "/common/**",
            "/user/login"//移动端登录
    };

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String requestURI = request.getRequestURI();

        //判断本次访问路径是否需要处理
        boolean check = checkUrl(requestURI);

        //不需要处理，直接放行
        if (check){
            filterChain.doFilter(request,response);
            return;
        }

        //判断员工端是否登录，已经登录则放行
        if (request.getSession().getAttribute("employee")!=null){
            //将当前登录的用户id通过BaseContext工具类保存到ThreadLocal中（ThreadLocal,一个线程中的公用变量区）
            Long empId = (Long) request.getSession().getAttribute("employee");
            BaseContext.setCurrentId(empId);
            filterChain.doFilter(request,response);
            return;
        }

        //判断用户端是否登录，已经登录则放行
        if (request.getSession().getAttribute("user")!=null){
            //将当前登录的用户id通过BaseContext工具类保存到ThreadLocal中（ThreadLocal,一个线程中的公用变量区）
            Long userId = (Long) request.getSession().getAttribute("user");
            BaseContext.setCurrentId(userId);
            filterChain.doFilter(request,response);
            return;
        }

        //未登录，则需要跳转到登录页面，直接写会一个JSON数据返回，前端进行响应拦截
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        return;
    }

    //检查本次请求是否包含在拦截中
    public boolean checkUrl(String requestURI){
        for (String url : urls) {
            boolean flag = PATH_MATCHER.match(url, requestURI);
            if (flag){
                return true;
            }
        }
        return false;
    }
}
