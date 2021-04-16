package com.usian.controller;

import com.usian.service.ItemCategoryServices;
import com.usian.service.SeachItemService;
import com.usian.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RequestMapping("seachservice")
@RestController
public class SerchItemControllers {

    @Autowired
    SeachItemService seachItemService;

    @RequestMapping("list")
    public ArrayList<Object> list(@RequestParam String q){
        return seachItemService.list(q);
    };
}
