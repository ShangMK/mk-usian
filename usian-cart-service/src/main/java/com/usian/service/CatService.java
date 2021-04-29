package com.usian.service;

import com.alibaba.druid.support.json.JSONUtils;
import com.usian.mapper.TbItemMapper;
import com.usian.mapper.TbOrderItemMapper;
import com.usian.pojo.TbItem;
import com.usian.pojo.TbOrderItem;
import com.usian.pojo.TbOrderItemExample;
import com.usian.redisconfig.RedisClient;
import com.usian.utils.JsonUtils;
import com.usian.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
public class CatService {

    @Autowired
    TbItemMapper tbItemMapper;
    @Autowired
    TbOrderItemMapper tbOrderItemMapper;
    @Autowired
    RedisClient redisClient;

    private static final String REDIS_SHOP = "SHOPING";


    public Result addItem(TbOrderItem tbOrderItem) {
        try {Map<String,TbItem> map;
            Object hget = redisClient.hget(REDIS_SHOP, tbOrderItem.getOrderId());
            if (StringUtils.isEmpty(hget)) {
                map = new HashMap<>();
            } else {
                map= JsonUtils.jsonToMap((String) hget, TbItem.class);
            }


            TbItem tbItem = selectTbitemid(Long.parseLong(tbOrderItem.getItemId()));
            tbItem.setNum(tbOrderItem.getNum());

            map.put(tbOrderItem.getItemId(), tbItem);
            redisClient.hset(REDIS_SHOP, tbOrderItem.getOrderId(), JsonUtils.objectToJson(map));
            return Result.ok();
        } catch (Exception e) {
            return Result.error("添加错误");
        }
    }

    public Result showCart(String userId) {
        try {
            Object hget = redisClient.hget(REDIS_SHOP, userId);
            if (StringUtils.isEmpty(hget))return null;

            Map<String,TbItem> map = JsonUtils.jsonToMap((String) hget, TbItem.class);

            Collection<TbItem> values = map.values();

            return Result.ok(values);
        } catch (Exception e) {
            return Result.error("添加错误");
        }
    }

    public TbItem selectTbitemid(Long itemid) {
        return tbItemMapper.selectByPrimaryKey(itemid);
    }


    public Result updateItemNum(TbOrderItem tbOrderItem) {
        try {
            Object hget = redisClient.hget(REDIS_SHOP, tbOrderItem.getOrderId());
            if (StringUtils.isEmpty(hget))return null;
            Map<String,TbItem> map = JsonUtils.jsonToMap((String) hget, TbItem.class);
            for (String s : map.keySet()) {
                TbItem mapTbOrderitem = map.get(s);
                if (tbOrderItem.getItemId().equals(mapTbOrderitem.getId().toString())) mapTbOrderitem.setNum(tbOrderItem.getNum());
            }
            redisClient.hset(REDIS_SHOP, tbOrderItem.getOrderId(), JsonUtils.objectToJson(map));
            return Result.ok();
        } catch (Exception e) {
            return Result.error("添加错误");
        }
    }

    public Result deleteItemFromCart(TbOrderItem tbOrderItem) {
        try {
            Object hget = redisClient.hget(REDIS_SHOP, tbOrderItem.getOrderId());
            if (StringUtils.isEmpty(hget))return null;
            Map<String,TbItem> map = JsonUtils.jsonToMap((String) hget, TbItem.class);
            for (String s : map.keySet()) {
                TbItem mapTbOrderitem = map.get(s);
                if (tbOrderItem.getItemId().equals(mapTbOrderitem.getId().toString())) map.remove(s);
            }
            redisClient.hset(REDIS_SHOP, tbOrderItem.getOrderId(), JsonUtils.objectToJson(map));
            return Result.ok();
        } catch (Exception e) {
            return Result.error("添加错误");
        }
    }
}
