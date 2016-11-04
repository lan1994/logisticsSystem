package com.zhuoyue.model;

import org.springframework.stereotype.Component;
/*
 * @author 兰心序
 * */
@Component
public class HostHolder {
private static ThreadLocal<User> users = new ThreadLocal<User>();
public void setUser(User user){
	users.set(user);
}
public User get(){
	return users.get();
}
public void clear(){
	users.remove();
}
}
