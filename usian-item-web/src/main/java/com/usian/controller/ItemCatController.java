package com.usian.controller;

import com.usian.feign.ItemFeign;
import com.usian.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("backend/itemCategory")
public class ItemCatController {
    @Autowired
    ItemFeign itemFeign;


    @RequestMapping("selectItemCategoryByParentId")
    public Result selectItemCategoryByParentId(Integer id){
        if (id==null){
            return itemFeign .selectItemCategoryByParentId(0);
        }

        return itemFeign .selectItemCategoryByParentId(id);
    }


}
