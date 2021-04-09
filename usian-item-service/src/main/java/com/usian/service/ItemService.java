package com.usian.service;

import com.usian.mapper.*;
import com.usian.pojo.TbItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemService {
    @Autowired
    TbItemMapper tbItemMapper;
    public TbItem findByid(Long itemId) {
        System.out.println("测试");
        return tbItemMapper.selectByPrimaryKey(itemId);
    }
}
