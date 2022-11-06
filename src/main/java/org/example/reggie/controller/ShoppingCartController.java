package org.example.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.example.reggie.common.BaseContext;
import org.example.reggie.common.R;
import org.example.reggie.entity.ShoppingCart;
import org.example.reggie.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @description:
 * @Title: ShoppingCartController
 * @Author: 刘成龙
 * @Version:1.0
 * @time: 2022/11/05 14:49
 */
@RestController
@RequestMapping("/shoppingCart")
@Slf4j
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    /**
     * 查询购物车信息
     * @return
     */
    @GetMapping("/list")
    public R<List<ShoppingCart>> list(){

        log.info("有人访问了该路径！");

        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        //设置用户id,指明当前要查询的谁的购物车
        queryWrapper.eq(ShoppingCart::getUserId,BaseContext.getCurrentId());
        //添加排序，按时间升序
        queryWrapper.orderByAsc(ShoppingCart::getCreateTime);
        //查询
        List<ShoppingCart> shoppingCartList = shoppingCartService.list(queryWrapper);

        return R.success(shoppingCartList);
    }

    /**
     * 添加菜品或套餐进入购物车
     * @param shoppingCart
     * @return
     */
    @PostMapping("/add")
    public R<ShoppingCart> add(@RequestBody ShoppingCart shoppingCart){
        //设置用户id，指定当前是哪个用户的购物车数据
        Long userId = BaseContext.getCurrentId();
        shoppingCart.setUserId(userId);
        //查询购物车中是否存在当前菜品或套餐
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        //添加userId条件
        queryWrapper.eq(ShoppingCart::getUserId,userId);
        //添加菜品id或套餐id
        queryWrapper.eq(shoppingCart.getDishId()!=null,ShoppingCart::getDishId,shoppingCart.getDishId());
        queryWrapper.eq(shoppingCart.getSetmealId()!=null,ShoppingCart::getSetmealId,shoppingCart.getSetmealId());
        //查询
        ShoppingCart shoppingCart1 = shoppingCartService.getOne(queryWrapper);
        //查询出不存在该菜品或套餐，插入操作
        if (shoppingCart1==null){
            //设置该菜品或套餐的数量为1
            shoppingCart.setNumber(1);
            //设置添加时间
            shoppingCart.setCreateTime(LocalDateTime.now());
            //添加该菜品或套餐进入购物车
            shoppingCartService.save(shoppingCart);
            shoppingCart1=shoppingCart;
        }else {
            //存在该菜品或套餐,更新操作
            //获取当前菜品或套餐的数量
            Integer number = shoppingCart1.getNumber();
            //当前数量+1
            shoppingCart1.setNumber(number+1);
            //更新菜品或套餐信息
            shoppingCartService.updateById(shoppingCart1);
        }
        return R.success(shoppingCart1);
    }

    /**
     * 清空当前用户的购物车
     * @return
     */
    @DeleteMapping("/clean")
    public R<String> clean(){
        //获取当前用户的id
        Long id = BaseContext.getCurrentId();
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        //添加用户id条件
        queryWrapper.eq(ShoppingCart::getUserId,id);
        //删除该用户的购物车信息
        shoppingCartService.remove(queryWrapper);
        return R.success("清空成功！");
    }

    /**
     * 将当前菜品或套餐数量 -1
     * @param shoppingCart
     * @return
     */
    @PostMapping("sub")
    public R<String> sub(@RequestBody ShoppingCart shoppingCart){
        //获取当前用户id
        Long userId = BaseContext.getCurrentId();
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        //添加用户id条件，指明操作哪个用户的购物车
        queryWrapper.eq(ShoppingCart::getUserId,userId);
        //添加菜品或套餐的id，指明需要移除的菜品或套餐
        queryWrapper.eq(shoppingCart.getDishId()!=null,ShoppingCart::getDishId,shoppingCart.getDishId());
        queryWrapper.eq(shoppingCart.getSetmealId()!=null,ShoppingCart::getSetmealId,shoppingCart.getSetmealId());
        //查询当前用户购物车中该菜品或套餐
        ShoppingCart cart = shoppingCartService.getOne(queryWrapper);
        //获取当前用户购物车中该菜品或套餐的数量
        int num = cart.getNumber();
        if (num<=1){
            //当前菜品或套餐数量只有一份， -1 后要删除该购物车
            shoppingCartService.remove(queryWrapper);
        }else {
            //当前菜品或套餐数量 >1 ,减少一份
            cart.setNumber(num-1);
            //更新购物车数据
            shoppingCartService.updateById(cart);
        }
        return R.success("移除成功！");
    }


}
