package com.zhuoyue.dao;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.zhuoyue.LogisticsSystemApplication;
import com.zhuoyue.model.MenuInfo;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = LogisticsSystemApplication.class)
public class TestMenuDAO {
	@Autowired
	MenuDAO menuDAO;
	
@Test
public void testmenu(){
/*	System.out.println(orderDAO.getByNumber(1001).getReaddressId());
	System.out.println(orderDAO.getByNumber(1001).getSendaddressId());
*/
	List<MenuInfo> menu = menuDAO.getSecondMenu(5);
	for(MenuInfo m : menu){
		System.out.println(m.getParentmenuId()+m.getMenuPath()+m.getMenuContent());
	}
	
}
}
