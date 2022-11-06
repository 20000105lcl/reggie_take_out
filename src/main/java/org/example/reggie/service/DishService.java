package org.example.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.reggie.dto.DishDto;
import org.example.reggie.entity.Dish;

import java.util.List;

/**
 * @Title: DishService
 * @Author: 刘成龙
 * @Version:1.0
 * @time: 2022/11/01 21:51
 * @description:
 */
public interface DishService extends IService<Dish> {

    void saveDishWithFlavor(DishDto dishDto);

    DishDto getByIdWithFlavor(Long id);

    void updateDishWithFlavor(DishDto dishDto);

    /**
     * 删除菜品信息，判断是否停售，判断是否关联套餐
     * @param ids
     */
    void delete(List<Long> ids);

    /**
     * 根据id起售与停售菜品 -- （当停售菜品时，对应的套餐也要被停售）
     * @param Status
     * @param ids
     */
    void updateStatus(int Status,List<Long> ids);
}
