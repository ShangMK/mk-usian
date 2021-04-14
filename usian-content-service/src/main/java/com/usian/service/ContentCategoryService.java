package com.usian.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.usian.mapper.TbContentCategoryMapper;
import com.usian.mapper.TbContentMapper;
import com.usian.pojo.TbContent;
import com.usian.pojo.TbContentCategory;
import com.usian.pojo.TbContentCategoryExample;
import com.usian.pojo.TbContentExample;
import com.usian.utils.PageResult;
import com.usian.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ContentCategoryService {
    @Autowired
    TbContentCategoryMapper tbContentCategoryMapper;
    @Autowired
    TbContentMapper tbContentMapper;

    public Result selectContentCategoryByParentId(Long id) {
        try {
            TbContentCategoryExample tbContentCategoryExample = new TbContentCategoryExample();
            TbContentCategoryExample.Criteria criteria = tbContentCategoryExample.createCriteria();
            criteria.andParentIdEqualTo(id);
            List<TbContentCategory> tbContentCategories = tbContentCategoryMapper.selectByExample(tbContentCategoryExample);
            return Result.ok(tbContentCategories);
        } catch (Exception e) {
            return Result.error("memory leak");
        }
    }

    public Result selectTbContentAllByCategoryId(Long categoryId) {
        PageHelper.startPage(1, 10);
        try {
            TbContentExample tbContentExample = new TbContentExample();
            TbContentExample.Criteria criteria = tbContentExample.createCriteria();
            TbContentExample.Criteria criteria1 = criteria.andCategoryIdEqualTo(categoryId);

            List<TbContent> tbContents = tbContentMapper.selectByExampleWithBLOBs(tbContentExample);
            PageInfo<TbContent> tbContentPageInfo = new PageInfo<TbContent>(tbContents);
            PageResult pageResult = new PageResult();
            pageResult.setPageIndex(1);
            pageResult.setResult(tbContentPageInfo.getList());
            pageResult.setTotalPage(tbContentPageInfo.getTotal());
            return Result.ok(pageResult);
        } catch (Exception e) {
            return Result.error("memory leak");
        }
    }

    public Result insertTbContent(TbContent tbContent) {
        try {
            Date date = new Date();
            tbContent.setCreated(date);
            tbContent.setUpdated(date);
            tbContentMapper.insertSelective(tbContent);
            return Result.ok();
        } catch (Exception e) {
            return Result.error("SHow leak");
        }
    }

    public Result deleteContentByIds(Long ids) {
        try {
            tbContentMapper.deleteByPrimaryKey(ids);
            return Result.ok();
        } catch (Exception e) {
            return Result.error("SHow leak");
        }
    }

    public Result insertContentCategory(TbContentCategory tbContentCategory) {
        try {
            Date date = new Date();
            tbContentCategory.setCreated(date);
            tbContentCategory.setUpdated(date);
            tbContentCategory.setSortOrder(1);
            tbContentCategoryMapper.insertSelective(tbContentCategory);
            return Result.ok();
        } catch (Exception e) {
            return Result.error("SHow leak");
        }
    }

    public Result updateContentCategory(TbContentCategory tbContentCategory) {
        try {
            Date date = new Date();
            tbContentCategory.setUpdated(date);
            tbContentCategoryMapper.updateByPrimaryKeySelective(tbContentCategory);
            return Result.ok();
        } catch (Exception e) {
            return Result.error("SHow leak");
        }
    }

    public Result deleteContentCategoryById(Long categoryId) {
        try {
            tbContentCategoryMapper.deleteByPrimaryKey(categoryId);
            return Result.ok();
        } catch (Exception e) {
            return Result.error("SHow leak");
        }
    }
}
