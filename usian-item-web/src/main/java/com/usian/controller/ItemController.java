package com.usian.controller;

import com.usian.feign.ItemFeign;
import com.usian.pojo.TbItem;
import com.usian.utils.PageResult;
import com.usian.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("backend/item")
public class ItemController {
    @Autowired
    ItemFeign itemFeign;

    @RequestMapping("selectTbItemAllByPage")
    public Result selectTbItemAllByPage(@RequestParam Integer page){
        return itemFeign.selectTbItemAllByPage(page);
    }

}
