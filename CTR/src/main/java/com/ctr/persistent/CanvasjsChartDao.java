package com.ctr.persistent;

import java.util.List;
import java.util.Map;


import com.ctr.domain.csv_colVO;
import com.ctr.domain.csv_nameVO;
 
public interface CanvasjsChartDao {
	
	List<List<Map<Object, Object>>> getCanvasjsChartData();

	public List<csv_nameVO> list() throws Exception;
	
	public int total() throws Exception;
//	public void register(csv_nameVO vo) throws Exception;
	

}