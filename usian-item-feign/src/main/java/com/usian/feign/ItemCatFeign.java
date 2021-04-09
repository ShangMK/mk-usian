package com.usian.feign;


import com.usian.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient("usian-item-service")
public interface ItemCatFeign {
    @RequestMapping("itemcat/selectItemCategoryByParentId")
    Result selectItemCategoryByParentId();
}
