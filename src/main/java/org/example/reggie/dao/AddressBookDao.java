package org.example.reggie.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.reggie.entity.AddressBook;

/**
 * @description:
 * @Title: AddressBookDao
 * @Author: 刘成龙
 * @Version:1.0
 * @time: 2022/11/05 11:04
 */
@Mapper
public interface AddressBookDao extends BaseMapper<AddressBook> {
}
