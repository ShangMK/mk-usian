package com.usian.controller;

import com.usian.pojo.TbContent;
import com.usian.pojo.TbContentCategory;
import com.usian.service.ContentCategoryService;
import com.usian.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("contentcategory")
public class ContentCategoryController {
   @Autowired
    ContentCategoryService contentCategoryService;

    @RequestMapping("selectContentCategoryByParentId")
    public Result selectContentCategoryByParentId(Long id){
       return contentCategoryService.selectContentCategoryByParentId(id);
    }

    @RequestMapping("selectTbContentAllByCategoryId")
    public Result selectTbContentAllByCategoryId(Long categoryId){
       return contentCategoryService.selectTbContentAllByCategoryId(categoryId);
    }

    @RequestMapping("insertTbContent")
    public Result insertTbContent(@RequestBody TbContent tbContent){
       return contentCategoryService.insertTbContent(tbContent);
    }
    @RequestMapping("deleteContentByIds")
    public Result deleteContentByIds(@RequestParam Long ids){
       return contentCategoryService.deleteContentByIds(ids);
    }

    @RequestMapping("insertContentCategory")
    public Result insertContentCategory(@RequestBody TbContentCategory tbContentCategory){
        return contentCategoryService.insertContentCategory(tbContentCategory);
    }
    @RequestMapping("updateContentCategory")
    public Result updateContentCategory(@RequestBody TbContentCategory tbContentCategory){
        return contentCategoryService.updateContentCategory(tbContentCategory);
    }
    @RequestMapping("deleteContentCategoryById")
    public Result deleteContentCategoryById(Long categoryId){
        return contentCategoryService.deleteContentCategoryById(categoryId);
    }

}
