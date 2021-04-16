package com.usian.service;

import com.usian.mapper.TbItemCatMapper;
import com.usian.mapper.TbItemDescMapper;
import com.usian.mapper.TbItemMapper;
import com.usian.pojo.*;
import com.usian.utils.PageResult;
import com.usian.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SeachItemService {
    @Autowired
    TbItemMapper tbItemMapper;
    @Autowired
    TbItemCatMapper tbItemCatMapper;
    @Autowired
    TbItemDescMapper tbItemDescMapper;

    public ArrayList<Object> list(String q) {
        ArrayList<Object> arrayList = new ArrayList<>();
        TbItemExample tbItemExample = new TbItemExample();
        TbItemExample.Criteria criteria = tbItemExample.createCriteria();
        criteria.andStatusEqualTo((byte)1);
        tbItemExample.setOrderByClause("created asc");
        List<TbItem> tbItems = tbItemMapper.selectByExample(null);
        for (TbItem tbItem : tbItems) {
            TbItemDesc tbItemDesc = tbItemDescMapper.selectByPrimaryKey(tbItem.getId());
            TbItemCat tbItemCat = tbItemCatMapper.selectByPrimaryKey(tbItem.getCid());
            SeachItem seachItem = zHuang(tbItem);
            if (tbItemDesc!= null) {
                seachItem.setItem_desc(tbItemDesc.getItemDesc());
            }
            if (tbItemCat!= null) {
                seachItem.setItem_category_name(tbItemCat.getName());
            }
            arrayList.add(seachItem);
        }
        return arrayList;
    }

    private SeachItem zHuang(TbItem tbItem){
        SeachItem seachItem = new SeachItem();
        seachItem.setId(tbItem.getId());
        seachItem.setItem_image(tbItem.getImage());
        seachItem.setItem_price(tbItem.getPrice());
        seachItem.setItem_sell_point(tbItem.getSellPoint());
        seachItem.setItem_title(tbItem.getTitle());
        return seachItem;
    }

}
