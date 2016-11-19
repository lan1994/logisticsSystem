package com.zhuoyue.dao;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zhuoyue.LogisticsSystemApplication;
import com.zhuoyue.model.Order;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = LogisticsSystemApplication.class)
public class TestOrderDAO {
	@Autowired
	OrderDAO orderDAO;
	
@Test
public void testselectcount(){
/*	System.out.println(orderDAO.getByNumber(1001).getReaddressId());
	System.out.println(orderDAO.getByNumber(1001).getSendaddressId());
*/
	Order order = new Order();
	order.setOrderNumber(Order.getRandomOrderNumber());
	order.setorderSchedule(new Date());
	order.setReaddressId(2);
	order.setSendaddressId(1);
	order.setUserId(10014);
	orderDAO.addOrder(order);

}
}
