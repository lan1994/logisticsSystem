package com.zhuoyue.model;

import java.text.SimpleDateFormat;
import java.util.Date;
/*
 * @author 阮飞
 * */
public class Order {
	private int oder_id;  //id
	private int ordernumber;//订单号
	private String sender; //发货人	
	private String sender_address;//发货地址
	private String addressee;//收件人
	private String site_addressee;//收件地址
	private Date order_schedule;//订单时间
	private int timeliness;//0为修改前的   1为修改后的
	private int state;  //0表示还未收货，1表示已经收货
	public int getOder_id() {
		return oder_id;
	}
	public void setOder_id(int oder_id) {
		this.oder_id = oder_id;
	}
	public int getOrdernumber() {
		return ordernumber;
	}
	public void setOrdernumber(int ordernumber) {
		this.ordernumber = ordernumber;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getSender_address() {
		return sender_address;
	}
	public void setSender_address(String sender_address) {
		this.sender_address = sender_address;
	}
	public String getAddressee() {
		return addressee;
	}
	public void setAddressee(String addressee) {
		this.addressee = addressee;
	}
	public String getSite_addressee() {
		return site_addressee;
	}
	public void setSite_addressee(String site_addressee) {
		this.site_addressee = site_addressee;
	}
	public int getTimeliness() {
		return timeliness;
	}
	public void setTimeliness(int timeliness) {
		this.timeliness = timeliness;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	
	//获取时间
	public String getorder_schedule(){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm");
		return sdf.format(order_schedule);
	}
	
	public void setorder_schedule(){
		this.order_schedule=order_schedule;
	}
	

}
