package com.usian.feign;


import com.usian.pojo.TbContent;
import com.usian.pojo.TbContentCategory;
import com.usian.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient("usian-content-service")
public interface ItemContentFeign {

    @RequestMapping("contentcategory/selectContentCategoryByParentId")
    Result selectContentCategoryByParentId(@RequestParam Long id);

    @RequestMapping("contentcategory/selectTbContentAllByCategoryId")
    Result selectTbContentAllByCategoryId(@RequestParam Long categoryId);

    @RequestMapping("contentcategory/insertTbContent")
    Result insertTbContent(@RequestBody TbContent tbContent);

    @RequestMapping("contentcategory/deleteContentByIds")
    Result deleteContentByIds(@RequestParam Long ids);

    @RequestMapping("contentcategory/insertContentCategory")
    Result insertContentCategory(@RequestBody TbContentCategory tbContentCategory);

    @RequestMapping("contentcategory/updateContentCategory")
    Result updateContentCategory(@RequestBody TbContentCategory tbContentCategory);

    @RequestMapping("contentcategory/deleteContentCategoryById")
    Result deleteContentCategoryById(@RequestParam Long categoryId);
}
