package com.zhuoyue.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhuoyue.dao.SortStationDAO;
import com.zhuoyue.model.SortStation;

/*
 * @author 吴兵兵
 * */
@Service
public class SortStationService {
	@Autowired
	SortStationDAO sortstationDAO;
	
    /*添加分拣站信息*/
	public boolean AddSortStation(SortStation sortstation){	
	   SortStation station = sortstationDAO.selectStationByStationId(sortstation.getStationId());
	   System.out.println(station == null);
	   if (station!=null){
			return false;
	   }
		else{
			int count = sortstationDAO.AddSortStration(sortstation);
			if(count>0){
			  return true;
			}
			else{
			  return false;
			}
		    
		}
	   }
	/*查询分拣站信息*/
	public List<SortStation> selectStationinfo(int rank){
		return sortstationDAO.selectByRank(rank);
	}
	
	/*根据分拣站的ID号查询分拣站信息*/
	public SortStation selectStationById(int id){
		return sortstationDAO.selectById(id);
	}
	
	/*更新分拣站信息*/
	public void updateStation(SortStation station){
		sortstationDAO.updateStation(station);
	}
	
	//删除二级分拣站
	public void delStation(int id){
		sortstationDAO.DelStation(id);
	}
}
