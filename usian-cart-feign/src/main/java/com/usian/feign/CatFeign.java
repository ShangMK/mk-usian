package com.usian.feign;

import com.usian.pojo.TbItem;
import com.usian.pojo.TbOrderItem;
import com.usian.pojo.TbUser;
import com.usian.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("usian-cat-service")
public interface CatFeign {

    @RequestMapping("cartservice/addItem")
    Result addItem(@RequestBody TbOrderItem tbOrderItem);

    @RequestMapping("cartservice/showCart")
    Result showCart(@RequestParam String userId);

    @RequestMapping("cartservice/selectTbitemid")
    TbItem selectTbitemid(@RequestParam Long itemid);

    @RequestMapping("cartservice/updateItemNum")
    Result updateItemNum(@RequestBody TbOrderItem tbOrderItem);

    @RequestMapping("cartservice/deleteItemFromCart")
    Result deleteItemFromCart(@RequestBody TbOrderItem tbOrderItem);


}
