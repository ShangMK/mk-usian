package com.usian.controller;

import com.usian.feign.CatFeign;
import com.usian.pojo.TbItem;
import com.usian.pojo.TbOrderItem;
import com.usian.utils.CookieUtils;
import com.usian.utils.JsonUtils;
import com.usian.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("frontend/cart")
public class CartController {
    @Autowired
    CatFeign catFeign;
    private static final String COOKIE_NAME = "TBITEMCC";
    private static final String CART_COOKIE_EXPIRE = "604800";

    /**
     * 添加购物车
     * @param num
     * @param userId
     * @param itemId
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("addItem")
    public Result addItem(@RequestParam(defaultValue = "1") Integer num,@RequestParam String userId, @RequestParam String itemId, HttpServletRequest request, HttpServletResponse response) {
        if (StringUtils.isEmpty(userId)) {
            try {
                String cookieValue = CookieUtils.getCookieValue(request, COOKIE_NAME, true);

                Map<String, TbItem> map;
                if (!StringUtils.isEmpty(cookieValue)) {
                    map = (HashMap<String, TbItem>) JsonUtils.jsonToMap(cookieValue, TbItem.class);
                }else {
                    map= new HashMap<>();
                }
                //添加商品
                Map<String, TbItem> hashMap = addtbitem(map, Long.parseLong(itemId), num);
                //将购物车添加到cookie
                String s = JsonUtils.objectToJson(hashMap);
                CookieUtils.setCookie(request,response,COOKIE_NAME, s,Integer.parseInt(CART_COOKIE_EXPIRE),true);
                return Result.ok();
            } catch (Exception e) {
                e.printStackTrace();
                return Result.error("错误");
            }
        } else {
            TbOrderItem tborderItem = getTborderItem(itemId, userId, num);
            return catFeign.addItem(tborderItem);
        }
    }

    /**
     * 查看购物车
     * @param userId
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("showCart")
    public Result addItem(@RequestParam String userId, HttpServletRequest request, HttpServletResponse response) {
        if (!StringUtils.isEmpty(userId)){
            return catFeign.showCart(userId);
        }else {
            try {
                String cookieValue = CookieUtils.getCookieValue(request, COOKIE_NAME, true);
                HashMap<String,TbItem> map = (HashMap<String, TbItem>) JsonUtils.jsonToMap(cookieValue, TbItem.class);
                ArrayList<Object> arrayList = new ArrayList<>();
                for (String s : map.keySet()) {
                    TbItem tbItem = map.get(s);
                    arrayList.add(tbItem);
                }
                return Result.ok(arrayList);
            } catch (Exception e) {
                return Result.error("错误");
            }
        }

    }

    /**
     * 修改购物车
     * @param num
     * @param userId
     * @param itemId
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("updateItemNum")
    public Result updateItemNum(@RequestParam(defaultValue = "1") Integer num,@RequestParam String userId, @RequestParam String itemId, HttpServletRequest request, HttpServletResponse response) {
        if (!StringUtils.isEmpty(userId)){
            TbOrderItem tborderItem = getTborderItem(itemId, userId, num);
            return catFeign.updateItemNum(tborderItem);
        }else {
            try {
                String cookieValue = CookieUtils.getCookieValue(request, COOKIE_NAME, true);
                HashMap<String,TbItem> map = (HashMap<String, TbItem>) JsonUtils.jsonToMap(cookieValue, TbItem.class);
                for (String s : map.keySet()) {
                    TbItem tbItem = map.get(s);
                    if (tbItem.getId() == Long.parseLong(itemId)) {
                        tbItem.setNum(num);
                    }
                }
                CookieUtils.setCookie(request,response,COOKIE_NAME,JsonUtils.objectToJson(map),Integer.parseInt(CART_COOKIE_EXPIRE),true);
                return Result.ok();
            } catch (Exception e) {
                return Result.error("错误");
            }
        }
    }

    /**
     * 删除购物车
     * @param userId
     * @param itemId
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("deleteItemFromCart")
    public Result deleteItemFromCart(@RequestParam String userId, @RequestParam String itemId, HttpServletRequest request, HttpServletResponse response) {
        if (!StringUtils.isEmpty(userId)){
            TbOrderItem tborderItem = getTborderItem(itemId, userId,1);
            return catFeign.deleteItemFromCart(tborderItem);
        }else {
            try {
                String cookieValue = CookieUtils.getCookieValue(request, COOKIE_NAME, true);
                HashMap<String,TbItem> map = (HashMap<String, TbItem>) JsonUtils.jsonToMap(cookieValue, TbItem.class);
                for (String s : map.keySet()) {
                    TbItem tbItem = map.get(s);
                    if (tbItem.getId() == Long.parseLong(itemId)) {
                        map.remove(s);
                    }
                }
                CookieUtils.setCookie(request,response,COOKIE_NAME,JsonUtils.objectToJson(map),Integer.parseInt(CART_COOKIE_EXPIRE),true);
                return Result.ok();
            } catch (Exception e) {
                return Result.error("错误");
            }

        }
    }


    /**
     * 根据参数封装对象
     * @param itemId
     * @param userId
     * @param num
     * @return
     */
    public TbOrderItem getTborderItem(String itemId,String userId,Integer num){
        TbOrderItem tbOrderItem = new TbOrderItem();
        tbOrderItem.setItemId(itemId);
        tbOrderItem.setOrderId(userId);
        tbOrderItem.setNum(num);
        return tbOrderItem;
    }

    /**
     * 根据参数查询封装前台cookie需要的map对象
     * @param cart
     * @param itemid
     * @param num
     * @return
     */
    public Map<String, TbItem> addtbitem(Map<String, TbItem> cart,Long itemid,Integer num) {
        TbItem tbItem=cart.get(itemid.toString());
            if (tbItem != null) {
                tbItem.setNum(num+tbItem.getNum());
            }else {
                tbItem = catFeign.selectTbitemid(itemid);
                tbItem.setNum(num);
            }
        cart.put(itemid.toString(),tbItem);
        return cart;
    }
}
