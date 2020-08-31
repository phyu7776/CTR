package com.ctr.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;


@Service("asyncTaskService")
public class CanvasjsChartData {
	
	File file;
	FileReader fr;
	BufferedReader in;
	String[] attrName;
	String[] columnsContent;
	static List<List<Map<Object,Object>>> list = new ArrayList<List<Map<Object,Object>>>();
	
	@Async
	public String[] readcsv() {

		Connection con = null;
		PreparedStatement pstmt = null;
		PreparedStatement nstmt = null;
		Statement check = null;
		ResultSet rs = null;
		int count = 0;


		String driver = "org.mariadb.jdbc.Driver";
		String url = "jdbc:mariadb://127.0.0.1:3306/ctr";
		String user = "root";
		String password = "1111";

		String SQL = "insert into ctr_column ( bid_time, ad_format, app_bundle, app_id, tagid, carrier, connectiontype, devicetype, dnt, ifa, ip, js, lmt, make, model, os, osv, clickbrowser ,geo_city, geo_country, geo_region, geo_type, dsp_id, adomain, cost_ratio, is_click) values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		String Check = "select * from ctr_name";
		String Nopath = "insert into ctr_name (NAME) values ( ?)";

		//String[] temp = null;
		BufferedReader br = null;
		String line;
		File path = new File("D:\\new csv data");
		File[] fileList = path.listFiles();
		File[] filtered_Filelist = null;
		List<String> dbpath = new ArrayList<String>(); // db 안의 경로

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url,user,password);
			pstmt = con.prepareStatement(SQL);
			nstmt = con.prepareStatement(Nopath);
			check = con.createStatement();


			rs = check.executeQuery(Check);
			while(rs.next()) {
				dbpath.add(rs.getString("NAME"));
			}

			filtered_Filelist = filter(fileList, dbpath);
			
//			for(int i=0;i<filtered_Filelist.length;i++) {
//				System.out.println(filtered_Filelist[i]);
//			}
//			
			System.out.println();
			
			if(filtered_Filelist.length > 0) {
				for(int i=0;i<filtered_Filelist.length;i++) {
					for(int k=0;k<dbpath.size();k++) {

						System.out.println(filtered_Filelist[i]);

						nstmt.setString(1, filtered_Filelist[i].getAbsolutePath());
						int s = nstmt.executeUpdate();

						try {
							br = new BufferedReader(new InputStreamReader(new FileInputStream(filtered_Filelist[i]), "UTF-8"));
							br.readLine();
							while((line = br.readLine()) != null) {
								String[] temp = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");

								for(String item : temp) {
									item.replaceAll(",", "");
								}


								for(int j=0;j<temp.length;j++) {
									if(temp[j].isEmpty()) {
										temp[j] = "0";
									}else if(temp[j] == null) {
										temp[j] = "0";
									}

								}

								//" " 제거 반복문
								//									for(int j=0;j<temp.length;j++) {  
								//										temp[j] = temp[j].substring(1,temp[j].length()-1);
								//									}
								//									for(int j=0;j<temp.length;j++) {
								//										System.out.print(temp[j] + " ");
								//									}

								//출력 담당
								//									for(int j=0;j<temp.length;j++) {
								//										System.out.print(j+"번"+temp[j]+" ");
								//									}
								//									System.out.println();

								for(int j=0;j<temp.length;j++) {

									if(j == 6 || j == 7 || j == 8 || j == 11 || j == 12 || j == 17 || j == 22) {

										pstmt.setInt(j+1, Integer.parseInt(temp[j]));
									}
									else if(j == temp.length-1) { // is_click F -> 0
										if(temp[j].equals("F")) {
											pstmt.setInt(j+1, 0);
										}
										else {
											pstmt.setInt(j+1, 1);
										}
									}
									else {
										pstmt.setString(j+1, temp[j]);
									}
									//System.out.print(temp[j] + " ");
								}

								count++;
								if (count == 10000) {
									System.out.println("10000!! reset count");
									count = 0;
								}

								//오류 찾기 용
								//									pstmt.setString(1, temp[0]);
								//									pstmt.setString(2, temp[1]); 
								//									pstmt.setString(3, temp[2]); 
								//									pstmt.setString(4, temp[3]); 
								//									pstmt.setString(5, temp[4]);
								//									pstmt.setString(6, temp[5]);
								//									pstmt.setString(7, temp[6]);
								//									pstmt.setString(8, temp[7]);
								//									pstmt.setString(9, temp[8]);
								//									pstmt.setString(10, temp[9]);
								//									pstmt.setString(11, temp[10]);
								//									pstmt.setString(12, temp[11]);
								//									pstmt.setString(13, temp[12]);
								//									pstmt.setString(14, temp[13]);
								//									pstmt.setString(15, temp[14]);
								//									pstmt.setString(16, temp[15]);
								//									pstmt.setString(17, temp[16]);
								//									pstmt.setString(18, temp[17]);
								//									pstmt.setString(19, temp[18]);
								//									pstmt.setString(20, temp[19]);
								//									pstmt.setString(21, temp[20]);
								//									pstmt.setString(22, temp[21]);
								//									pstmt.setString(23, temp[22]);
								//									pstmt.setString(24, temp[23]);
								//									pstmt.setString(25, temp[24]);
								//									pstmt.setString(26, temp[25]);

								int r = pstmt.executeUpdate();

							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

				}

			}
		}catch (SQLException e) { 
			System.out.println("[SQL Error : " + e.getMessage() + "]"); 
		} catch (ClassNotFoundException e1) { 
			System.out.println("[JDBC Connector Driver 오류 : " + e1.getMessage() + "]"); 
		}finally { //사용순서와 반대로 close 함 
			if (pstmt != null) { 
				try { 
					pstmt.close(); 
				} catch (SQLException e) { 
					e.printStackTrace(); 
				} 
			} if (con != null) { 
				try { con.close(); 
				} catch (SQLException e) { 
					e.printStackTrace(); 
				} 
			} 
		}

		return null;

	}
	
	@Async
	public File[] filter(File[] fileList,List<String> dbpath) {
		List<String> uppath = new ArrayList<String>();

		for(int i=0;i<fileList.length;i++) {
			uppath.add(fileList[i].getAbsolutePath());
		}
		if(fileList.length > 0) {
			for(int i=0;i<fileList.length;i++) {
				for(int k=0;k<dbpath.size();k++) {
					//					System.out.println(fileList[i].getAbsolutePath().equals(dbpath.get(k)));
					if(fileList[i].getAbsolutePath().equals(dbpath.get(k))) { // 만약 db에 같은 이름의 path 가 있다면 무시하고 
						//						System.out.println("같다");
						uppath.remove(dbpath.get(k));
					}else{ // 그렇지 않다면 파일을 읽는다 
						//						System.out.println("다르다");
					}

				}
			}
		}
		File[] newapp=new File[uppath.size()];
		for(int i=0;i<uppath.size();i++) {
			newapp[i]=new File(uppath.get(i));
		}

		return newapp;
	}
	@Async
	public Future<String> jobRunningInBackground() {
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
		return new AsyncResult<String>("End");

	}
	
	public static List<List<Map<Object, Object>>> PostCanvasjsDataList(){
		return list;
	}
}             