package com.usian.controller;

import com.usian.PersonItemCategoryFeign;
import com.usian.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("frontend/itemCategory")
public class PersonItemCategoryWebController {
    @Autowired
    PersonItemCategoryFeign personItemCategoryFeign;

    @RequestMapping("selectItemCategoryAll")
    public Result selectItemCategoryAll(){
        return personItemCategoryFeign.selectItemCategoryAll();
    }





}
