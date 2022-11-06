package org.example.reggie.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.reggie.entity.Category;

/**
 * @Title: CategoryDao
 * @Author: 刘成龙
 * @Version:1.0
 * @time: 2022/11/01 19:59
 * @description:
 */
@Mapper
public interface CategoryDao extends BaseMapper<Category> {
}
