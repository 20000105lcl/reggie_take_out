package org.example.reggie.common;

/**
 * @description: 自定义业务异常
 * @Title: CustomException
 * @Author: 刘成龙
 * @Version:1.0
 * @time: 2022/11/02 09:47
 */
public class CustomException extends RuntimeException {

    public CustomException(String message){
        super(message);
    }

}
