package com.zhuoyue.util;

import java.util.UUID;

public class TestMD5 {
public static void main(String[] args) {
	String pass = 123454322+"";
	String md5pass = LogisticsSystemUtil.MD5(pass+UUID.randomUUID().toString().substring(0, 5));
	System.out.println(md5pass);
}
}
