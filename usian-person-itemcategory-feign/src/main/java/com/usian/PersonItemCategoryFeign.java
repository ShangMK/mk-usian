package com.usian;

import com.usian.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("usian-person-itemcategory-service")
public interface PersonItemCategoryFeign {

    @RequestMapping("personservice/selectItemCategoryAll")
    Result selectItemCategoryAll();

    @RequestMapping("personservice/selectFrontendContentByAD")
    Result selectFrontendContentByAD();

    @RequestMapping("seachservice/list")
    List<?> list(@RequestParam String q);
}
