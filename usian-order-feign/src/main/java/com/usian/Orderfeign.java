package com.usian;

import com.usian.pojo.OrderINfo;
import com.usian.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("usian-order-service")
public interface Orderfeign {


    @RequestMapping("orderservice/goSettlements")
    Result goSettlements(@RequestParam String[] ids,@RequestParam  String userId,@RequestParam  String token);

    @RequestMapping("orderservice/insertOrder")
    Result insertOrder(@RequestBody  OrderINfo orderINfo,@RequestParam String userId);
}
