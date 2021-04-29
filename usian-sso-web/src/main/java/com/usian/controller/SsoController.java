package com.usian.controller;


import com.usian.feign.SsoFeign;
import com.usian.pojo.TbItem;
import com.usian.pojo.TbUser;
import com.usian.redisconfig.RedisClient;
import com.usian.utils.CookieUtils;
import com.usian.utils.JsonUtils;
import com.usian.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("frontend/sso")
public class SsoController {

    @Autowired
    SsoFeign ssoFeign;

    @Autowired
    RedisClient redisClient;
    private static final String REDIS_SHOP = "SHOPING";

    private static final String COOKIE_NAME = "TBITEMCC";

    /**
     * 用户注册方法
     * @param tbUser
     * @return
     */
    @RequestMapping("userRegister")
    public Result userRegister(TbUser tbUser){
        TbUser tbUser1 = tbUser;
        return ssoFeign.userRegister(tbUser1);
    }

    /**
     * 注册信息校验
     * @param checkValue
     * @param checkFlag
     * @return
     */
    @RequestMapping("checkUserInfo/{checkValue}/{checkFlag}")
    public Result checkUserInfo(@PathVariable String checkValue,@PathVariable Integer checkFlag){
        return ssoFeign.checkUserInfo(checkValue,checkFlag);
    }

    /**
     * 登录信息校验
     * @param tbUser
     * @param request
     * @return
     */
    @RequestMapping("userLogin")
    public Result userLogin(TbUser tbUser, HttpServletRequest request){
        TbUser tbUser1 = tbUser;
        Result result = ssoFeign.userLogin(tbUser1);
        if (result!=null && result.getStatus()==200) {
            TbUser user = ssoFeign.findByname(tbUser.getUsername());
            String cookieValue = CookieUtils.getCookieValue(request, COOKIE_NAME, true);
            if (!StringUtils.isEmpty(cookieValue)) {
                HashMap<String, TbItem> shopmap = (HashMap<String, TbItem>) JsonUtils.jsonToMap(cookieValue, TbItem.class);

            Object hget = redisClient.hget(REDIS_SHOP, user.getId().toString());
            if (StringUtils.isEmpty(hget)){
                redisClient.hset(REDIS_SHOP, user.getId().toString(), JsonUtils.objectToJson(shopmap));
                //CookieUtils.deleteCookie(request,response,COOKIE_NAME);
                System.out.println("测试删除1");
            }else{
                Map<String,TbItem> shopmap2 = JsonUtils.jsonToMap((String) hget, TbItem.class);
                Collection<TbItem> values = shopmap.values();
                for (TbItem value : values) {
                    shopmap2.put(value.getId().toString(), value);
                }
                redisClient.hset(REDIS_SHOP, user.getId().toString(), JsonUtils.objectToJson(shopmap));
                // CookieUtils.deleteCookie(request,response,COOKIE_NAME);
                System.out.println("测试删除2");
            }
            }
        }
        return result;
    }

    /**
     * 登出
     * @param token
     * @return
     */
    @RequestMapping("logOut")
    public Result logOut(String token){
        return ssoFeign.logOut(token);
    }

    /**
     * 获取token进行首页名字回显
     * @param token
     * @return
     */
    @RequestMapping("getUserByToken/{token}")
    public Result getUserByToken(@PathVariable String token){
        return ssoFeign.getUserByToken(token);
    }
}
