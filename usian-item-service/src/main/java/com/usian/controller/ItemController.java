package com.usian.controller;

import com.usian.pojo.TbItem;
import com.usian.service.ItemService;
import com.usian.utils.PageResult;
import com.usian.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("service")
public class ItemController {
    @Autowired
    ItemService itemService;


    @RequestMapping("selectTbItemAllByPage")
    public Result selectTbItemAllByPage(Integer page){
      return itemService.selectTbItemAllByPage(page);
    }


    @RequestMapping("insertTbItem")
    public Result insertTbItem(@RequestBody TbItem tbItem){
      return itemService.insertTbItem(tbItem);
    }

    @RequestMapping("updateTbItem")
    public Result updateTbItem(@RequestBody TbItem tbItem){
      return itemService.updateTbItem(tbItem);
    }

    @RequestMapping("preUpdateItem")
    public Result preUpdateItem(@RequestBody Long itemId){
      return itemService.preUpdateItem(itemId);
    }

    @RequestMapping("deleteItemById")
    public Result deleteItemById(@RequestBody Long itemId){
      return itemService.deleteItemById(itemId);
    }

}

