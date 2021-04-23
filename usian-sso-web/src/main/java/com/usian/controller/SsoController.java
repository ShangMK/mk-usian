package com.usian.controller;


import com.usian.feign.SsoFeign;
import com.usian.pojo.TbUser;
import com.usian.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("frontend/sso")
public class SsoController {

    @Autowired
    SsoFeign ssoFeign;

    @RequestMapping("userRegister")
    public Result userRegister(TbUser tbUser){
        TbUser tbUser1 = tbUser;
        return ssoFeign.userRegister(tbUser1);
    }

    @RequestMapping("checkUserInfo/{checkValue}/{checkFlag}")
    public Result checkUserInfo(@PathVariable String checkValue,@PathVariable Integer checkFlag){
        return ssoFeign.checkUserInfo(checkValue,checkFlag);
    }
    @RequestMapping("userLogin")
    public Result userLogin(TbUser tbUser){
        TbUser tbUser1 = tbUser;
        return ssoFeign.userLogin(tbUser1);
    }
    @RequestMapping("logOut")
    public Result logOut(String token){
        return ssoFeign.logOut(token);
    }
    @RequestMapping("getUserByToken/{token}")
    public Result getUserByToken(@PathVariable String token){
        return ssoFeign.getUserByToken(token);
    }
}
