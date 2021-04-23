package com.usian.controller;

import com.usian.feign.SeachFeign;
import com.usian.pojo.SearchItem;
import com.usian.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("frontend/searchItem")
public class SeachController {
    @Autowired
    SeachFeign seachFeign;
    @RequestMapping("importAll")
    public Result importAll(){
        return seachFeign.importAll();
    }
    @RequestMapping("list")
    public List<SearchItem> list(String q, @RequestParam(defaultValue = "1")
            Long page, @RequestParam(defaultValue = "20") Integer pageSize){
        return seachFeign.list(q,page,pageSize);
    }

}
