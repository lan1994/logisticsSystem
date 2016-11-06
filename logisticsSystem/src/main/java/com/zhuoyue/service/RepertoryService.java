package com.zhuoyue.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhuoyue.dao.RepertoryDAO;
import com.zhuoyue.model.Repertory;
import com.zhuoyue.util.StockStatus;
/*
 * @author 兰心序
 * */
@Service
public class RepertoryService {
	@Autowired
	RepertoryDAO repertoryDAO;
	
    public List<Repertory> selectByStation(int sortstationId,int status,int offset,int limit){
    	return repertoryDAO.selectByStationId(sortstationId, status, offset, limit);
    }
    
    public List<Repertory> selectByOrderId(int orderId){
    	return repertoryDAO.selectByOrderId(orderId);
    }
    
    @Transactional
    public int instock(Repertory repertory){
    	 List<Repertory> ls = repertoryDAO.selectByOrderId(repertory.getOrderId());
    	 if(ls!=null&&ls.get(0)!=null){
    		 int lastRepertoryId = ls.get(0).getId();
    		 repertoryDAO.updateStatus(StockStatus.ARRIVAL.getValue(), lastRepertoryId);
    	 }
    	 return repertoryDAO.addRepertory(repertory);
    }
    
  /*  @Transactional
    public int outstock(Repertory repertory){
    	repertoryDAO.updateStatus(StockStatus.ONTHEWAY.getValue(), repertory.getId());
    	
    
    }*/
    
    public void updateStatus(int status,int repertoryId){
    	repertoryDAO.updateStatus(status, repertoryId);
    }
}
