package com.usian.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.usian.mapper.TbItemCatMapper;
import com.usian.mapper.TbItemDescMapper;
import com.usian.mapper.TbItemMapper;
import com.usian.pojo.ItemPreUpdate;
import com.usian.pojo.TbItem;
import com.usian.pojo.TbItemCat;
import com.usian.pojo.TbItemDesc;
import com.usian.utils.PageResult;
import com.usian.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ItemService {
    @Autowired
    TbItemMapper tbItemMapper;
    @Autowired
    TbItemCatMapper tbItemCatMapper;
    @Autowired
    TbItemDescMapper tbItemDescMapper;


    public Result selectTbItemAllByPage(Integer page) {
        Result result = new Result();
        try {
            PageHelper.startPage(page,5);
            Page<TbItem> page1=tbItemMapper.findall();
            PageResult pageResult = new PageResult();
            pageResult.setPageIndex(page);
            pageResult.setResult(page1.getResult());
            pageResult.setTotalPage(page1.getTotal());
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

    public Result insertTbItem(TbItem tbItem) {
        Result result = new Result();
        try {
            tbItem.setCreated(new Date());
            tbItem.setUpdated(new Date());
            tbItem.setStatus((byte)1);
            tbItemMapper.insertbyprimarykey(tbItem);

            String substring = tbItem.getTitle().substring(tbItem.getTitle().indexOf("-"));
            TbItemDesc tbItemDesc = new TbItemDesc();
            tbItemDesc.setItemId(tbItem.getId());
            tbItemDesc.setItemDesc(substring);
            tbItemDesc.setCreated(new Date());
            tbItemDesc.setUpdated(new Date());
            tbItemDescMapper.insertSelective(tbItemDesc);
            result.setStatus(200);
            result.setMsg("成功");
        }
        catch (Exception e){
            result.setStatus(400);
            result.setMsg("错误");
        }
        return result;
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
