package com.usian.feign;

import com.usian.pojo.TbUser;
import com.usian.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("usian-sso-service")
public interface SsoFeign {
    @RequestMapping("ssoservice/userRegister")
    Result userRegister(@RequestBody TbUser tbUser);

    @RequestMapping("ssoservice/checkUserInfo")
    Result checkUserInfo(@RequestParam String checkValue,@RequestParam Integer checkFlag);

    @RequestMapping("ssoservice/userLogin")
    Result userLogin(@RequestBody TbUser tbUser);

    @RequestMapping("ssoservice/logOut")
    Result logOut(@RequestParam String token);

    @RequestMapping("ssoservice/getUserByToken")
    Result getUserByToken(@RequestParam String token);
}
