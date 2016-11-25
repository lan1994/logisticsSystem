package com.zhuoyue.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhuoyue.dao.OrderDAO;
import com.zhuoyue.model.Order;

@Service
public class OrderService {

	@Autowired
	OrderDAO orderDAO;

	// 获取所有的订单
	public List<Order> getAllOrder() {
		return orderDAO.getAllOrder();
	}

	// 按用户id获取订单号
	public List<Order> getOrdrById(int userid,int state ){
		return orderDAO.getOrdrById(userid, state);
	}
	
	public List<Order> getAllOrdrById(int userid){
		return orderDAO.getAllOrdrById(userid);
	}

	// 通过订单号查询
	public Order getOrderByNumber(long orderNumber) {
		return orderDAO.getByNumber(orderNumber);
	}

	// 建立新的订单
	public boolean getNeworder(Order order) {
		int pull = orderDAO.addOrder(order);
		if (pull > 0)
			return true;
		else
			return false;
	}

	// 修改订单
	public boolean updateOrder(long orderNumber, Order order) {
		int pl = orderDAO.changTimeLiness(orderNumber);
		int pk = orderDAO.addOrder(order);
		if (pl > 0 && pk > 0)
			return true;
		else
			return false;
	}

	// 订单完成
	public boolean finishOrder( long orderNumber) {
		int pull = orderDAO.finishByNumber(orderNumber);
		if (pull > 0)
			return true;
		else
			return false;
	}

	// 删除订单
	public boolean deletOrder(long orderNumber) {
		int pull = orderDAO.deleteByNumber(orderNumber);
		if (pull > 0)
			return true;
		else
			return false;
	}

}
