package com.usian.controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.usian.feign.ItemFeign;
import com.usian.pojo.TbItem;
import com.usian.utils.PageResult;
import com.usian.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("backend/item")
public class ItemController {
    @Autowired
    ItemFeign itemFeign;

    @RequestMapping("selectTbItemAllByPage")
    public Result selectTbItemAllByPage(@RequestParam Integer page){
        return itemFeign.selectTbItemAllByPage(page);
    }
    @RequestMapping("insertTbItem")
    public Result insertTbItem(@RequestParam Integer cid,String title,String sellPoint,Integer price,Integer num,String desc,String itemParams){
        TbItem tbItem = new TbItem();
        tbItem.setCid(Long.parseLong(cid.toString()));
        tbItem.setTitle(title+"-"+desc);
        tbItem.setSellPoint(sellPoint);
        tbItem.setPrice(Long.parseLong(price.toString()));
        tbItem.setNum(num);
        return itemFeign.insertTbItem(tbItem);
    }
    @RequestMapping("updateTbItem")
    public Result updateTbItem(@RequestParam Long id,Integer cid,String title,String sellPoint,Integer price,Integer num,String desc,String itemParams){
        TbItem tbItem = new TbItem();
        tbItem.setId(Long.parseLong(id.toString()));
        if (cid!=null){tbItem.setCid(Long.parseLong(cid.toString()));}
        if (title!=null){ tbItem.setTitle(title);}
        if (sellPoint!=null){ tbItem.setSellPoint(sellPoint);}
        if (num!=null){ tbItem.setNum(num);}
        if (price!=null){ tbItem.setPrice(Long.parseLong(price.toString()));}
        if (desc!=null){tbItem.setBarcode(desc);}
        return itemFeign.updateTbItem(tbItem);
    }
    @RequestMapping("preUpdateItem")
    public Result preUpdateItem(Long itemId){
        return itemFeign.preUpdateItem(itemId);
    }
    @RequestMapping("deleteItemById")
    public Result deleteItemById(Long itemId){
        return itemFeign.deleteItemById(itemId);
    }

}
