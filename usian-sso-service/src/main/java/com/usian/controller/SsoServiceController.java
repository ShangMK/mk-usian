package com.usian.controller;

import com.usian.pojo.TbUser;
import com.usian.service.SsoService;
import com.usian.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("ssoservice")
public class SsoServiceController {

    @Autowired
    SsoService ssoService;

    @RequestMapping("userRegister")
    public Result userRegister(@RequestBody TbUser tbUser) {
        return ssoService.userRegister(tbUser);
    }

    @RequestMapping("checkUserInfo")
    public Result checkUserInfo(@RequestParam String checkValue, @RequestParam Integer checkFlag) {
        return ssoService.checkUserInfo(checkValue,checkFlag);
    }
    @RequestMapping("userLogin")
    public Result userLogin(@RequestBody TbUser tbUser){
        return ssoService.userLogin(tbUser);
    }
    @RequestMapping("logOut")
    public Result logOut(@RequestParam String token){
        return ssoService.logOut(token);
    }
    @RequestMapping("getUserByToken")
    public Result getUserByToken(@RequestParam String token){
        return ssoService.getUserByToken(token);
    }
}
