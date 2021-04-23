package com.usian.feign;

import com.usian.pojo.SearchItem;
import com.usian.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("usian-seach-service")
public interface SeachFeign {

    @RequestMapping("SeachService/importAll")
    Result importAll();

    @RequestMapping("SeachService/list")
    List<SearchItem> list(@RequestParam String q, @RequestParam Long page, @RequestParam Integer pageSize);
}
