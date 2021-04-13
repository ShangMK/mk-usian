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

/*

    @RequestMapping("insertTbItem")
    public Result insertTbItem(@RequestBody TbItem tbitem,@RequestParam String desc,@RequestParam String itemParams){
      return itemService.insertTbItem(tbitem,desc,itemParams);
    }

    @RequestMapping("updateTbItem")
    public Result updateTbItem(@RequestBody TbItem tbitem,@RequestParam String desc,@RequestParam String itemParams){
      return itemService.updateTbItem(tbitem,desc,itemParams);
    }

    @RequestMapping("preUpdateItem")
    public Result preUpdateItem(@RequestBody Long itemId){
      return itemService.preUpdateItem(itemId);
    }

    @RequestMapping("deleteItemById")
    public Result deleteItemById(@RequestBody Long itemId){
      return itemService.deleteItemById(itemId);
    }
*/

}

