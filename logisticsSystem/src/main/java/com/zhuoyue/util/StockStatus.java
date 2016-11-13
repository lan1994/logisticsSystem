package com.zhuoyue.util;

/*
 * @author 兰心序
 * */
public enum StockStatus {
	ONTHEWAY(0, "已出库，在运送途中"), 
	ARRIVAL(1, "出库，并且已经到达下一站"), 
	INSTOCK(2, "在库");
	private int value;
	private String valueString;

	StockStatus(int value, String valueString) {
		this.value = value;
		this.valueString = valueString;
	}
	public int getValue() {
		return value;
	}
	public String getValueString(){
		return valueString;
	}
	public static StockStatus valueof(int value){
		switch(value){
			case 0:
				return ONTHEWAY;
			case 1:
				return ARRIVAL;
			case 2:
				return INSTOCK;
			default:
				return null;
		}
	}
}
