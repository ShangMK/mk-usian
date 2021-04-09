package com.usian.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.usian.mapper.*;
import com.usian.pojo.TbItem;
import com.usian.utils.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemService {
    @Autowired
    TbItemMapper tbItemMapper;


    public PageResult selectTbItemAllByPage(Integer page) {
        PageHelper.startPage(page,5);
        Page<TbItem> page1=tbItemMapper.findall();
        PageResult pageResult = new PageResult();
        pageResult.setPageIndex(page);
        pageResult.setResult(page1.getResult());
        pageResult.setTotalPage(page1.getTotal());
        return pageResult;
    }
}
