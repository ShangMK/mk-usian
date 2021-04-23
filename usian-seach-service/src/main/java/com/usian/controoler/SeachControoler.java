package com.usian.controoler;

import com.usian.pojo.SearchItem;
import com.usian.service.SeachService;
import org.springframework.beans.factory.annotation.Autowired;
import com.usian.utils.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("SeachService")
public class SeachControoler {
    @Autowired
    SeachService seachService;

    @RequestMapping("importAll")
    public Result importAll(){
        return   seachService.importAll();
    }
    @RequestMapping("list")
    public List<SearchItem> list( String q,  Long page,  Integer pageSize){
        return   seachService.list(q,page,pageSize);
    }
}