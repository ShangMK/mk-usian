package com.usian.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.usian.mapper.AcDao;
import com.usian.mapper.TbItemCatMapper;
import com.usian.mapper.TbItemMapper;
import com.usian.mapper.TbItemParamMapper;
import com.usian.pojo.*;
import com.usian.utils.PageResult;
import com.usian.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class ItemCatService {
    @Autowired
    TbItemMapper tbItemMapper;
    @Autowired
    TbItemCatMapper tbItemCatMapper;
    @Autowired
    AcDao acDao;
    @Autowired
    TbItemParamMapper tbItemParamMapper;

    public Result selectItemCategoryByParentId(Integer id) {
        try {
            TbItemCatExample tbItemCatExample=new TbItemCatExample();
            TbItemCatExample.Criteria criteria = tbItemCatExample.createCriteria();
            criteria.andParentIdEqualTo((long) id);
            List<TbItemCat> itemCats = tbItemCatMapper.selectByExample(tbItemCatExample);
            return Result.ok(itemCats);
        }
        catch (Exception e){
            return Result.error("错误");
        }
    }

    public Result selectItemParamByItemCatId(Integer id) {

        try {
           TbItemParamExample tbItemParamExample=new TbItemParamExample();
            TbItemParamExample.Criteria criteria = tbItemParamExample.createCriteria();
            criteria.andItemCatIdEqualTo((long) id);
            List<TbItemParam> tbItemParams = tbItemParamMapper.selectByExampleWithBLOBs(tbItemParamExample);
            if (tbItemParams.size()>0){return Result.ok(tbItemParams.get(0));}
            return null;
        }
        catch (Exception e){
            return Result.error("错误");
        }

    }
}
