package com.usian.controller;

import com.usian.pojo.TbItem;
import com.usian.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("service")
public class ItemController {
    @Autowired
    ItemService itemService;


    @RequestMapping("findById")
    public TbItem findById( Long itemId){
        TbItem tbItem=itemService.findByid(itemId);
        return tbItem;
    }
}