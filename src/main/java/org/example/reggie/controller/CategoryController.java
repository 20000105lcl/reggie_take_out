package org.example.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.example.reggie.common.R;
import org.example.reggie.entity.Category;
import org.example.reggie.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Title: CategoryController - 分类管理
 * @Author: 刘成龙
 * @Version:1.0
 * @time: 2022/11/01 20:05
 * @description:
 */
@RestController
@RequestMapping("/category")
@Slf4j
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 添加分类
     * @param category
     * @return
     */
    @PostMapping
    public R<String> save(@RequestBody Category category){
        categoryService.save(category);
        return R.success("添加成功！");
    }

    /**
     * 分页查询 -- 分类管理页面
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/page")
    public R<Page<Category>> page(int page,int pageSize){

        log.info("分类--分页查询--{}，{}",page,pageSize);

        Page<Category> categoryPage = new Page<>(page,pageSize);

        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        //排序规则
        queryWrapper.orderByAsc(Category::getSort);

        categoryService.page(categoryPage,queryWrapper);

        log.info("{}",categoryPage.toString());

        return R.success(categoryPage);
    }

    /**
     * 根据id删除分类，要判断当前套餐是否使用 --分类管理页面
     * @param id
     * @return
     */
    @DeleteMapping
    public R<String> delete(@RequestParam("ids") Long id){
        log.info("删除--id={}",id);
        categoryService.remove(id);
        return R.success("删除成功！");
    }

    /**
     * 更新分类信息
     * @return
     */
    @PutMapping
    public R<String> update(@RequestBody Category category){

        log.info("更新分类：{}",category);
        categoryService.updateById(category);
        return R.success("更新成功！");
    }

    /**
     * 根据条件查询分类数据
     * @param category
     * @return
     */
    @GetMapping("/list")
    public R<List<Category>> list(Category category){

        //创建条件查询构造器
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        //添加分类条件
        queryWrapper.eq(category.getType()!=null,Category::getType,category.getType());
        //添加排序--根据排序字段升序，更新日期降序
        queryWrapper.orderByAsc(Category::getSort).orderByDesc(Category::getUpdateTime);

        List<Category> categoryList = categoryService.list(queryWrapper);

        return R.success(categoryList);
    }






}
