package com.usian.controller;

import com.usian.feign.ItemContentFeign;
import com.usian.pojo.TbContent;
import com.usian.pojo.TbContentCategory;
import com.usian.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("content")
@Api("usian商城swage管理")
public class ContentCategoryController {
    @Autowired
    ItemContentFeign itemFeign;

    @GetMapping("selectContentCategoryByParentId")
    @ApiOperation("查询管理")
    public Result selectContentCategoryByParentId(@RequestParam(defaultValue = "0") Long id){
       return itemFeign.selectContentCategoryByParentId(id);
    }


    @RequestMapping("selectTbContentAllByCategoryId")
    public Result selectTbContentAllByCategoryId(Long categoryId){
       return itemFeign.selectTbContentAllByCategoryId(categoryId);
    }

    @RequestMapping("insertTbContent")
    public Result insertTbContent(TbContent tbContent){
       return itemFeign.insertTbContent(tbContent);
    }

    @RequestMapping("deleteContentByIds")
    public Result deleteContentByIds(Long ids){
        return itemFeign.deleteContentByIds(ids);
    }

    @RequestMapping("insertContentCategory")
    public Result insertContentCategory(TbContentCategory tbContentCategory){
        return itemFeign.insertContentCategory(tbContentCategory);
    }
    @RequestMapping("deleteContentCategoryById")
    public Result deleteContentCategoryById(Long categoryId){
        return itemFeign.deleteContentCategoryById(categoryId);
    }
    @RequestMapping("updateContentCategory")
    public Result updateContentCategory(TbContentCategory tbContentCategory){
        return itemFeign.updateContentCategory(tbContentCategory);
    }

    @RequestMapping("hello")
    public String hello(){
        return "hellohellohellohellohellohellohellohellohellohellohello";
    }
}
