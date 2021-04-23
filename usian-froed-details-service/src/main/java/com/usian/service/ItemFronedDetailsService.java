package com.usian.service;

import com.usian.mapper.TbItemDescMapper;
import com.usian.mapper.TbItemMapper;
import com.usian.mapper.TbItemParamItemMapper;
import com.usian.pojo.TbItem;
import com.usian.pojo.TbItemDesc;
import com.usian.pojo.TbItemParamItem;
import com.usian.pojo.TbItemParamItemExample;
import com.usian.redisconfig.RedisClient;
import com.usian.utils.JsonUtils;
import com.usian.utils.Result;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.resource.ClientResources;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemFronedDetailsService {
    @Autowired
    TbItemMapper tbItemMapper;
    @Autowired
    TbItemDescMapper tbItemDescMapper;
    @Autowired
    TbItemParamItemMapper tbItemParamItemMapper;

    @Autowired
    RedisClient redisClient;

    public Result selectItemInfo(Long itemId) {
        try {
            if (!redisClient.exists("item")){
                TbItem tbItemex=null;
                List<TbItem> tbItems = tbItemMapper.selectByExample(null);
                for (TbItem tbItem : tbItems) {
                    if (tbItem.getId() == itemId) { tbItemex = tbItem; }
                    redisClient.hset("item", "ITEM_INFO:" + tbItem.getId() + ":BASE", JsonUtils.objectToJson(tbItem));
                }
                redisClient.expire("item", 86400L);
                return Result.ok(tbItemex);
            }
            else{
                Object item = redisClient.hget("item", "ITEM_INFO:" + itemId + ":BASE");
                if (item == null) {
                    return Result.ok(null);
                }
                TbItem tbItem = JsonUtils.jsonToPojo((String) item, TbItem.class);
                return Result.ok(tbItem);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("失败");
        }
    }

    public Result selectItemDescByItemId(Long itemId) {
        try {
            if (!redisClient.exists("itemdesc")) {
                TbItemDesc tbItem1 =null;
                List<TbItemDesc> tbItemDescs = tbItemDescMapper.selectByExampleWithBLOBs(null);
                for (TbItemDesc tbItem : tbItemDescs) {
                    if (tbItem.getItemId()==itemId){tbItem1=tbItem;}
                    redisClient.hset("itemdesc", "ITEM_INFO:" + tbItem.getItemId() + ":DESC", JsonUtils.objectToJson(tbItem));
                }
                redisClient.expire("itemdesc", 86400L);
                System.out.println("mysql--desc");
                return Result.ok(tbItem1);
            }
            else {
                Object item = redisClient.hget("itemdesc", "ITEM_INFO:" + itemId + ":DESC");
                if (item == null) {
                    return Result.ok(null);
                }
                TbItemDesc tbItemDesc = JsonUtils.jsonToPojo((String) item, TbItemDesc.class);
                return Result.ok(tbItemDesc);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("失败");
        }
    }

    public Result selectTbItemParamItemByItemId(String itemId) {
        try {
            if (!redisClient.exists("tbitemparamitem")) {
                TbItemParamItem tbItemParamItemex=null;
                List<TbItemParamItem> tbItemParamItems = tbItemParamItemMapper.selectByExampleWithBLOBs(null);
                for (TbItemParamItem tbItemParamItem : tbItemParamItems) {
                    if (tbItemParamItem.getItemId() == Long.parseLong(itemId)) { tbItemParamItemex = tbItemParamItem; }
                    redisClient.hset("tbitemparamitem", "ITEM_INFO:" + tbItemParamItem.getItemId() + ":PARAM", JsonUtils.objectToJson(tbItemParamItem));
                }
                redisClient.expire("tbitemparamitem", 86400L);
                return Result.ok(tbItemParamItemex);
            }
            else{
                Object item = redisClient.hget("tbitemparamitem", "ITEM_INFO:" + itemId + ":PARAM");
                if (item == null) {
                    return Result.ok(null);
                }
                TbItemParamItem tbItemParamItem = JsonUtils.jsonToPojo((String) item, TbItemParamItem.class);
                return Result.ok(tbItemParamItem);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("失败");
        }
    }
}
