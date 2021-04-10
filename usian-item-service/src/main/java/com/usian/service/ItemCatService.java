package com.usian.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.usian.mapper.AcDao;
import com.usian.mapper.TbItemCatMapper;
import com.usian.mapper.TbItemMapper;
import com.usian.pojo.ItemCatId;
import com.usian.pojo.TbItem;
import com.usian.pojo.TbItemCat;
import com.usian.pojo.TbItemCatExample;
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

    public Result selectItemCategoryByParentId(Integer id) {
        Result result = new Result();
        try {
            List<TbItemCat> itemCats = tbItemCatMapper.findall(id);
            result.setStatus(200);
            result.setMsg("成功");
            result.setData(itemCats);
        }
        catch (Exception e){
            result.setStatus(400);
            result.setMsg("错误");
        }
        return result;
    }

    public Result selectItemParamByItemCatId(Integer id) {
        Result result = new Result();
        try {
            ItemCatId itemCatId=acDao.findbyid(id);
            char two = itemCatId.getItemCatId().toString().charAt(1);
            char three = itemCatId.getItemCatId().toString().charAt(2);
            List<HashMap<String, String>> list = new ArrayList<>();

            HashMap<String, String> hashMap = new HashMap<>();
            TbItemCat tbItemCat=acDao.findbyparentid(String.valueOf(two));
            hashMap.put("param",tbItemCat.toString());
            hashMap.put("group",String.valueOf(two));

            HashMap<String, String>   hashMap2 = new HashMap<>();
            TbItemCat tbItemCat2=acDao.findbyparentid(String.valueOf(three));
            hashMap2.put("param",tbItemCat2.toString());
            hashMap2.put("group", String.valueOf(three));

            list.add(hashMap);
            list.add(hashMap2);
            itemCatId.setParamData(list);
            result.setStatus(200);
            result.setMsg("成功");
            result.setData(itemCatId);
        }
        catch (Exception e){
            result.setStatus(400);
            result.setMsg("错误");
        }
        return result;
    }
}
