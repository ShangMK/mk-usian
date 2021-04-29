package com.usian.controller;


import com.usian.pojo.TbItem;
import com.usian.pojo.TbOrderItem;
import com.usian.service.CatService;
import com.usian.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("cartservice")
@RestController
public class CartServiceController {
    @Autowired
    CatService catService;

    @RequestMapping("addItem")
    public Result addItem(@RequestBody TbOrderItem tbOrderItem) {
        return catService.addItem(tbOrderItem);
    }


    @RequestMapping("showCart")
    public Result showCart( String userId) {
        return catService.showCart(userId);
    }


    @RequestMapping("selectTbitemid")
    public TbItem selectTbitemid(Long itemid) {
        return catService.selectTbitemid(itemid);
    }

    @RequestMapping("updateItemNum")
    public Result updateItemNum(@RequestBody  TbOrderItem tbOrderItem) {
        return catService.updateItemNum(tbOrderItem);
    }


    @RequestMapping("deleteItemFromCart")
    public Result deleteItemFromCart( @RequestBody TbOrderItem tbOrderItem) {
        return catService.deleteItemFromCart(tbOrderItem);
    }

}
