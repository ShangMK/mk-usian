package com.usian.controller;

import com.usian.Orderfeign;
import com.usian.pojo.*;
import com.usian.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("frontend/orders")
@RestController
public class OrderController {

    @Autowired
    Orderfeign orderfeign;


    /**
     * 结算实现方法，
     * @param ids
     * @param userId
     * @param token
     * @return
     */
    @RequestMapping("goSettlement")
    public Result goSettlement(String[] ids, String userId, String token) {
        return orderfeign.goSettlements(ids, userId, token);
    }

    @RequestMapping("insertOrder")
    public Result insertOrder(String orderItem, TbOrder tbOrder , TbOrderShipping tbOrderShipping,String userId) {
        OrderINfo orderINfo = new OrderINfo();
        orderINfo.setV(orderItem);
        orderINfo.setTbOrder(tbOrder);
        orderINfo.setTbOrderShipping(tbOrderShipping);
        return orderfeign.insertOrder(orderINfo,userId);
    }


}
