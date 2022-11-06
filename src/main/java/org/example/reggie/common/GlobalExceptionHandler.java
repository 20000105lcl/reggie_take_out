package org.example.reggie.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * @Title: GlobalExceptionHandler--全局异常捕获器
 * @Author: 刘成龙
 * @Version:1.0
 * @time: 2022/10/31 20:31
 * @description: 创建一个全局的异常处理器
 */
@ControllerAdvice(annotations = {RestController.class, Controller.class})
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {

    /**
     * SQLIntegrityConstraintViolationException 异常处理器
     * @param exception
     * @return
     */
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public R<String> exceptionHandler(SQLIntegrityConstraintViolationException exception){
        log.info("========="+exception.getMessage()+"===========");
        if (exception.getMessage().contains("Duplicate entry")){
            String msg = exception.getMessage().split(" ")[2].replaceAll("[']","")+"已存在！";
            return R.error("添加失败，"+msg);
        }
        return R.error("添加失败!");
    }

    /**
     * CustomException 异常处理器
     * @param exception
     * @return
     */
    @ExceptionHandler(CustomException.class)
    public R<String> exceptionHandler(CustomException exception){
        log.info("========="+exception.getMessage()+"===========");
        return R.error(exception.getMessage());
    }

}
