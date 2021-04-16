package com.usian.controller;

import com.usian.PersonItemCategoryFeign;
import com.usian.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("frontend/searchItem")
public class SerchItemController {
    @Autowired
    PersonItemCategoryFeign personItemCategoryFeign;
/*
    @RequestMapping("list")
    public List<?> list(@RequestParam(defaultValue = "1") String q){
        return personItemCategoryFeign.list(q);
    }*/
    @RequestMapping("list")
    public List<?> list(@RequestParam(defaultValue = "1") String q){
        return personItemCategoryFeign.list(q);
    }




}
