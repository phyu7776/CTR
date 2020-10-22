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
	
	public int ios() throws Exception;
	
	public int android() throws Exception;
	
	public int unkwon() throws Exception;
	
	public int apple() throws Exception;
	
	public int samsung() throws Exception;
	
	public int lg() throws Exception;
	
	public int unknown() throws Exception;
	
	public int ck02() throws Exception;
	public int ck04() throws Exception;
	public int ck06() throws Exception;
	public int ck08() throws Exception;
	public int ck10() throws Exception;
	public int ck12() throws Exception;
	public int ck14() throws Exception;
	public int ck16() throws Exception;
	public int ck18() throws Exception;
	public int ck20() throws Exception;
	public int ck22() throws Exception;
	public int ck24() throws Exception;
	
}