package com.usian.quartz;

import com.usian.mapper.LocalMessageMapper;
import com.usian.mq.MQSender;
import com.usian.pojo.LocalMessage;
import com.usian.pojo.TbOrder;
import com.usian.pojo.TbOrderItem;
import com.usian.redisconfig.RedisClient;
import com.usian.service.OrderService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderQuartz implements Job {

	@Autowired
	private OrderService orderService;
	@Autowired
	RedisClient redisClient;

	@Autowired
	LocalMessageMapper localMessageMapper;

	@Autowired
	MQSender mqSender;

	/**
	 * 关闭超时订单
	 */
	@Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
		String ip = null;
		try {
			ip = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		//解决quartz集群任务重复执行
		if(redisClient.setnx("SETNX_LOCK_ORDER_KEY",ip,30)) {

			System.out.println("执行扫描本地消息表的任务...." + new Date());
			List<LocalMessage> localMessageList = orderService.selectlocalMessageByStatus(0);

			//1、查询超时订单
			List<TbOrder> tbOrderList = orderService.readTimeout();

			//2、关闭超时订单
			ArrayList<List<TbOrderItem>> lists = orderService.colseOrder(tbOrderList);

			//3、把超时订单中的商品库存数量加回去
			int i = orderService.updateTbitem(lists);

			for (int w = 0; w < localMessageList.size(); w++) {
				LocalMessage localMessage =  localMessageList.get(w);
				mqSender.sendMsg(localMessage);
			}
			//... ... ... 关闭超时订单业务
			redisClient.del("SETNX_LOCK_ORDER_KEY");
		}else{
			System.out.println(
					"===========任务正在执行=======================");
		}

    }

}