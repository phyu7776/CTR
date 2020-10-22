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
	@Override
	public int total() throws Exception {
		// TODO Auto-generated method stub
		return sql.selectOne(namespace + ".total");
	}
	@Override
	public int ios() throws Exception {
		// TODO Auto-generated method stub
		return sql.selectOne(namespace + ".ios");
	}
	@Override
	public int android() throws Exception {
		// TODO Auto-generated method stub
		return sql.selectOne(namespace + ".android");
	}
	@Override
	public int unkwon() throws Exception {
		// TODO Auto-generated method stub
		return sql.selectOne(namespace + ".unkwon");
	}
	@Override
	public int apple() throws Exception {
		// TODO Auto-generated method stub
		return sql.selectOne(namespace + ".mk_apple");
	}
	@Override
	public int samsung() throws Exception {
		// TODO Auto-generated method stub
		return sql.selectOne(namespace + ".mk_samsung");
	}
	@Override
	public int lg() throws Exception {
		// TODO Auto-generated method stub
		return sql.selectOne(namespace + ".mk_lg");
	}
	@Override
	public int unknown() throws Exception {
		// TODO Auto-generated method stub
		return sql.selectOne(namespace + ".mk_unkwon");
	}
	@Override
	public int ck02() throws Exception {
		// TODO Auto-generated method stub
		return sql.selectOne(namespace + ".ck02");
	}
	@Override
	public int ck04() throws Exception {
		// TODO Auto-generated method stub
		return sql.selectOne(namespace + ".ck04");
	}
	@Override
	public int ck06() throws Exception {
		// TODO Auto-generated method stub
		return sql.selectOne(namespace + ".ck06");
	}
	@Override
	public int ck08() throws Exception {
		// TODO Auto-generated method stub
		return sql.selectOne(namespace + ".ck08");
	}
	@Override
	public int ck10() throws Exception {
		// TODO Auto-generated method stub
		return sql.selectOne(namespace + ".ck10");
	}
	@Override
	public int ck12() throws Exception {
		// TODO Auto-generated method stub
		return sql.selectOne(namespace + ".ck12");
	}
	@Override
	public int ck14() throws Exception {
		// TODO Auto-generated method stub
		return sql.selectOne(namespace + ".ck14");
	}
	@Override
	public int ck16() throws Exception {
		// TODO Auto-generated method stub
		return sql.selectOne(namespace + ".ck16");
	}
	@Override
	public int ck18() throws Exception {
		// TODO Auto-generated method stub
		return sql.selectOne(namespace + ".ck18");
	}
	@Override
	public int ck20() throws Exception {
		// TODO Auto-generated method stub
		return sql.selectOne(namespace + ".ck20");
	}
	@Override
	public int ck22() throws Exception {
		// TODO Auto-generated method stub
		return sql.selectOne(namespace + ".ck22");
	}
	@Override
	public int ck24() throws Exception {
		// TODO Auto-generated method stub
		return sql.selectOne(namespace + ".ck24");
	}
}