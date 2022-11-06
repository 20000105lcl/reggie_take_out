package org.example.reggie.common;

/**
 * @Title: BaseContext - 基于ThreadLocal封装工具类，用户保存和获取当前登录的用户id
 * @Author: 刘成龙
 * @Version:1.0
 * @time: 2022/11/01 19:40
 * @description:
 */
public class BaseContext {

    private static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    public static void setCurrentId(Long id){
        threadLocal.set(id);
    }

    public static Long getCurrentId(){
        return threadLocal.get();
    }

}
