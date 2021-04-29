package com.usian.controller;

import com.usian.pojo.OrderINfo;
import com.usian.service.OrderService;
import com.usian.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("orderservice")
@RestController
public class OrderServiceController {
    @Autowired
    OrderService orderService;

    @RequestMapping("/goSettlements")
    public Result goSettlements(@RequestParam String[] ids, @RequestParam  String userId, @RequestParam  String token){
        return orderService.goSettlements(ids, userId, token);
    };

    @RequestMapping("/insertOrder")
    public Result insertOrder(@RequestBody OrderINfo orderINfo, String userId){
        return orderService.insertOrder(orderINfo,userId);
    };
}
