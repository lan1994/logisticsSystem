package com.zhuoyue.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhuoyue.dao.RepertoryDAO;
import com.zhuoyue.dao.SortStationDAO;
import com.zhuoyue.model.Repertory;
import com.zhuoyue.model.SortStation;
import com.zhuoyue.util.StockStatus;
/*
 * @author 兰心序
 * */
@Service
public class RepertoryService {
	@Autowired
	RepertoryDAO repertoryDAO;
	@Autowired
	SortStationDAO sortStationDAO;
    public List<Repertory> selectByStation(int sortstationId,int status,int offset,int limit){
    	return repertoryDAO.selectByStationId(sortstationId, status, offset, limit);
    }
    
    public int selectCountByStation(int sortstationId,int status){
    	return repertoryDAO.selectCountByStaion(sortstationId, status);
    }
    
    public List<Repertory> selectByOrderId(long orderId){
    	return repertoryDAO.selectByOrderId(orderId);
    }
    
    public Repertory selectByIdAndStation(int next,long orderId){
    	return repertoryDAO.selectByIdAndStation(next, orderId);
    }
    
    @Transactional
    public int instock(Repertory repertory){
    	 List<Repertory> ls = repertoryDAO.selectByOrderId(repertory.getOrderId());
    	 if(ls!=null&&ls.size()!=0){
    		 int lastRepertoryId = ls.get(0).getId();
    		 repertoryDAO.updateStatus(StockStatus.ARRIVAL.getValue(), lastRepertoryId);
    	 }
    	 return repertoryDAO.addRepertory(repertory);
    }
    
    public int outstock(int repertId){
    	Repertory repertory = repertoryDAO.selectById(repertId);
    	SortStation sortStation = sortStationDAO.selectById(repertory.getNextStationCode());
    	if(sortStation!=null){
    		repertoryDAO.updateStatus(StockStatus.ONTHEWAY.getValue(), repertId);
    	}else{
    		return 0;
    	}
    	return 1;
    }
    
    public void updateStatus(int status,int repertoryId){
    	repertoryDAO.updateStatus(status, repertoryId);
    }
}
