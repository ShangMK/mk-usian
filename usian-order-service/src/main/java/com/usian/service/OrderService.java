package com.usian.service;

import com.usian.mapper.*;
import com.usian.mq.MQSender;
import com.usian.pojo.*;
import com.usian.redisconfig.RedisClient;
import com.usian.utils.JsonUtils;
import com.usian.utils.Result;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
public class   OrderService {

    @Autowired
    TbOrderMapper tbOrderMapper;

    @Autowired
    TbItemMapper tbItemMapper;

    @Autowired
    TbOrderShippingMapper tbOrderShippingMapper;

    @Autowired
    TbOrderItemMapper tbOrderItemMapper;

    @Autowired
    RedisClient redisClient;
    //订单id的REDIS
    @Value("${ORDER_ID}")
    private String ORDER_ID;

    //订单id的步值
    @Value("${ORDER_BEGIN}")
    private String ORDER_BEGIN;

    //订单id初始值
    @Value("${ORDER_INITNUMER}")
    private String ORDER_INITNUMER;

    private String ODER_ITEM_ID = "ODER_ITEM_ID";

    @Autowired
    LocalMessageMapper localMessageMapper;

    @Autowired
    MQSender mqSender;

    @Autowired
    AmqpTemplate amqpTemplate;

    /**
     * 提交订单后的页面数据实现
     * @param ids
     * @param userId
     * @param token
     * @return
     */
    public Result goSettlements(String[] ids, String userId, String token) {
        try {
            Object shoping = redisClient.hget("SHOPING", userId);
            Map<String,TbItem> map = JsonUtils.jsonToMap((String) shoping, TbItem.class);
            Collection<TbItem> values = map.values();
            ArrayList<TbItem> arrayList = new ArrayList<>();
            for (TbItem value : values) {
                for (String id : ids) {
                    if (id.equals(value.getId().toString())) {
                        arrayList.add(value);
                    }
                }
            }
            return Result.ok(arrayList);
        } catch (Exception e) {
            return Result.error("500");
        }
    }

    /**
     * 将数据插入物流，商品信息，。。三张表
     * @param orderINfo
     * @param userId
     * @return
     */
    public Result insertOrder(OrderINfo orderINfo,String userId) {
        Date date = new Date();
        try {
            long incr;
            long itemid;
            //生成快递id的自增数
            if (redisClient.exists(ORDER_ID)){
                incr = redisClient.incr(ORDER_ID, 1);
                redisClient.set(ORDER_ID,incr+"");
            }else {
                incr=Long.parseLong(ORDER_INITNUMER);
                redisClient.set(ORDER_ID,incr+"");
            }
            //生成itemid的自增数
            if (!redisClient.exists(ODER_ITEM_ID)){
                itemid=Long.parseLong("10356");
                redisClient.set(ODER_ITEM_ID,itemid+"");
            }


            //订单插入
            TbOrder tbOrder = orderINfo.getTbOrder();
            tbOrder.setOrderId(String.valueOf(incr));
            tbOrder.setCreateTime(date);
            tbOrder.setUpdateTime(date);
            tbOrder.setStatus(1);
            tbOrderMapper.insertSelective(tbOrder);

            //物流插入
            TbOrderShipping tbOrderShipping = orderINfo.getTbOrderShipping();
            tbOrderShipping.setOrderId(String.valueOf(incr));
            tbOrderShipping.setCreated(date);
            tbOrderShipping.setUpdated(date);
            tbOrderShippingMapper.insertSelective(tbOrderShipping);

            //商品插入
            Map<String,TbItem> map;
            List<TbOrderItem> tbItems = JsonUtils.jsonToList(orderINfo.getV(), TbOrderItem.class);
            for (TbOrderItem tbItem : tbItems) {
                itemid = redisClient.incr(ODER_ITEM_ID, 1);
                redisClient.set(ODER_ITEM_ID,itemid+"");
                tbItem.setOrderId(incr + "");
                tbItem.setId(itemid+"");
                tbOrderItemMapper.insertSelective(tbItem);
                Object shoping = redisClient.hget("SHOPING", userId);
                if (StringUtils.isEmpty(shoping)){
                    map= JsonUtils.jsonToMap((String) shoping, TbItem.class);
                    map.remove(tbItem.getId().toString());
                    System.out.println("测试删除1");
                }
            }
            //redisClient.hset("SHOPING",userId, JsonUtils.objectToJson(map));
            //amqpTemplate.convertAndSend("orderid", "order.add", incr);

            //保存本地消息记录
            LocalMessage localMessage = new LocalMessage();
            localMessage.setTxNo(UUID.randomUUID().toString());
            localMessage.setOrderNo(incr+"");
            localMessage.setState(0);
            localMessageMapper.insertSelective(localMessage);

            //发布消息到mq，完成扣减库存
            mqSender.sendMsg(localMessage);

            return Result.ok(incr);
        } catch (Exception e) {
            return Result.error("500");
        }

    }

    /**
     *查找过时订单并返回
     * @return
     */
    public List<TbOrder> readTimeout(){
        List<TbOrder> tbOrders = tbOrderMapper.selectByExample(null);
        for (TbOrder tbOrder : tbOrders) {
            if (tbOrder.getStatus() != 1) {
                tbOrders.remove(tbOrder);
            }
        }
        return tbOrders;
    }

    /**
     * 根据集合批量关闭订单
     * @param list
     * @return
     */
    public ArrayList<List<TbOrderItem>>  colseOrder(List<TbOrder> list) {
        int count=0;
        ArrayList<List<TbOrderItem>> arrayList = new ArrayList<>();
        for (TbOrder tbOrder : list) {
            List<TbOrderItem> orderItem = getOrderItem(tbOrder);
            arrayList.add(orderItem);
            int i = colseOrder(tbOrder);
            count += i;
        }
        System.out.println(count);

        return arrayList;
    }

    /**
     * 根据订单获取订单商品信息进行进一步修改
     * @param tborder
     * @return
     */
    public List<TbOrderItem>  getOrderItem(TbOrder tborder) {
        TbOrderItemExample tbOrderItemExample = new TbOrderItemExample();
        TbOrderItemExample.Criteria criteria = tbOrderItemExample.createCriteria();
        criteria.andOrderIdEqualTo(tborder.getOrderId());
        List<TbOrderItem> tbOrderItems = tbOrderItemMapper.selectByExample(tbOrderItemExample);
        return tbOrderItems;
    }

    /**
     * 关闭订单
     *
     * @param tbOrder
     * @return
     */
    public int colseOrder(TbOrder tbOrder) {
        Date date = new Date();
        tbOrder.setStatus(6);
        tbOrder.setUpdateTime(date);
        tbOrder.setCloseTime(date);
        tbOrder.setConsignTime(date);
        tbOrder.setEndTime(date);
        tbOrder.setPaymentTime(date);
        int i = tbOrderMapper.updateByPrimaryKeySelective(tbOrder);
        return i;
    }

    /**
     * 恢复消失的库存
     * @param arrayList
     * @return
     */
    public int updateTbitem(ArrayList<List<TbOrderItem>> arrayList){
        try {
            for (List<TbOrderItem> tbOrderItems : arrayList) {
                for (TbOrderItem tbOrderItem : tbOrderItems) {
                    TbItem tbItem = tbItemMapper.selectByPrimaryKey(Long.parseLong(tbOrderItem.getItemId()));
                    tbItem.setNum(tbOrderItem.getNum() + tbItem.getNum());
                    tbItemMapper.updateByPrimaryKeySelective(tbItem);
                }
            }
            return 1;
        } catch (Exception e) {
            return 0;
        }

    }


    public List<LocalMessage> selectlocalMessageByStatus(int i) {
        return localMessageMapper.selectByExample(null);
    }
}
