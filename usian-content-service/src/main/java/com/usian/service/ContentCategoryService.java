package com.usian.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.usian.mapper.TbContentCategoryMapper;
import com.usian.mapper.TbContentMapper;
import com.usian.pojo.TbContent;
import com.usian.pojo.TbContentCategory;
import com.usian.pojo.TbContentCategoryExample;
import com.usian.pojo.TbContentExample;
import com.usian.redisconfig.RedisClient;
import com.usian.utils.PageResult;
import com.usian.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class ContentCategoryService {
    @Autowired
    TbContentCategoryMapper tbContentCategoryMapper;
    @Autowired
    TbContentMapper tbContentMapper;
    @Autowired
    RedisClient redisClient;

    public Result selectContentCategoryByParentId(Long id) {
        try {
            TbContentCategoryExample tbContentCategoryExample = new TbContentCategoryExample();
            TbContentCategoryExample.Criteria criteria = tbContentCategoryExample.createCriteria();
            criteria.andParentIdEqualTo(id);
            criteria.andStatusEqualTo(1);
            List<TbContentCategory> tbContentCategories = tbContentCategoryMapper.selectByExample(tbContentCategoryExample);
            return Result.ok(tbContentCategories);
        } catch (Exception e) {
            return Result.error("memory leak");
        }
    }

    public Result insertContentCategory(TbContentCategory tbContentCategory) {
        try {
            Date date = new Date();
            tbContentCategory.setCreated(date);
            tbContentCategory.setUpdated(date);
            tbContentCategory.setSortOrder(1);
            tbContentCategoryMapper.insertSelective(tbContentCategory);
            redisClient.del("PORTAL_AD_KEY");
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
            redisClient.del("PORTAL_AD_KEY");
            return Result.ok();
        } catch (Exception e) {
            return Result.error("SHow leak");
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
            redisClient.del("PROTAL_CATRESULT_KEY");
            return Result.ok();
        } catch (Exception e) {
            return Result.error("SHow leak");
        }
    }

    public Result deleteContentByIds(Long ids) {
        try {
            tbContentMapper.deleteByPrimaryKey(ids);
            redisClient.del("PROTAL_CATRESULT_KEY");
            return Result.ok();
        } catch (Exception e) {
            return Result.error("SHow leak");
        }
    }
    /**
     * 1查看是否为根节点，是就停止返回失败
     * 2不是就循环删除其子节点
     *      1查出子节点list
     *      2遍历子节点先删除后查询
     * 3删除节点本身
     */
    public Result deleteContentCategoryById(Long categoryId) {
        try {
            TbContentCategory tbContentCategory = tbContentCategoryMapper.selectByPrimaryKey(categoryId);
            if (tbContentCategory.getParentId() == 0) {
                return Result.error("无法删除根");
            }

            this.deletechildenId(categoryId);

            TbContentCategory tbContentCategory1 = new TbContentCategory();
            tbContentCategory1.setStatus(0);
            tbContentCategory1.setId(categoryId);
            tbContentCategoryMapper.updateByPrimaryKeySelective(tbContentCategory1);
            redisClient.del("PORTAL_AD_KEY");
            return Result.ok();
        } catch (Exception e) {
            return Result.error("SHow leak");
        }
    }

    private void deletechildenId(Long id) {
        List<TbContentCategory> selectid = this.selectid(id);
        if (selectid != null && selectid.size() > 0) {
            for (TbContentCategory tbContentCategory : selectid) {
                tbContentCategory.setStatus(0);
                tbContentCategoryMapper.updateByPrimaryKeySelective(tbContentCategory);
                this.deletechildenId(tbContentCategory.getId());
            }
        }
    }

    private  List<TbContentCategory> selectid(Long id) {
        TbContentCategoryExample tbContentCategoryExample = new TbContentCategoryExample();
        TbContentCategoryExample.Criteria criteria = tbContentCategoryExample.createCriteria();
        criteria.andParentIdEqualTo(id);
        criteria.andStatusEqualTo(1);
       return  tbContentCategoryMapper.selectByExample(tbContentCategoryExample);
    }
}
