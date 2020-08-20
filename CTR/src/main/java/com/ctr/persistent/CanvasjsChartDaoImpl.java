package com.ctr.persistent;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.ctr.data.CanvasjsChartData;
import com.ctr.domain.csv_colVO;
import com.ctr.domain.csv_nameVO;
 
@Repository
public class CanvasjsChartDaoImpl implements CanvasjsChartDao {
 
	@Inject
	private SqlSession sql;
	
	// 매퍼 
	private static String namespace = "com.ctr.mappers.ctrMapper";
	
	@Override
	public List<List<Map<Object, Object>>> getCanvasjsChartData() {
		return CanvasjsChartData.PostCanvasjsDataList();
	}
//	@Override
//	public void register(csv_nameVO vo) throws Exception {
//		// TODO Auto-generated method stub
//		sql.insert(namespace + ".register", vo);
//	}
	@Override
	public List<csv_nameVO> list() throws Exception {
		// TODO Auto-generated method stub
		return sql.selectList(namespace + ".list");
	}
}