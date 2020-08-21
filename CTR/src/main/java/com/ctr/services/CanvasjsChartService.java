package com.ctr.services;

import java.util.List;
import java.util.Map;

import com.ctr.domain.csv_colVO;
import com.ctr.domain.csv_nameVO;
 
public interface CanvasjsChartService {
 
	List<List<Map<Object, Object>>> getCanvasjsChartData();
	
//	public void register(csv_nameVO vo) throws Exception;
 
	public List<csv_nameVO> list() throws Exception;
	
	public int total() throws Exception;
}