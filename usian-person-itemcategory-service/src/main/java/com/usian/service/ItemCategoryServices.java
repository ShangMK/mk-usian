package com.usian.service;

import com.usian.mapper.TbContentMapper;
import com.usian.mapper.TbItemCatMapper;
import com.usian.pojo.TbContent;
import com.usian.pojo.TbItemCat;
import com.usian.pojo.TbItemCatExample;
import com.usian.redisconfig.RedisClient;
import com.usian.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemCategoryServices {
    @Value("${AD_CATEGORY_ID}")
    private Long AD_CATEGORY_ID;

    @Value("${AD_HEIGHT}")
    private Integer AD_HEIGHT;

    @Value("${AD_WIDTH}")
    private Integer AD_WIDTH;

    @Value("${AD_HEIGHTB}")
    private Integer AD_HEIGHTB;

    @Value("${AD_WIDTHB}")
    private Integer AD_WIDTHB;

    @Value("${PROTAL_CATRESULT_KEY}")
    private String portal_catresult_redis_key;

    @Value("${PORTAL_AD_KEY}")
    private String protal;

    @Autowired
    RedisClient redisClient;

    @Autowired
    TbItemCatMapper tbItemCatMapper;
    @Autowired
    TbContentMapper tbContentMapper;

    public ArrayList<Object> getchildrehashmap(Long id) {
        ArrayList<Object> arrayList = new ArrayList<>();
        List<TbItemCat> parentId = this.getParentId(id);
        int count=0;

        for (TbItemCat tbItemCat : parentId) {

            if (tbItemCat.getIsParent()) {
                CatNode catNode = new CatNode();
                catNode.setName(tbItemCat.getName());
                catNode.setItem(this.getchildrehashmap(tbItemCat.getId()));
                arrayList.add(catNode);
                if (count == 18) {
                    break;
                }
            }else{
                arrayList.add(tbItemCat.getName());
            }
            count++;
        }
        return arrayList;
    }
    /**
     * 把循环查询的example提出来变成一个方法降低代码重复
     */
    public  List<TbItemCat> getParentId(Long id){
        TbItemCatExample tbItemCatExample = new TbItemCatExample();
        TbItemCatExample.Criteria criteria = tbItemCatExample.createCriteria();
        criteria.andParentIdEqualTo(id);
        List<TbItemCat> itemCats = tbItemCatMapper.selectByExample(tbItemCatExample);
        return itemCats;
    }
    public Result selectItemCategoryAll() {
        CatResult catResult = new CatResult();
        if (redisClient.exists(portal_catresult_redis_key)) {
            System.out.println("redis----");
            String ca = (String) redisClient.get(portal_catresult_redis_key);
            catResult = JsonUtils.jsonToPojo(ca, CatResult.class);
        }else{
            ArrayList<Object> arrayList = new ArrayList<>();
            //查出根类标题集合
            List<TbItemCat> oneitemCats = this.getParentId(Long.parseLong("0"));
            //遍历父节点
            for (TbItemCat oneitemCat : oneitemCats) {
                CatNode catNode = new CatNode();
                catNode.setName(oneitemCat.getName());
                catNode.setItem(this.getchildrehashmap(oneitemCat.getId()));
                arrayList.add(catNode);
            }
            catResult.setData(arrayList);
            System.out.println("mysql---");
            redisClient.set(portal_catresult_redis_key, JsonUtils.objectToJson(catResult));
        }
        return Result.ok(catResult);
    }




    public Result selectFrontendContentByAD() {
        try {
            if (redisClient.exists(protal)){
                System.out.println("首页大广告--redis");
                String o = (String) redisClient.get(protal);
                List<AdNode> adNodes = JsonUtils.jsonToList(o, AdNode.class);
                return Result.ok(adNodes);
            }else {
                List<TbContent> tbContents = tbContentMapper.selectByExample(null);
                ArrayList<AdNode> list = new ArrayList<>();
                for (TbContent tbContent : tbContents) {
                    AdNode adNode = new AdNode();
                    adNode.setSrc(tbContent.getPic());
                    adNode.setSrcB(tbContent.getPic2());
                    adNode.setHref(tbContent.getUrl());
                    adNode.setHeight(AD_HEIGHT);
                    adNode.setWidth(AD_WIDTH);
                    adNode.setHeightB(AD_HEIGHTB);
                    adNode.setWidthB(AD_WIDTHB);
                    list.add(adNode);
                }
                System.out.println("打广告 mysql ---");
                redisClient.set(protal, JsonUtils.objectToJson(list));
                return Result.ok(list);
            }
        } catch (Exception e) {
            return Result.error("999");
        }
    }

}
