package com.usian.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.usian.mapper.*;
import com.usian.pojo.TbItem;
import com.usian.pojo.TbItemCat;
import com.usian.pojo.TbItemCatExample;
import com.usian.pojo.TbItemExample;
import com.usian.utils.PageResult;
import com.usian.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {
    @Autowired
    TbItemMapper tbItemMapper;
    @Autowired
    TbItemCatMapper tbItemCatMapper;


    public Result selectTbItemAllByPage(Integer page) {
        Result result = new Result();
        try {
            PageHelper.startPage(page,5);
            Page<TbItem> page1=tbItemMapper.findall();
            PageResult pageResult = new PageResult();
            pageResult.setPageIndex(page);
            pageResult.setResult(page1.getResult());
            pageResult.setTotalPage(page1.getTotal());
            result.setStatus(200);
            result.setMsg("成功");
            result.setData(pageResult);
        }
        catch (Exception e){
            result.setStatus(400);
            result.setMsg("错误");
        }
        return result;
    }
}
