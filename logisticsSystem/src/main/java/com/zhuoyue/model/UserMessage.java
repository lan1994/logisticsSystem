package com.zhuoyue.model;
/*
 * @author 兰心序
 * */
public class UserMessage {
	private int id;
	private int userid;
	private String name;
	private String mobile;
	private char sex;
	private int province;
	private int city;
	private int area;
	private int sorttationid;
	
	
	public int getSorttationid() {
		return sorttationid;
	}

	public void setSorttationid(int sorttationid) {
		this.sorttationid = sorttationid;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public char getSex() {
		return sex;
	}

	public void setSex(char sex) {
		this.sex = sex;
	}

	public int getProvince() {
		return province;
	}

	public void setProvince(int province) {
		this.province = province;
	}

	public int getCity() {
		return city;
	}

	public void setCity(int city) {
		this.city = city;
	}

	public int getArea() {
		return area;
	}

	public void setArea(int area) {
		this.area = area;
	}

}
