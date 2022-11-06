package org.example.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.reggie.dao.AddressBookDao;
import org.example.reggie.entity.AddressBook;
import org.example.reggie.service.AddressBookService;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @Title: AddressBookServiceImpl
 * @Author: 刘成龙
 * @Version:1.0
 * @time: 2022/11/05 11:06
 */
@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookDao, AddressBook> implements AddressBookService {
}
