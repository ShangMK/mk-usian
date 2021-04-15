package com.usian.controller;

import com.usian.service.ItemCategoryServices;
import com.usian.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("personservice")
@RestController
public class ItemCategoryControllerS {

    @Autowired
    ItemCategoryServices itemCategoryServices;

    @RequestMapping("selectItemCategoryAll")
    public Result selectItemCategoryAll(){
        return itemCategoryServices.selectItemCategoryAll();
    };
    @RequestMapping("selectFrontendContentByAD")
    public Result selectFrontendContentByAD(){
        return itemCategoryServices.selectFrontendContentByAD();
    };
}
