package com.usian.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.usian.mapper.TbItemCatMapper;
import com.usian.mapper.TbItemDescMapper;
import com.usian.mapper.TbItemMapper;
import com.usian.mapper.TbItemParamItemMapper;
import com.usian.pojo.*;
import com.usian.utils.IDUtils;
import com.usian.utils.PageResult;
import com.usian.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ItemService {
    @Autowired
    TbItemMapper tbItemMapper;
    @Autowired
    TbItemCatMapper tbItemCatMapper;
    @Autowired
    TbItemDescMapper tbItemDescMapper;
    @Autowired
    TbItemParamItemMapper tbItemParamItemMapper;

    public Result selectTbItemAllByPage(Integer page,Integer rows) {
        Result result = new Result();
        try {
            PageHelper.startPage(page,rows);
            TbItemExample tbItemExample=new TbItemExample();
            tbItemExample.setOrderByClause("updated desc");
            TbItemExample.Criteria criteria = tbItemExample.createCriteria();
            criteria.andStatusEqualTo((byte)1);
            List<TbItem> tbItems = tbItemMapper.selectByExample(tbItemExample);
            PageInfo<TbItem> tbItemPageInfo = new PageInfo<>(tbItems);

            /*Page<TbItem> page1=tbItemMapper.findall();*/
            PageResult pageResult = new PageResult();
            pageResult.setPageIndex(page);
            pageResult.setResult(tbItemPageInfo.getList());
            pageResult.setTotalPage(tbItemPageInfo.getTotal());
            result.setStatus(200);
            result.setMsg("成功");
            result.setData(pageResult);
        }
        catch (Exception e){
            result.setStatus(400);
            result.setMsg("错误");
        }
        return result;
    }

    public Result insertTbItem(TbItem tbItem,String desc,String itemParams) {
        Date date = new Date();
        try {
            long id = IDUtils.genItemId();
            tbItem.setId(id);
            tbItem.setCreated(date);
            tbItem.setUpdated(date);
            tbItem.setStatus((byte)1);
            tbItemMapper.insertSelective(tbItem);


            TbItemDesc tbItemDesc = new TbItemDesc();
            tbItemDesc.setItemId(id);
            tbItemDesc.setItemDesc(desc);
            tbItemDesc.setCreated(date);
            tbItemDesc.setUpdated(date);
            tbItemDescMapper.insertSelective(tbItemDesc);

            TbItemParamItem tbItemParamItem = new TbItemParamItem();
            tbItemParamItem.setItemId(id);
            tbItemParamItem.setParamData(itemParams);
            tbItemParamItem.setCreated(date);
            tbItemParamItem.setUpdated(date);
            tbItemParamItemMapper.insertSelective(tbItemParamItem);
            return Result.ok("成功");
        }
        catch (Exception e){
            return Result.error("失败");
        }
    }

    public Result preUpdateItem(Long itemId) {
        Result result = new Result();
        ItemPreUpdate itemPreUpdate = new ItemPreUpdate();
        try {
            TbItem tbItem = tbItemMapper.selectByPrimaryKey(itemId);
            itemPreUpdate.setItem(tbItem);

            TbItemCat tbItemCat = tbItemCatMapper.selectByPrimaryKey(tbItem.getCid());
            itemPreUpdate.setItemCat(tbItemCat.getName());

            TbItemDesc tbItemDesc = tbItemDescMapper.selectByPrimaryKey(itemId);
            itemPreUpdate.setItemDesc(tbItemDesc.getItemDesc());

            TbItemParamItemExample tbItemParamItemExample = new TbItemParamItemExample();
            TbItemParamItemExample.Criteria criteria = tbItemParamItemExample.createCriteria();
            TbItemParamItemExample.Criteria criteria1 = criteria.andItemIdEqualTo(itemId);
            List<TbItemParamItem> tbItemParamItems = tbItemParamItemMapper.selectByExampleWithBLOBs(tbItemParamItemExample);
            if (tbItemParamItems.size() > 0) {
                itemPreUpdate.setItemParamItem(tbItemParamItems.get(0).getParamData());
            }else {
                itemPreUpdate.setItemParamItem("");
            }
            result.setStatus(200);
            result.setMsg("成功");
            result.setData(itemPreUpdate);
        }
        catch (Exception e){
            result.setStatus(400);
            result.setMsg("错误");
        }
        return result;
    }

    public Result updateTbItem(TbItem tbItem,String desc,String itemParams) {
        Result result = new Result();
        Date date = new Date();
        try {
            tbItem.setUpdated(date);
            tbItemMapper.updateByPrimaryKeySelective(tbItem);


            TbItemDesc tbItemDesc = new TbItemDesc();
            tbItemDesc.setItemId(tbItem.getId());
            tbItemDesc.setItemDesc(desc);
            tbItemDesc.setUpdated(date);
            tbItemDescMapper.updateByPrimaryKeySelective(tbItemDesc);

            TbItemParamItemExample tbItemParamItemExample = new TbItemParamItemExample();
            TbItemParamItemExample.Criteria criteria = tbItemParamItemExample.createCriteria();
            TbItemParamItemExample.Criteria criteria1 = criteria.andItemIdEqualTo(tbItem.getId());
            List<TbItemParamItem> tbItemParamItems = tbItemParamItemMapper.selectByExampleWithBLOBs(tbItemParamItemExample);
            TbItemParamItem tbItemParamItem = new TbItemParamItem();
            tbItemParamItem.setParamData(itemParams);
            tbItemParamItem.setUpdated(date);
            tbItemParamItem.setId(tbItemParamItems.get(0).getId());
            tbItemParamItemMapper.updateByPrimaryKeySelective(tbItemParamItem);

            result.setStatus(200);
            result.setMsg("成功");
        }
        catch (Exception e){
            result.setStatus(400);
            result.setMsg("错误");
        }
        return result;
    }

    public Result deleteItemById(Long itemId) {
        Result result = new Result();
        try {
            TbItem tbItem = new TbItem();
            tbItem.setId(itemId);
            tbItem.setStatus((byte)0);
            tbItemMapper.updateByPrimaryKeySelective(tbItem);
            result.setStatus(200);
            result.setMsg("成功");
        }
        catch (Exception e){
            result.setStatus(400);
            result.setMsg("错误");
        }
        return result;
    }
}
