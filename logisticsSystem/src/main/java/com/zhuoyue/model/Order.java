package com.zhuoyue.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
/*
 * @author 阮飞
 * */
public class Order {
	
	private long orderNumber;//订单号
	private int userId;  //用户id
	private int sendaddressId;//发件地址 
	private int readdressId;//收件地址
	private Date orderSchedule;//订单时间
	private int timeliness;//0为修改前的   1为修改后的
	private int state;  //0表示还未收货，1表示已经收货
	
    
	public static long getRandomOrderNumber() {
		int orderNum = (int) (Math.random()*9000+100);
		 String date = null ;
	    String str = new SimpleDateFormat("yyMMddHHmmss").format(new Date());  
	    if(date==null||!date.equals(str)){  
	        date = str;  
	    }  
	    orderNum ++ ;  
	    long orderNo = Long.parseLong((date)) * 1000;  
	    orderNo += orderNum;;  
		return orderNo;
	}


	public long getOrderNumber() {
		
		return orderNumber;
	}

	public void setOrderNumber(long orderNumber) {
		this.orderNumber = orderNumber;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getSendaddressId() {
		return sendaddressId;
	}

	public void setSendaddressId(int sendaddressId) {
		this.sendaddressId = sendaddressId;
	}

	public int getReaddressId() {
		return readdressId;
	}

	public void setReaddressId(int readdressId) {
		this.readdressId = readdressId;
	}

	public int getTimeliness() {
		return timeliness;
	}

	public void setTimeliness(int timeliness) {
		this.timeliness = 1;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = 0;
	}

	//获取时间
	public String getorderSchedule(){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm");
		return sdf.format(orderSchedule);
	}
	
	public void setorderSchedule(Date orderSchedule){
		this.orderSchedule=orderSchedule;
	}
	

}
