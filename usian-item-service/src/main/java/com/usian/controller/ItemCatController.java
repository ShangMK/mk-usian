package com.usian.controller;

import com.usian.service.ItemCatService;
import com.usian.service.ItemService;
import com.usian.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("itemcat")
public class ItemCatController {
    @Autowired
    ItemCatService itemCatService;

    @RequestMapping("selectItemCategoryByParentId")
    public Result selectItemCategoryByParentId( Integer id){
      return itemCatService.selectItemCategoryByParentId(id);
    }
    @RequestMapping("selectItemParamByItemCatId/{id}")
    public Result selectItemParamByItemCatId(@PathVariable Integer id){
      return itemCatService.selectItemParamByItemCatId(id);
    }
}

