package com.usian.controller;

import com.usian.feign.ItemDetailsFeign;
import com.usian.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("frontend/detail")
public class FrontedDetailController {
    @Autowired
    ItemDetailsFeign itemDetailsFeign;

    @RequestMapping("selectItemInfo")
    public Result selectItemInfo(@RequestParam Long itemId){
        return itemDetailsFeign.selectItemInfo(itemId);
    }
    @RequestMapping("selectItemDescByItemId")
    public Result selectItemDescByItemId(@RequestParam Long itemId){
        return itemDetailsFeign.selectItemDescByItemId(itemId);
    }
    @RequestMapping("selectTbItemParamItemByItemId")
    public Result selectTbItemParamItemByItemId(@RequestParam String itemId){
        return itemDetailsFeign.selectItemByItemId(itemId);
    }




}
