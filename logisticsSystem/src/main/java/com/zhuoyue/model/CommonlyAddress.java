package com.zhuoyue.model;

public class CommonlyAddress {
private int id;
private String name;
private int userId;
private String mobile;
private int province;
private int city;
private int area;
private String detailAddress;
public CommonlyAddress(){}
public CommonlyAddress(String name, int userId, String mobile, int province,
		int city, int area, String detailAddress) {
	this.name = name;
	this.userId = userId;
	this.mobile = mobile;
	this.province = province;
	this.city = city;
	this.area = area;
	this.detailAddress = detailAddress;
}
public String getDetailAddress(){
	return detailAddress;
}
public void setDetailAddress(String detailAddress) {
	this.detailAddress = detailAddress;
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public int getUserId() {
	return userId;
}
public void setUserId(int userId) {
	this.userId = userId;
}
public String getMobile() {
	return mobile;
}
public void setMobile(String mobile) {
	this.mobile = mobile;
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
