package com.usian.feign;


import com.usian.pojo.TbItem;
import com.usian.utils.PageResult;
import com.usian.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;


@FeignClient("usian-froned-details-service")
public interface ItemDetailsFeign {
    @RequestMapping("foneddetails/selectItemInfo")
    Result selectItemInfo(@RequestParam Long itemId);

    @RequestMapping("foneddetails/selectItemDescByItemId")
    Result selectItemDescByItemId(@RequestParam Long itemId);

    @RequestMapping("foneddetails/selectTbItemParamItemByItemId")
    Result selectItemByItemId(@RequestBody String itemId);
}
