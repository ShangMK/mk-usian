package com.usian.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.usian.mapper.TbItemParamMapper;
import com.usian.pojo.TbItem;
import com.usian.pojo.TbItemParam;
import com.usian.pojo.TbItemParamExample;
import com.usian.utils.PageResult;
import com.usian.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ItemParamService {
    @Autowired
    TbItemParamMapper tbItemParamMapper;


    public PageResult selectTbItemAllByPage() {
        PageHelper.startPage(1, 5);
        PageResult pageResult = new PageResult();
        try {
            List<TbItemParam> tbItemParams = tbItemParamMapper.selectByExampleWithBLOBs(new TbItemParamExample());
            PageInfo<TbItemParam> tbItemParamPageInfo = new PageInfo<>(tbItemParams);
            pageResult.setTotalPage(tbItemParamPageInfo.getTotal());
            pageResult.setPageIndex(1);
            pageResult.setResult(tbItemParamPageInfo.getList());
            return pageResult;
        } catch (Exception e) {
            return null;
        }
    }

    public Result insertItemParam(Long itemCatId,String paramData) {
        Date date = new Date();
        try {
            TbItemParam tbItemParam = new TbItemParam();
            tbItemParam.setItemCatId(itemCatId);
            tbItemParam.setParamData(paramData);
            tbItemParam.setCreated(date);
            tbItemParam.setUpdated(date);
            tbItemParamMapper.insertSelective(tbItemParam);
            return Result.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("错误");
        }
    }

    public Result deleteItemParamById(Long id) {
        try {
            tbItemParamMapper.deleteByPrimaryKey(id);
            return Result.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("错误");
        }
    }
}
