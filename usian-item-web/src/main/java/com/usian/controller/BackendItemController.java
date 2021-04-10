package com.usian.controller;

import com.usian.feign.ItemFeign;
import com.usian.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("backend/itemParam")
public class BackendItemController {
    @Autowired
    ItemFeign itemFeign;

    @RequestMapping("selectItemParamByItemCatId/{id}")
    public Result selectItemParamByItemCatId(@PathVariable Integer id){
        return itemFeign .selectItemParamByItemCatId(id);
    }


}
