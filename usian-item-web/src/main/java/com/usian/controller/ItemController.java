package com.usian.controller;

import com.usian.feign.ItemFeign;
import com.usian.pojo.TbItem;
import com.usian.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("frontend/item")
public class ItemController {
    @Autowired
    ItemFeign itemFeign;

    @RequestMapping("selectItemInfo")
    public Result selectItemInfo(Long itemId){
        TbItem tbItem= itemFeign.findById(itemId);
        if (tbItem != null) {
            return Result.ok(tbItem);
        } else {
            return Result.error("失败");
        }
    }

}
