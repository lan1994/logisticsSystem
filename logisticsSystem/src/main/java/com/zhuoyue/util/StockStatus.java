package com.zhuoyue.util;
/*
 * @author 兰心序
 * */
public enum StockStatus {
	ONTHEWAY(0),
	ARRIVAL(1),
	INSTOCK(2);
	private int value;
	StockStatus(int value){this.value = value;}
	public int getValue(){return value;}
}
