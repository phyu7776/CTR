package com.ctr.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ctr.persistent.CanvasjsChartDao;
import com.ctr.domain.csv_colVO;
import com.ctr.domain.csv_nameVO;
 
@Service
public class CanvasjsChartServiceImpl implements CanvasjsChartService {
 
	@Autowired
	private CanvasjsChartDao canvasjsChartDao;
 
	public void setCanvasjsChartDao(CanvasjsChartDao canvasjsChartDao) {
		this.canvasjsChartDao = canvasjsChartDao;
	}
 
	@Override
	public List<List<Map<Object, Object>>> getCanvasjsChartData() {
		return canvasjsChartDao.getCanvasjsChartData();
	}

//	@Override
//	public void register(csv_nameVO vo) throws Exception {
//		// TODO Auto-generated method stub
//		canvasjsChartDao.register(vo);
//	}

	@Override
	public List<csv_nameVO> list() throws Exception {
		// TODO Auto-generated method stub
		return canvasjsChartDao.list();
	}
}   