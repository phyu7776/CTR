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


	@Override
	public int total() throws Exception {
		// TODO Auto-generated method stub
		return canvasjsChartDao.total();
	}

	@Override
	public int ios() throws Exception {
		// TODO Auto-generated method stub
		return canvasjsChartDao.ios();
	}

	@Override
	public int android() throws Exception {
		// TODO Auto-generated method stub
		return canvasjsChartDao.android();
	}

	@Override
	public int unkwon() throws Exception {
		// TODO Auto-generated method stub
		return canvasjsChartDao.unkwon();
	}

	@Override
	public int apple() throws Exception {
		// TODO Auto-generated method stub
		return canvasjsChartDao.apple();
	}

	@Override
	public int samsung() throws Exception {
		// TODO Auto-generated method stub
		return canvasjsChartDao.samsung();
	}

	@Override
	public int lg() throws Exception {
		// TODO Auto-generated method stub
		return canvasjsChartDao.lg();
	}

	@Override
	public int unknown() throws Exception {
		// TODO Auto-generated method stub
		return canvasjsChartDao.unknown();
	}

	@Override
	public int ck02() throws Exception {
		// TODO Auto-generated method stub
		return canvasjsChartDao.ck02();
	}

	@Override
	public int ck04() throws Exception {
		// TODO Auto-generated method stub
		return canvasjsChartDao.ck04();
	}

	@Override
	public int ck06() throws Exception {
		// TODO Auto-generated method stub
		return canvasjsChartDao.ck06();
	}

	@Override
	public int ck08() throws Exception {
		// TODO Auto-generated method stub
		return canvasjsChartDao.ck08();
	}

	@Override
	public int ck10() throws Exception {
		// TODO Auto-generated method stub
		return canvasjsChartDao.ck10();
	}

	@Override
	public int ck12() throws Exception {
		// TODO Auto-generated method stub
		return canvasjsChartDao.ck12();
	}

	@Override
	public int ck14() throws Exception {
		// TODO Auto-generated method stub
		return canvasjsChartDao.ck14();
	}

	@Override
	public int ck16() throws Exception {
		// TODO Auto-generated method stub
		return canvasjsChartDao.ck16();
	}

	@Override
	public int ck18() throws Exception {
		// TODO Auto-generated method stub
		return canvasjsChartDao.ck18();
	}

	@Override
	public int ck20() throws Exception {
		// TODO Auto-generated method stub
		return canvasjsChartDao.ck20();
	}

	@Override
	public int ck22() throws Exception {
		// TODO Auto-generated method stub
		return canvasjsChartDao.ck22();
	}

	@Override
	public int ck24() throws Exception {
		// TODO Auto-generated method stub
		return canvasjsChartDao.ck24();
	}


}   