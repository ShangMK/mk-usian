package com.usian.controller;

import com.usian.service.ItemFronedDetailsService;
import com.usian.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("foneddetails")
public class ContentCategoryController {
    @Autowired
    ItemFronedDetailsService itemFronedDetailsService;

    @RequestMapping("selectItemInfo")
    public Result selectItemInfo(Long itemId){
      return   itemFronedDetailsService.selectItemInfo(itemId);
    }
    @RequestMapping("selectItemDescByItemId")
    public Result selectItemDescByItemId(Long itemId){
      return   itemFronedDetailsService.selectItemDescByItemId(itemId);
    }
    @RequestMapping("selectTbItemParamItemByItemId")
    public Result selectTbItemParamItemByItemId(@RequestBody String itemId){
      return   itemFronedDetailsService.selectTbItemParamItemByItemId(itemId);
    }
}
