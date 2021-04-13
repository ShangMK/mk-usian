package com.usian.controller;

import com.usian.pojo.TbItem;
import com.usian.service.ItemParamService;
import com.usian.service.ItemService;
import com.usian.utils.PageResult;
import com.usian.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("itemparam")
public class ItemParamController {
    @Autowired
    ItemParamService itemParamService;


    @RequestMapping("selectItemParamAll")
    public PageResult selectTbItemAllByPage(){
      return itemParamService.selectTbItemAllByPage();
    }

    @RequestMapping("insertItemParam")
    public Result insertItemParam(Long itemCatId,String paramData){
      return itemParamService.insertItemParam(itemCatId,paramData);
    }


    @RequestMapping("deleteItemParamById")
    public Result deleteItemParamById( Long id){
      return itemParamService.deleteItemParamById(id);
    }



}

