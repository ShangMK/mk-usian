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
    public Result selectTbItemAllByPage(){
        PageResult pageResult = itemFeign.selectItemParamAll();
        if (pageResult != null) {
            return Result.ok(pageResult);
        } else {
            return Result.error("错误");
        }
    }
    @RequestMapping("insertItemParam")
    public Result insertItemParam(Long itemCatId,String paramData){
        return itemFeign.insertItemParam(itemCatId, paramData);
    }
    @RequestMapping("deleteItemParamById")
    public Result deleteItemParamById(Long id){
        return itemFeign.deleteItemParamById(id);
    }

}
