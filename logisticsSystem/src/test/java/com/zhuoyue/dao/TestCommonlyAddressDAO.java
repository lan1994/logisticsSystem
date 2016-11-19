package com.zhuoyue.dao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.zhuoyue.LogisticsSystemApplication;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = LogisticsSystemApplication.class)
public class TestCommonlyAddressDAO {
	@Autowired
	CommonlyAddressDAO CommonlyAddressDAO;
	
@Test
public void testselectcount(){
	System.out.println(CommonlyAddressDAO.selectAddressCountByUserId(1));
}
}
