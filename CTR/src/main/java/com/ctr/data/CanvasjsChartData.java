package com.ctr.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


@Service("asyncTaskService")
public class CanvasjsChartData {
	

	static List<List<Map<Object,Object>>> list = new ArrayList<List<Map<Object,Object>>>();
	@Async
	public void jobRunningInBackground() {
		if(list.isEmpty()) {
			
		}else {
			list = new ArrayList<List<Map<Object,Object>>>();
		}
		
		JdbcTemplate jdbcTemplate;
		Map<Object,Object> map = null;
		List<Map<Object,Object>> dataPoints1 = new ArrayList<Map<Object,Object>>();
		List<Map<Object,Object>> dataPoints2 = new ArrayList<Map<Object,Object>>();
		List<Map<Object,Object>> dataPoints3 = new ArrayList<Map<Object,Object>>();
		
		int Click = 0;
		int SKT1 = 0;
		int LG1 = 0;
		int KT1 = 0;
		int UNKWON1 = 0;
		
		int Non_Click = 0;
		int SKT0 = 0;
		int LG0 = 0;
		int KT0 = 0;
		int UNKWON0 = 0;


		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("org.mariadb.jdbc.Driver");
		dataSource.setUrl("jdbc:mariadb://127.0.0.1:3306/ctr");
		dataSource.setUsername("root");
		dataSource.setPassword("1111");
		jdbcTemplate = new JdbcTemplate(dataSource);

		
		System.out.println("Thread Start");

		String Click1 = "select count(is_click) from ctr_column where is_click = 1";
		String sk1 = "SELECT COUNT(carrier) FROM ctr_column WHERE carrier LIKE 'sk' AND is_click = 1";
		String lg1 = "SELECT COUNT(carrier) FROM ctr_column WHERE carrier LIKE 'lg' AND is_click = 1";
		String kt1 = "SELECT COUNT(carrier) FROM ctr_column WHERE carrier LIKE 'kt' AND is_click = 1";
		String unkwon1 = "SELECT COUNT(carrier) FROM ctr_column WHERE carrier LIKE 'unknown' AND is_click = 1";
		

		String Click0 = "select count(is_click) from ctr_column where is_click = 0";
		String sk0 = "SELECT COUNT(carrier) FROM ctr_column WHERE carrier LIKE 'sk' AND is_click = 0";
		String lg0 = "SELECT COUNT(carrier) FROM ctr_column WHERE carrier LIKE 'lg' AND is_click = 0";
		String kt0 = "SELECT COUNT(carrier) FROM ctr_column WHERE carrier LIKE 'kt' AND is_click = 0";
		String unkwon0 = "SELECT COUNT(carrier) FROM ctr_column WHERE carrier LIKE 'unknown' AND is_click = 0";


		Click = jdbcTemplate.queryForObject(Click1, Integer.class);
		SKT1 = jdbcTemplate.queryForObject(sk1, Integer.class);
		LG1 = jdbcTemplate.queryForObject(lg1, Integer.class);
		KT1 = jdbcTemplate.queryForObject(kt1, Integer.class);
		UNKWON1 = jdbcTemplate.queryForObject(unkwon1, Integer.class);
		
		Non_Click = jdbcTemplate.queryForObject(Click0, Integer.class);
		SKT0 = jdbcTemplate.queryForObject(sk0, Integer.class);
		LG0 = jdbcTemplate.queryForObject(lg0, Integer.class);
		KT0 = jdbcTemplate.queryForObject(kt0, Integer.class);
		UNKWON0 = jdbcTemplate.queryForObject(unkwon0, Integer.class);



		map = new HashMap<Object,Object>(); map.put("name", "Click"); map.put("y", Click); map.put("color", "#E7823A");dataPoints1.add(map);
		map = new HashMap<Object,Object>(); map.put("name", "Non_Click"); map.put("y", Non_Click); map.put("color", "#546BC1");dataPoints1.add(map);	

		map = new HashMap<Object,Object>(); map.put("label", "SKT"); map.put("y", SKT1);dataPoints2.add(map);
		map = new HashMap<Object,Object>(); map.put("label", "LG"); map.put("y", LG1);dataPoints2.add(map);
		map = new HashMap<Object,Object>(); map.put("label", "KT"); map.put("y", KT1);dataPoints2.add(map);
		map = new HashMap<Object,Object>(); map.put("label", "UNKWON"); map.put("y", UNKWON1);dataPoints2.add(map);

		map = new HashMap<Object,Object>(); map.put("label", "SKT"); map.put("y", SKT0);dataPoints3.add(map);
		map = new HashMap<Object,Object>(); map.put("label", "LG"); map.put("y", LG0);dataPoints3.add(map);
		map = new HashMap<Object,Object>(); map.put("label", "KT"); map.put("y", KT0);dataPoints3.add(map);
		map = new HashMap<Object,Object>(); map.put("label", "UNKWON"); map.put("y", UNKWON0);dataPoints3.add(map);


		list.add(dataPoints1);
		list.add(dataPoints2);
		list.add(dataPoints3);

		System.out.println("Thread End");
	}
	
	public static List<List<Map<Object, Object>>> PostCanvasjsDataList(){
		return list;
	}
}             