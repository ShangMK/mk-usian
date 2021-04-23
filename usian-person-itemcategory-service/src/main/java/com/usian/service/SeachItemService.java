package com.usian.service;

import com.usian.mapper.TbItemCatMapper;
import com.usian.mapper.TbItemDescMapper;
import com.usian.mapper.TbItemMapper;
import com.usian.pojo.*;
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
            SearchItem searchItem = zHuang(tbItem);
            if (tbItemDesc!= null) {
                searchItem.setItem_desc(tbItemDesc.getItemDesc());
            }
            if (tbItemCat!= null) {
                searchItem.setItem_category_name(tbItemCat.getName());
            }
            arrayList.add(searchItem);
        }
        return arrayList;
    }

    private SearchItem zHuang(TbItem tbItem){
        SearchItem searchItem = new SearchItem();
        searchItem.setId(tbItem.getId());
        searchItem.setItem_image(tbItem.getImage());
        searchItem.setItem_price(tbItem.getPrice());
        searchItem.setItem_sell_point(tbItem.getSellPoint());
        searchItem.setItem_title(tbItem.getTitle());
        return searchItem;
    }

}
