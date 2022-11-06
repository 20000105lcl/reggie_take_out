package org.example.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.reggie.dto.SetmealDto;
import org.example.reggie.entity.Setmeal;

import java.util.List;

/**
 * @Title: SetmealService
 * @Author: 刘成龙
 * @Version:1.0
 * @time: 2022/11/01 21:52
 * @description:
 */
public interface SetmealService extends IService<Setmeal> {

    /**
     * 新增套餐，保存套餐信息以及套餐中的菜品信息
     * @param setmealDto
     */
    void saveSetmealWithDish(SetmealDto setmealDto);

    /**
     * 删除套餐，并删除套餐关联的套餐与菜品关联数据信息
     * @param ids
     */
    void deleteSetmealWithDish(List<Long> ids);

    /**
     * 更新套餐信息--套餐基本信息-setmeal表，套餐关联的菜品信息-setmeal_dish表
     * @param setmealDto
     */
    void updateSetmealWithDish(SetmealDto setmealDto);
}
