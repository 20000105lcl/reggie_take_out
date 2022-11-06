package org.example.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.reggie.common.R;
import org.example.reggie.entity.Category;

/**
 * @Title: CategoryService
 * @Author: 刘成龙
 * @Version:1.0
 * @time: 2022/11/01 20:00
 * @description:
 */
public interface CategoryService extends IService<Category> {

    /**
     * 根据id删除，要先判断是否关联了菜品或套餐
     * @param id
     */
    public void remove(Long id);

}
