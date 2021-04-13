package com.usian.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.usian.mapper.TbItemParamMapper;
import com.usian.pojo.TbItemParam;
import com.usian.pojo.TbItemParamExample;
import com.usian.utils.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
