package com.usian.controller;

import com.usian.feign.ItemFeign;
import com.usian.pojo.TbItem;
import com.usian.utils.PageResult;
import com.usian.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("backend/itemParam")
public class ItemParamController {
    @Autowired
    ItemFeign itemFeign;

    @RequestMapping("selectItemParamAll")
    public PageResult selectTbItemAllByPage(){
        return itemFeign.selectItemParamAll();
    }
   /* @RequestMapping("insertTbItem")
    public Result insertTbItem(@RequestParam Integer cid,String image,String title,String sellPoint,Integer price,Integer num,String desc,String itemParams){
        TbItem tbItem = new TbItem();
        if (cid!=null){tbItem.setCid(Long.parseLong(cid.toString()));}
        if (title!=null){ tbItem.setTitle(title);}
        if (sellPoint!=null){ tbItem.setSellPoint(sellPoint);}
        if (num!=null){ tbItem.setNum(num);}
        if (price!=null){ tbItem.setPrice(Long.parseLong(price.toString()));}
        if (image != null) { tbItem.setImage(image); }
        return itemFeign.insertTbItem(tbItem,desc,itemParams);
    }
*//*
    @RequestMapping("insertTbItem")
    public Result insertTbItem(@RequestBody TbItem tbItem,@RequestParam String itemParams,@RequestParam String desc){
        return itemFeign.insertTbItem(tbItem,desc,itemParams);
    }
*//*

    @RequestMapping("updateTbItem")
    public Result updateTbItem(@RequestParam Long id,Integer cid,String title,String sellPoint,Integer price,Integer num,String desc,String itemParams){
        TbItem tbItem = new TbItem();
        tbItem.setId(Long.parseLong(id.toString()));
        if (cid!=null){tbItem.setCid(Long.parseLong(cid.toString()));}
        if (title!=null){ tbItem.setTitle(title);}
        if (sellPoint!=null){ tbItem.setSellPoint(sellPoint);}
        if (num!=null){ tbItem.setNum(num);}
        if (price!=null){ tbItem.setPrice(Long.parseLong(price.toString()));}
        return itemFeign.updateTbItem(tbItem,desc,itemParams);
    }
    @RequestMapping("preUpdateItem")
    public Result preUpdateItem(Long itemId){
        return itemFeign.preUpdateItem(itemId);
    }
    @RequestMapping("deleteItemById")
    public Result deleteItemById(Long itemId){
        return itemFeign.deleteItemById(itemId);
    }
*/
}
