package com.usian.controller;

import com.usian.feign.ItemCatFeign;
import com.usian.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("backend/itemCategory")
public class ItemCatController {
    @Autowired
    ItemCatFeign itemCatFeign;

    @RequestMapping("selectItemCategoryByParentId")
    public Result selectItemCategoryByParentId(){
        return itemCatFeign .selectItemCategoryByParentId();
    }

}
