package com.usian.service;

import com.usian.mapper.TbItemDescMapper;
import com.usian.mapper.TbItemMapper;
import com.usian.mapper.TbItemParamItemMapper;
import com.usian.pojo.TbItem;
import com.usian.pojo.TbItemDesc;
import com.usian.pojo.TbItemParamItem;
import com.usian.pojo.TbItemParamItemExample;
import com.usian.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ItemFronedDetailsService {
    @Autowired
    TbItemMapper tbItemMapper;
    @Autowired
    TbItemDescMapper tbItemDescMapper;
    @Autowired
    TbItemParamItemMapper tbItemParamItemMapper;

    @Value("${PROTAL_CATRESULT_KEY}")
    private String portal_catresult_redis_key;

    public Result selectItemInfo(Long itemId) {
        try {
            TbItem tbItem = tbItemMapper.selectByPrimaryKey(itemId);
            return Result.ok(tbItem);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("失败");
        }
    }

    public Result selectItemDescByItemId(Long itemId) {
        try {
            TbItemDesc tbItem = tbItemDescMapper.selectByPrimaryKey(itemId);
            return Result.ok(tbItem);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("失败");
        }
    }

    public Result selectTbItemParamItemByItemId(String itemId) {
        try {
            TbItemParamItemExample tbItemParamItemExample = new TbItemParamItemExample();
            TbItemParamItemExample.Criteria criteria = tbItemParamItemExample.createCriteria();
            criteria.andItemIdEqualTo(Long.parseLong(itemId));
            List<TbItemParamItem> tbItem = tbItemParamItemMapper.selectByExampleWithBLOBs(tbItemParamItemExample);
            return Result.ok(tbItem.get(0));
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("失败");
        }
    }
}
