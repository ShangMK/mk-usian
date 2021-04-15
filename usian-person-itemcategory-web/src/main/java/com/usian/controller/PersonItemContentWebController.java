package com.usian.controller;

import com.usian.PersonItemCategoryFeign;
import com.usian.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("frontend/content")
public class PersonItemContentWebController {
    @Autowired
    PersonItemCategoryFeign personItemCategoryFeign;

    @RequestMapping("selectFrontendContentByAD")
    public Result selectFrontendContentByAD(){
        return personItemCategoryFeign.selectFrontendContentByAD();
    }




}
