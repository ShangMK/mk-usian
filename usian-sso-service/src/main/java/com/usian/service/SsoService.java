package com.usian.service;

import com.usian.mapper.TbUserMapper;
import com.usian.pojo.TbUser;
import com.usian.pojo.TbUserExample;
import com.usian.redisconfig.RedisClient;
import com.usian.utils.JsonUtils;
import com.usian.utils.MD5Utils;
import com.usian.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Service
public class SsoService {
    @Autowired
    TbUserMapper tbUserMapper;
    @Autowired
    RedisClient redisClient;
    @Value("${EXPRIE}")

    Long EXPRIE;
    public Result userRegister(TbUser tbUser) {
        try {
            String digest = MD5Utils.digest(tbUser.getPassword());
            Date date = new Date();
            tbUser.setCreated(date);
            tbUser.setUpdated(date);
            tbUser.setPassword(digest);
            tbUserMapper.insertSelective(tbUser);
            return Result.ok();
        } catch (Exception e) {
            return Result.error("500");
        }
    }

    public Result checkUserInfo(String checkValue, Integer checkFlag) {
        TbUserExample tbUserExample = new TbUserExample();
        TbUserExample.Criteria criteria = tbUserExample.createCriteria();
        if (checkFlag == 1) {
            criteria.andUsernameEqualTo(checkValue);
        } else {
            criteria.andPhoneEqualTo(checkValue);
        }
        List<TbUser> tbUsers = tbUserMapper.selectByExample(tbUserExample);
        if (tbUsers != null && tbUsers.size() > 0) {
            return Result.error("错误");
        }else{
            return Result.ok(true);
        }
    }

    public Result userLogin(TbUser tbUser) {
        TbUserExample tbUserExample = new TbUserExample();
        TbUserExample.Criteria criteria = tbUserExample.createCriteria();
        criteria.andUsernameEqualTo(tbUser.getUsername());
        String digest = MD5Utils.digest(tbUser.getPassword());
        criteria.andPasswordEqualTo(digest);
        List<TbUser> tbUsers = tbUserMapper.selectByExample(tbUserExample);
        if (tbUsers != null && tbUsers.size() > 0) {
            TbUser user = tbUsers.get(0);

            String token = UUID.randomUUID().toString();
            redisClient.hset("token",token, JsonUtils.objectToJson(user));
            redisClient.expire(token,EXPRIE);

            HashMap<Object, Object> map = new HashMap<>();
            map.put("token", token);
            map.put("userid", user.getId());
            map.put("username", user.getUsername());
            return Result.ok(map);
        } else {
            return null;
        }
    }

    public Result logOut(String token) {
        try {
            redisClient.del("token");
            return Result.ok();
        } catch (Exception e) {
            return Result.error("错误");
        }
    }

    public Result getUserByToken(String token) {
        try {
            Object o = redisClient.hget("token",token);
            TbUser tbUser = JsonUtils.jsonToPojo((String) o, TbUser.class);
            return Result.ok(tbUser);
        } catch (Exception e) {
            return Result.error("错误");
        }
    }
}
