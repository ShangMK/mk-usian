package com.usian.interceptorconfig;

import com.usian.redisconfig.RedisClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class UserIntercept implements HandlerInterceptor {
    @Autowired
    RedisClient redisClient;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getParameter("token");
        if (StringUtils.isEmpty(token)) {
            return false;
        }
        Object token1 = redisClient.hget("token", token);
        if (StringUtils.isEmpty(String.valueOf(token))) {
            return false;
        }
         return true;
    }
}
