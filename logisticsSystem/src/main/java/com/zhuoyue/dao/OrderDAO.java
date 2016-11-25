
package com.zhuoyue.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.zhuoyue.model.Order;


/*
 * 
  *  @author 阮飞
 */
@Mapper
public interface OrderDAO {
    
	//获取所有的订单信息---管理员
	@Select({"select  * from orders"})
    List<Order>  getAllOrder();
	
	//按用户userid和状态获取订单
	@Select({"select  * from orders where timeliness = 1 and state=#{state} and userid=#{userid}"})
	List<Order>  getOrdrById( @Param("userid") int userid,@Param("state") int state );
	
	//按用户userid获取订单
	@Select({"select  * from orders where timeliness = 1  and userid=#{userid}"})
	List<Order>  getAllOrdrById( @Param("userid") int userid);
	
	//通过订单号查询
	@Select({"select  * from orders where  orderNumber=#{orderNumber}"})
	Order  getByNumber(@Param("orderNumber") long orderNumber);
	
	
	//建立新的订单
	@Insert({"insert into orders (orderNumber, userId ,sendaddressId,readdressId,orderSchedule,timeliness,state)"
			+ " values (#{orderNumber},#{userId},#{sendaddressId},#{readdressId},#{orderSchedule},1,0)"
		})
	int addOrder(Order order);
	
	//作废     修改前的订单 将timeless改为0
	@Update({"update orders set timeliness = 0 where orderNumber=#{orderNumber}"})
	int changTimeLiness(long ordernumber);
	
	//订单完成
	@Update({"update orders set state = 1 where ordernumber=#{ordernumber}"})
	int finishByNumber(long ordernumber);
			
	//通过订单号删除
	@Delete({"delete from orders where orderNumber=#{orderNumber}"})
	int deleteByNumber(long orderNumber);
	
}
