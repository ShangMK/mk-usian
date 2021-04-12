package com.usian.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.usian.mapper.TbItemCatMapper;
import com.usian.mapper.TbItemDescMapper;
import com.usian.mapper.TbItemMapper;
import com.usian.mapper.TbItemParamItemMapper;
import com.usian.pojo.*;
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
        try {
            TbItemDesc tbItemDesc = new TbItemDesc();
            tbItemDesc.setItemId(tbItem.getId());
            tbItemDesc.setItemDesc(desc);
            tbItemDesc.setCreated(new Date());
            tbItemDesc.setUpdated(new Date());
            tbItemDescMapper.insertSelective(tbItemDesc);

            tbItem.setCreated(new Date());
            tbItem.setUpdated(new Date());
            tbItem.setStatus((byte)1);
            tbItemMapper.insertSelective(tbItem);

            TbItemParamItem tbItemParamItem = new TbItemParamItem();
            tbItemParamItem.setItemId(tbItem.getId());
            tbItemParamItem.setParamData(itemParams);
            tbItemParamItem.setCreated(new Date());
            tbItemParamItem.setUpdated(new Date());
            tbItemParamItemMapper.insertSelective(tbItemParamItem);
            return Result.ok("成功");
        }
        catch (Exception e){
            return Result.error("失败");
        }
    }

    public Result preUpdateItem(Long itemId) {
        Result result = new Result();
        try {
            TbItemDesc tbItemDesc = tbItemDescMapper.selectByPrimaryKey(itemId);
            TbItem tbItem = tbItemMapper.selectByPrimaryKey(itemId);
            TbItemCat tbItemCat = tbItemCatMapper.selectByPrimaryKey(tbItem.getCid());
            ItemPreUpdate itemPreUpdate = new ItemPreUpdate();
            itemPreUpdate.setItem(tbItem);
            itemPreUpdate.setItemCat(tbItemCat.getName());
            itemPreUpdate.setItemDesc(tbItemDesc.getItemDesc());
            itemPreUpdate.setItemParamItem("length");
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

    public Result updateTbItem(TbItem tbItem) {
        Result result = new Result();
        try {
            tbItem.setUpdated(new Date());
            tbItem.setStatus((byte)1);
            tbItemMapper.updateByPrimaryKeySelective(tbItem);
            if (tbItem.getBarcode() != null) {
                TbItemDesc tbItemDesc = new TbItemDesc();
                tbItemDesc.setItemId(tbItem.getId());
                tbItemDesc.setItemDesc(tbItem.getBarcode());
                tbItemDesc.setCreated(new Date());
                tbItemDesc.setUpdated(new Date());
                tbItemDescMapper.updateByPrimaryKeySelective(tbItemDesc);

            }
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
            tbItemMapper.deleteByPrimaryKey(itemId);
            tbItemDescMapper.deleteByPrimaryKey(itemId);
            TbItemParamItemExample tbItemParamItemExample = new TbItemParamItemExample();
            TbItemParamItemExample.Criteria criteria = tbItemParamItemExample.createCriteria();
            criteria.andItemIdEqualTo(itemId);
            tbItemParamItemMapper.deleteByExample(tbItemParamItemExample);
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
