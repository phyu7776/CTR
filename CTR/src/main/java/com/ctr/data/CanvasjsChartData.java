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
		String url = "jdbc:mariadb://127.0.0.1:3307/ctr";
		String user = "root";
		String password = "1111";

		String SQL = "insert into ctr_column ( bid_time, ad_format, app_bundle, app_id, tagid, carrier, connectiontype, devicetype, dnt, ifa, ip, js, lmt, make, model, os, osv, clickbrowser ,geo_city, geo_country, geo_region, geo_type, dsp_id, adomain, cost_ratio, is_click) values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		String Check = "select * from ctr.ctr_name";
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
			
			
//			System.out.println(filtered_Filelist.length > 0);
			
			if(filtered_Filelist.length > 0) {
				for(int i=0;i<filtered_Filelist.length;i++) {
					for(int k=0;k<dbpath.size();k++) {
			

						for(int j=0;j<dbpath.size();j++) {
							System.out.println(dbpath.get(j));
						}
						
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
		List<Map<Object,Object>> dataPoints4 = new ArrayList<Map<Object,Object>>();
		List<Map<Object,Object>> dataPoints5 = new ArrayList<Map<Object,Object>>();
		List<Map<Object,Object>> dataPoints6 = new ArrayList<Map<Object,Object>>();
		List<Map<Object,Object>> dataPoints7 = new ArrayList<Map<Object,Object>>();
		List<Map<Object,Object>> dataPoints8 = new ArrayList<Map<Object,Object>>();
		

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
		
		int SK = 0;
		int LG = 0;
		int KT = 0;
		int UNKWON = 0;
		//lg
		int LG_CountyCountbra = 0;
		int LG_CountyCountcan = 0;
		int LG_CountyCountche = 0;
		int LG_CountyCountchn = 0;
		int LG_CountyCountdeu = 0;
		int LG_CountyCountdza = 0;
		int LG_CountyCountfra = 0;
		int LG_CountyCountgbr = 0;
		int LG_CountyCountgin = 0;
		int LG_CountyCountgum = 0;
		int LG_CountyCountidn = 0;
		int LG_CountyCountirq = 0;
		int LG_CountyCountjpn = 0;
		int LG_CountyCountkhm = 0;
		int LG_CountyCountkor = 0;
		int LG_CountyCountlao = 0;
        int LG_CountyCountmex = 0;
        int LG_CountyCountnzl = 0;
		int LG_CountyCountphl = 0;
		int LG_CountyCounttha = 0;
		int LG_CountyCounttwn = 0;
		int LG_CountyCountusa = 0;
		int LG_CountyCountvnm = 0;
		//kt
		int KT_CountyCountbra = 0;
		int KT_CountyCountcan = 0;
		int KT_CountyCountche = 0;
		int KT_CountyCountchn = 0;
		int KT_CountyCountdeu = 0;
		int KT_CountyCountdza = 0;
		int KT_CountyCountfra = 0;
		int KT_CountyCountgbr = 0;
		int KT_CountyCountgin = 0;
		int KT_CountyCountgum = 0;
		int KT_CountyCountidn = 0;
		int KT_CountyCountirq = 0;
		int KT_CountyCountjpn = 0;
		int KT_CountyCountkhm = 0;
		int KT_CountyCountkor = 0;
		int KT_CountyCountlao = 0;
        int KT_CountyCountmex = 0;
        int KT_CountyCountnzl = 0;
		int KT_CountyCountphl = 0;
		int KT_CountyCounttha = 0;
		int KT_CountyCounttwn = 0;
		int KT_CountyCountusa = 0;
		int KT_CountyCountvnm = 0;
		//skt
		int SKT_CountyCountbra = 0;
		int SKT_CountyCountcan = 0;
		int SKT_CountyCountche = 0;
		int SKT_CountyCountchn = 0;
		int SKT_CountyCountdeu = 0;
		int SKT_CountyCountdza = 0;
		int SKT_CountyCountfra = 0;
		int SKT_CountyCountgbr = 0;
		int SKT_CountyCountgin = 0;
		int SKT_CountyCountgum = 0;
		int SKT_CountyCountidn = 0;
		int SKT_CountyCountirq = 0;
		int SKT_CountyCountjpn = 0;
		int SKT_CountyCountkhm = 0;
		int SKT_CountyCountkor = 0;
		int SKT_CountyCountlao = 0;
        int SKT_CountyCountmex = 0;
        int SKT_CountyCountnzl = 0;
		int SKT_CountyCountphl = 0;
		int SKT_CountyCounttha = 0;
		int SKT_CountyCounttwn = 0;
		int SKT_CountyCountusa = 0;
		int SKT_CountyCountvnm = 0;
		//unknown
		int UNKNOWN_CountyCountbra = 0;
		int UNKNOWN_CountyCountcan = 0;
		int UNKNOWN_CountyCountche = 0;
		int UNKNOWN_CountyCountchn = 0;
		int UNKNOWN_CountyCountdeu = 0;
		int UNKNOWN_CountyCountdza = 0;
		int UNKNOWN_CountyCountfra = 0;
		int UNKNOWN_CountyCountgbr = 0;
		int UNKNOWN_CountyCountgin = 0;
		int UNKNOWN_CountyCountgum = 0;
		int UNKNOWN_CountyCountidn = 0;
		int UNKNOWN_CountyCountirq = 0;
		int UNKNOWN_CountyCountjpn = 0;
		int UNKNOWN_CountyCountkhm = 0;
		int UNKNOWN_CountyCountkor = 0;
		int UNKNOWN_CountyCountlao = 0;
        int UNKNOWN_CountyCountmex = 0;
        int UNKNOWN_CountyCountnzl = 0;
		int UNKNOWN_CountyCountphl = 0;
		int UNKNOWN_CountyCounttha = 0;
		int UNKNOWN_CountyCounttwn = 0;
		int UNKNOWN_CountyCountusa = 0;
		int UNKNOWN_CountyCountvnm = 0;
		
		
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("org.mariadb.jdbc.Driver");
		dataSource.setUrl("jdbc:mariadb://127.0.0.1:3307/ctr");
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
		
		String sk = "SELECT COUNT(carrier) FROM ctr_column WHERE carrier LIKE 'sk'";
		String lg = "SELECT COUNT(carrier) FROM ctr_column WHERE carrier LIKE 'lg'";
		String kt = "SELECT COUNT(carrier) FROM ctr_column WHERE carrier LIKE 'kt'";
		String unkwon = "SELECT COUNT(carrier) FROM ctr_column WHERE carrier LIKE 'unknown'";
		
		//LG
		String lg_CountyCountbra = "SELECT COUNT(geo_country) FROM ctr_column where carrier = 'lg' AND geo_country = 'bra'";
		String lg_CountyCountcan = "SELECT COUNT(geo_country) FROM ctr_column where carrier = 'lg' and geo_country = 'can'";
		String lg_CountyCountche = "SELECT COUNT(geo_country) FROM ctr_column where carrier = 'lg' and geo_country = 'che'";
		String lg_CountyCountchn = "SELECT COUNT(geo_country) FROM ctr_column where carrier = 'lg' and geo_country = 'chn'";
		String lg_CountyCountdeu = "SELECT COUNT(geo_country) FROM ctr_column where carrier = 'lg' and geo_country = 'deu'";
		String lg_CountyCountdza = "SELECT COUNT(geo_country) FROM ctr_column where carrier = 'lg' and geo_country = 'dza'";
		String lg_CountyCountfra = "SELECT COUNT(geo_country) FROM ctr_column where carrier = 'lg' and geo_country = 'fra'";
		String lg_CountyCountgbr = "SELECT COUNT(geo_country) FROM ctr_column where carrier = 'lg' and geo_country = 'gbr'";
		String lg_CountyCountgin = "SELECT COUNT(geo_country) FROM ctr_column where carrier = 'lg' and geo_country = 'gin'";
		String lg_CountyCountgum = "SELECT COUNT(geo_country) FROM ctr_column where carrier = 'lg' and geo_country = 'gum'";
		String lg_CountyCountidn = "SELECT COUNT(geo_country) FROM ctr_column where carrier = 'lg' and geo_country = 'idn'";
		String lg_CountyCountirq = "SELECT COUNT(geo_country) FROM ctr_column where carrier = 'lg' and geo_country = 'irq'";
		String lg_CountyCountjpn = "SELECT COUNT(geo_country) FROM ctr_column where carrier = 'lg' and geo_country = 'jpn'";
		String lg_CountyCountkhm = "SELECT COUNT(geo_country) FROM ctr_column where carrier = 'lg' and geo_country = 'khm'";
		String lg_CountyCountkor = "SELECT COUNT(geo_country) FROM ctr_column where carrier = 'lg' and geo_country = 'kor'";
		String lg_CountyCountlao = "SELECT COUNT(geo_country) FROM ctr_column where carrier = 'lg' and geo_country = 'lao'";
		String lg_CountyCountmex = "SELECT COUNT(geo_country) FROM ctr_column where carrier = 'lg' and geo_country = 'mex'";
		String lg_CountyCountnzl = "SELECT COUNT(geo_country) FROM ctr_column where carrier = 'lg' and geo_country = 'nzl'";
		String lg_CountyCountphl = "SELECT COUNT(geo_country) FROM ctr_column where carrier = 'lg' and geo_country = 'phl'";
		String lg_CountyCounttha = "SELECT COUNT(geo_country) FROM ctr_column where carrier = 'lg' and geo_country = 'tha'";
		String lg_CountyCounttwn = "SELECT COUNT(geo_country) FROM ctr_column where carrier = 'lg' and geo_country = 'twn'";
		String lg_CountyCountusa = "SELECT COUNT(geo_country) FROM ctr_column where carrier = 'lg' and geo_country = 'usa'";
		String lg_CountyCountvnm = "SELECT COUNT(geo_country) FROM ctr_column where carrier = 'lg' and geo_country = 'vnm'";
		
		//KT
		String kt_CountyCountbra = "SELECT COUNT(geo_country) FROM ctr_column where carrier = 'kt' AND geo_country = 'bra'";
		String kt_CountyCountcan = "SELECT COUNT(geo_country) FROM ctr_column where carrier = 'kt' and geo_country = 'can'";
		String kt_CountyCountche = "SELECT COUNT(geo_country) FROM ctr_column where carrier = 'kt' and geo_country = 'che'";
		String kt_CountyCountchn = "SELECT COUNT(geo_country) FROM ctr_column where carrier = 'kt' and geo_country = 'chn'";
		String kt_CountyCountdeu = "SELECT COUNT(geo_country) FROM ctr_column where carrier = 'kt' and geo_country = 'deu'";
		String kt_CountyCountdza = "SELECT COUNT(geo_country) FROM ctr_column where carrier = 'kt' and geo_country = 'dza'";
		String kt_CountyCountfra = "SELECT COUNT(geo_country) FROM ctr_column where carrier = 'kt' and geo_country = 'fra'";
		String kt_CountyCountgbr = "SELECT COUNT(geo_country) FROM ctr_column where carrier = 'kt' and geo_country = 'gbr'";
		String kt_CountyCountgin = "SELECT COUNT(geo_country) FROM ctr_column where carrier = 'kt' and geo_country = 'gin'";
		String kt_CountyCountgum = "SELECT COUNT(geo_country) FROM ctr_column where carrier = 'kt' and geo_country = 'gum'";
		String kt_CountyCountidn = "SELECT COUNT(geo_country) FROM ctr_column where carrier = 'kt' and geo_country = 'idn'";
		String kt_CountyCountirq = "SELECT COUNT(geo_country) FROM ctr_column where carrier = 'kt' and geo_country = 'irq'";
		String kt_CountyCountjpn = "SELECT COUNT(geo_country) FROM ctr_column where carrier = 'kt' and geo_country = 'jpn'";
		String kt_CountyCountkhm = "SELECT COUNT(geo_country) FROM ctr_column where carrier = 'kt' and geo_country = 'khm'";
		String kt_CountyCountkor = "SELECT COUNT(geo_country) FROM ctr_column where carrier = 'kt' and geo_country = 'kor'";
		String kt_CountyCountlao = "SELECT COUNT(geo_country) FROM ctr_column where carrier = 'kt' and geo_country = 'lao'";
		String kt_CountyCountmex = "SELECT COUNT(geo_country) FROM ctr_column where carrier = 'kt' and geo_country = 'mex'";
		String kt_CountyCountnzl = "SELECT COUNT(geo_country) FROM ctr_column where carrier = 'kt' and geo_country = 'nzl'";
		String kt_CountyCountphl = "SELECT COUNT(geo_country) FROM ctr_column where carrier = 'kt' and geo_country = 'phl'";
		String kt_CountyCounttha = "SELECT COUNT(geo_country) FROM ctr_column where carrier = 'kt' and geo_country = 'tha'";
		String kt_CountyCounttwn = "SELECT COUNT(geo_country) FROM ctr_column where carrier = 'kt' and geo_country = 'twn'";
		String kt_CountyCountusa = "SELECT COUNT(geo_country) FROM ctr_column where carrier = 'kt' and geo_country = 'usa'";
		String kt_CountyCountvnm = "SELECT COUNT(geo_country) FROM ctr_column where carrier = 'kt' and geo_country = 'vnm'";
		
		//SKT
		String skt_CountyCountbra = "SELECT COUNT(geo_country) FROM ctr_column where carrier = 'sk' AND geo_country = 'bra'";
		String skt_CountyCountcan = "SELECT COUNT(geo_country) FROM ctr_column where carrier = 'sk' and geo_country = 'can'";
		String skt_CountyCountche = "SELECT COUNT(geo_country) FROM ctr_column where carrier = 'sk' and geo_country = 'che'";
		String skt_CountyCountchn = "SELECT COUNT(geo_country) FROM ctr_column where carrier = 'sk' and geo_country = 'chn'";
		String skt_CountyCountdeu = "SELECT COUNT(geo_country) FROM ctr_column where carrier = 'sk' and geo_country = 'deu'";
		String skt_CountyCountdza = "SELECT COUNT(geo_country) FROM ctr_column where carrier = 'sk' and geo_country = 'dza'";
		String skt_CountyCountfra = "SELECT COUNT(geo_country) FROM ctr_column where carrier = 'sk' and geo_country = 'fra'";
		String skt_CountyCountgbr = "SELECT COUNT(geo_country) FROM ctr_column where carrier = 'sk' and geo_country = 'gbr'";
		String skt_CountyCountgin = "SELECT COUNT(geo_country) FROM ctr_column where carrier = 'sk' and geo_country = 'gin'";
		String skt_CountyCountgum = "SELECT COUNT(geo_country) FROM ctr_column where carrier = 'sk' and geo_country = 'gum'";
		String skt_CountyCountidn = "SELECT COUNT(geo_country) FROM ctr_column where carrier = 'sk' and geo_country = 'idn'";
		String skt_CountyCountirq = "SELECT COUNT(geo_country) FROM ctr_column where carrier = 'sk' and geo_country = 'irq'";
		String skt_CountyCountjpn = "SELECT COUNT(geo_country) FROM ctr_column where carrier = 'sk' and geo_country = 'jpn'";
		String skt_CountyCountkhm = "SELECT COUNT(geo_country) FROM ctr_column where carrier = 'sk' and geo_country = 'khm'";
		String skt_CountyCountkor = "SELECT COUNT(geo_country) FROM ctr_column where carrier = 'sk' and geo_country = 'kor'";
		String skt_CountyCountlao = "SELECT COUNT(geo_country) FROM ctr_column where carrier = 'sk' and geo_country = 'lao'";
		String skt_CountyCountmex = "SELECT COUNT(geo_country) FROM ctr_column where carrier = 'sk' and geo_country = 'mex'";
		String skt_CountyCountnzl = "SELECT COUNT(geo_country) FROM ctr_column where carrier = 'sk' and geo_country = 'nzl'";
		String skt_CountyCountphl = "SELECT COUNT(geo_country) FROM ctr_column where carrier = 'sk' and geo_country = 'phl'";
		String skt_CountyCounttha = "SELECT COUNT(geo_country) FROM ctr_column where carrier = 'sk' and geo_country = 'tha'";
		String skt_CountyCounttwn = "SELECT COUNT(geo_country) FROM ctr_column where carrier = 'sk' and geo_country = 'twn'";
		String skt_CountyCountusa = "SELECT COUNT(geo_country) FROM ctr_column where carrier = 'sk' and geo_country = 'usa'";
		String skt_CountyCountvnm = "SELECT COUNT(geo_country) FROM ctr_column where carrier = 'sk' and geo_country = 'vnm'";
				
		//UNKWON
		String unknown_CountyCountbra = "SELECT COUNT(geo_country) FROM ctr_column where carrier = 'unknown' AND geo_country = 'bra'";
		String unknown_CountyCountcan = "SELECT COUNT(geo_country) FROM ctr_column where carrier = 'unknown' and geo_country = 'can'";
		String unknown_CountyCountche = "SELECT COUNT(geo_country) FROM ctr_column where carrier = 'unknown' and geo_country = 'che'";
		String unknown_CountyCountchn = "SELECT COUNT(geo_country) FROM ctr_column where carrier = 'unknown' and geo_country = 'chn'";
		String unknown_CountyCountdeu = "SELECT COUNT(geo_country) FROM ctr_column where carrier = 'unknown' and geo_country = 'deu'";
		String unknown_CountyCountdza = "SELECT COUNT(geo_country) FROM ctr_column where carrier = 'unknown' and geo_country = 'dza'";
		String unknown_CountyCountfra = "SELECT COUNT(geo_country) FROM ctr_column where carrier = 'unknown' and geo_country = 'fra'";
		String unknown_CountyCountgbr = "SELECT COUNT(geo_country) FROM ctr_column where carrier = 'unknown' and geo_country = 'gbr'";
		String unknown_CountyCountgin = "SELECT COUNT(geo_country) FROM ctr_column where carrier = 'unknown' and geo_country = 'gin'";
		String unknown_CountyCountgum = "SELECT COUNT(geo_country) FROM ctr_column where carrier = 'unknown' and geo_country = 'gum'";
		String unknown_CountyCountidn = "SELECT COUNT(geo_country) FROM ctr_column where carrier = 'unknown' and geo_country = 'idn'";
		String unknown_CountyCountirq = "SELECT COUNT(geo_country) FROM ctr_column where carrier = 'unknown' and geo_country = 'irq'";
		String unknown_CountyCountjpn = "SELECT COUNT(geo_country) FROM ctr_column where carrier = 'unknown' and geo_country = 'jpn'";
		String unknown_CountyCountkhm = "SELECT COUNT(geo_country) FROM ctr_column where carrier = 'unknown' and geo_country = 'khm'";
		String unknown_CountyCountkor = "SELECT COUNT(geo_country) FROM ctr_column where carrier = 'unknown' and geo_country = 'kor'";
		String unknown_CountyCountlao = "SELECT COUNT(geo_country) FROM ctr_column where carrier = 'unknown' and geo_country = 'lao'";
		String unknown_CountyCountmex = "SELECT COUNT(geo_country) FROM ctr_column where carrier = 'unknown' and geo_country = 'mex'";
		String unknown_CountyCountnzl = "SELECT COUNT(geo_country) FROM ctr_column where carrier = 'unknown' and geo_country = 'nzl'";
		String unknown_CountyCountphl = "SELECT COUNT(geo_country) FROM ctr_column where carrier = 'unknown' and geo_country = 'phl'";
		String unknown_CountyCounttha = "SELECT COUNT(geo_country) FROM ctr_column where carrier = 'unknown' and geo_country = 'tha'";
		String unknown_CountyCounttwn = "SELECT COUNT(geo_country) FROM ctr_column where carrier = 'unknown' and geo_country = 'twn'";
		String unknown_CountyCountusa = "SELECT COUNT(geo_country) FROM ctr_column where carrier = 'unknown' and geo_country = 'usa'";
		String unknown_CountyCountvnm = "SELECT COUNT(geo_country) FROM ctr_column where carrier = 'unknown' and geo_country = 'vnm'";
		                          
		  /**                       
		 * SELECT COUNT(geo_country) FROM ctr_column where carrier = 'unknown' and geo_country = 'bra'
		 * SELECT COUNT(geo_country) FROM ctr_column where carrier = 'unknown' and geo_country = 'can'
		 * SELECT COUNT(geo_country) FROM ctr_column where carrier = 'unknown' and geo_country = 'che'
		 * SELECT COUNT(geo_country) FROM ctr_column where carrier = 'unknown' and geo_country = 'chn'
		 * SELECT COUNT(geo_country) FROM ctr_column where carrier = 'unknown' and geo_country = 'deu'
		 * SELECT COUNT(geo_country) FROM ctr_column where carrier = 'unknown' and geo_country = 'dza'
		 * SELECT COUNT(geo_country) FROM ctr_column where carrier = 'unknown' and geo_country = 'fra'
		 * SELECT COUNT(geo_country) FROM ctr_column where carrier = 'unknown' and geo_country = 'gbr'
		 * SELECT COUNT(geo_country) FROM ctr_column where carrier = 'unknown' and geo_country = 'gin'
		 * SELECT COUNT(geo_country) FROM ctr_column where carrier = 'unknown' and geo_country = 'gum'
		 * SELECT COUNT(geo_country) FROM ctr_column where carrier = 'unknown' and geo_country = 'idn'
		 * SELECT COUNT(geo_country) FROM ctr_column where carrier = 'unknown' and geo_country = 'irq'
		 * SELECT COUNT(geo_country) FROM ctr_column where carrier = 'unknown' and geo_country = 'jpn'
		 * SELECT COUNT(geo_country) FROM ctr_column where carrier = 'unknown' and geo_country = 'khm'
		 * SELECT COUNT(geo_country) FROM ctr_column where carrier = 'unknown' and geo_country = 'kor'
		 * SELECT COUNT(geo_country) FROM ctr_column where carrier = 'unknown' and geo_country = 'lao'
		 * SELECT COUNT(geo_country) FROM ctr_column where carrier = 'unknown' and geo_country = 'mex'
		 * SELECT COUNT(geo_country) FROM ctr_column where carrier = 'unknown' and geo_country = 'nzl'
		 * SELECT COUNT(geo_country) FROM ctr_column where carrier = 'unknown' and geo_country = 'phl'
		 * SELECT COUNT(geo_country) FROM ctr_column where carrier = 'unknown' and geo_country = 'tha'
		 * SELECT COUNT(geo_country) FROM ctr_column where carrier = 'unknown' and geo_country = 'twn'
		 * SELECT COUNT(geo_country) FROM ctr_column where carrier = 'unknown' and geo_country = 'usa'
		 * SELECT COUNT(geo_country) FROM ctr_column where carrier = 'unknown' and geo_country = 'vnm'
		 */ 
		
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
		
		SK = jdbcTemplate.queryForObject(sk,Integer.class);
		LG = jdbcTemplate.queryForObject(lg,Integer.class);
		KT = jdbcTemplate.queryForObject(kt,Integer.class);
		UNKWON = jdbcTemplate.queryForObject(unkwon,Integer.class);
		
		//lg
		LG_CountyCountbra = jdbcTemplate.queryForObject(lg_CountyCountbra,Integer.class);
		LG_CountyCountcan = jdbcTemplate.queryForObject(lg_CountyCountcan,Integer.class);
		LG_CountyCountche = jdbcTemplate.queryForObject(lg_CountyCountche,Integer.class);
		LG_CountyCountchn = jdbcTemplate.queryForObject(lg_CountyCountchn,Integer.class);
		LG_CountyCountdeu = jdbcTemplate.queryForObject(lg_CountyCountdeu,Integer.class);
		LG_CountyCountdza = jdbcTemplate.queryForObject(lg_CountyCountdza,Integer.class);
		LG_CountyCountfra = jdbcTemplate.queryForObject(lg_CountyCountfra,Integer.class);
		LG_CountyCountgbr = jdbcTemplate.queryForObject(lg_CountyCountgbr,Integer.class);
		LG_CountyCountgin = jdbcTemplate.queryForObject(lg_CountyCountgin,Integer.class);
		LG_CountyCountgum = jdbcTemplate.queryForObject(lg_CountyCountgum,Integer.class);
		LG_CountyCountidn = jdbcTemplate.queryForObject(lg_CountyCountidn,Integer.class);
		LG_CountyCountirq = jdbcTemplate.queryForObject(lg_CountyCountirq,Integer.class);
		LG_CountyCountjpn = jdbcTemplate.queryForObject(lg_CountyCountjpn,Integer.class);
		LG_CountyCountkhm = jdbcTemplate.queryForObject(lg_CountyCountkhm,Integer.class);
		LG_CountyCountkor = jdbcTemplate.queryForObject(lg_CountyCountkor,Integer.class);
		LG_CountyCountlao = jdbcTemplate.queryForObject(lg_CountyCountlao,Integer.class);
		LG_CountyCountmex = jdbcTemplate.queryForObject(lg_CountyCountmex,Integer.class);
		LG_CountyCountnzl = jdbcTemplate.queryForObject(lg_CountyCountnzl,Integer.class);
		LG_CountyCountphl = jdbcTemplate.queryForObject(lg_CountyCountphl,Integer.class);
		LG_CountyCounttha = jdbcTemplate.queryForObject(lg_CountyCounttha,Integer.class);
		LG_CountyCounttwn = jdbcTemplate.queryForObject(lg_CountyCounttwn,Integer.class);
		LG_CountyCountusa = jdbcTemplate.queryForObject(lg_CountyCountusa,Integer.class);
		LG_CountyCountvnm = jdbcTemplate.queryForObject(lg_CountyCountvnm,Integer.class);
		
		//kt
		KT_CountyCountbra = jdbcTemplate.queryForObject(kt_CountyCountbra,Integer.class);
		KT_CountyCountcan = jdbcTemplate.queryForObject(kt_CountyCountcan,Integer.class);
		KT_CountyCountche = jdbcTemplate.queryForObject(kt_CountyCountche,Integer.class);
		KT_CountyCountchn = jdbcTemplate.queryForObject(kt_CountyCountchn,Integer.class);
		KT_CountyCountdeu = jdbcTemplate.queryForObject(kt_CountyCountdeu,Integer.class);
		KT_CountyCountdza = jdbcTemplate.queryForObject(kt_CountyCountdza,Integer.class);
		KT_CountyCountfra = jdbcTemplate.queryForObject(kt_CountyCountfra,Integer.class);
		KT_CountyCountgbr = jdbcTemplate.queryForObject(kt_CountyCountgbr,Integer.class);
		KT_CountyCountgin = jdbcTemplate.queryForObject(kt_CountyCountgin,Integer.class);
		KT_CountyCountgum = jdbcTemplate.queryForObject(kt_CountyCountgum,Integer.class);
		KT_CountyCountidn = jdbcTemplate.queryForObject(kt_CountyCountidn,Integer.class);
		KT_CountyCountirq = jdbcTemplate.queryForObject(kt_CountyCountirq,Integer.class);
		KT_CountyCountjpn = jdbcTemplate.queryForObject(kt_CountyCountjpn,Integer.class);
		KT_CountyCountkhm = jdbcTemplate.queryForObject(kt_CountyCountkhm,Integer.class);
		KT_CountyCountkor = jdbcTemplate.queryForObject(kt_CountyCountkor,Integer.class);
		KT_CountyCountlao = jdbcTemplate.queryForObject(kt_CountyCountlao,Integer.class);
		KT_CountyCountmex = jdbcTemplate.queryForObject(kt_CountyCountmex,Integer.class);
		KT_CountyCountnzl = jdbcTemplate.queryForObject(kt_CountyCountnzl,Integer.class);
		KT_CountyCountphl = jdbcTemplate.queryForObject(kt_CountyCountphl,Integer.class);
		KT_CountyCounttha = jdbcTemplate.queryForObject(kt_CountyCounttha,Integer.class);
		KT_CountyCounttwn = jdbcTemplate.queryForObject(kt_CountyCounttwn,Integer.class);
		KT_CountyCountusa = jdbcTemplate.queryForObject(kt_CountyCountusa,Integer.class);
		KT_CountyCountvnm = jdbcTemplate.queryForObject(kt_CountyCountvnm,Integer.class);
		
		//skt
		SKT_CountyCountbra = jdbcTemplate.queryForObject(skt_CountyCountbra,Integer.class);
		SKT_CountyCountcan = jdbcTemplate.queryForObject(skt_CountyCountcan,Integer.class);
		SKT_CountyCountche = jdbcTemplate.queryForObject(skt_CountyCountche,Integer.class);
		SKT_CountyCountchn = jdbcTemplate.queryForObject(skt_CountyCountchn,Integer.class);
		SKT_CountyCountdeu = jdbcTemplate.queryForObject(skt_CountyCountdeu,Integer.class);
		SKT_CountyCountdza = jdbcTemplate.queryForObject(skt_CountyCountdza,Integer.class);
		SKT_CountyCountfra = jdbcTemplate.queryForObject(skt_CountyCountfra,Integer.class);
		SKT_CountyCountgbr = jdbcTemplate.queryForObject(skt_CountyCountgbr,Integer.class);
		SKT_CountyCountgin = jdbcTemplate.queryForObject(skt_CountyCountgin,Integer.class);
		SKT_CountyCountgum = jdbcTemplate.queryForObject(skt_CountyCountgum,Integer.class);
		SKT_CountyCountidn = jdbcTemplate.queryForObject(skt_CountyCountidn,Integer.class);
		SKT_CountyCountirq = jdbcTemplate.queryForObject(skt_CountyCountirq,Integer.class);
		SKT_CountyCountjpn = jdbcTemplate.queryForObject(skt_CountyCountjpn,Integer.class);
		SKT_CountyCountkhm = jdbcTemplate.queryForObject(skt_CountyCountkhm,Integer.class);
		SKT_CountyCountkor = jdbcTemplate.queryForObject(skt_CountyCountkor,Integer.class);
		SKT_CountyCountlao = jdbcTemplate.queryForObject(skt_CountyCountlao,Integer.class);
		SKT_CountyCountmex = jdbcTemplate.queryForObject(skt_CountyCountmex,Integer.class);
		SKT_CountyCountnzl = jdbcTemplate.queryForObject(skt_CountyCountnzl,Integer.class);
		SKT_CountyCountphl = jdbcTemplate.queryForObject(skt_CountyCountphl,Integer.class);
		SKT_CountyCounttha = jdbcTemplate.queryForObject(skt_CountyCounttha,Integer.class);
		SKT_CountyCounttwn = jdbcTemplate.queryForObject(skt_CountyCounttwn,Integer.class);
		SKT_CountyCountusa = jdbcTemplate.queryForObject(skt_CountyCountusa,Integer.class);
		SKT_CountyCountvnm = jdbcTemplate.queryForObject(skt_CountyCountvnm,Integer.class);
		
		//unknown
		UNKNOWN_CountyCountbra = jdbcTemplate.queryForObject(unknown_CountyCountbra,Integer.class);
		UNKNOWN_CountyCountcan = jdbcTemplate.queryForObject(unknown_CountyCountcan,Integer.class);
		UNKNOWN_CountyCountche = jdbcTemplate.queryForObject(unknown_CountyCountche,Integer.class);
		UNKNOWN_CountyCountchn = jdbcTemplate.queryForObject(unknown_CountyCountchn,Integer.class);
		UNKNOWN_CountyCountdeu = jdbcTemplate.queryForObject(unknown_CountyCountdeu,Integer.class);
		UNKNOWN_CountyCountdza = jdbcTemplate.queryForObject(unknown_CountyCountdza,Integer.class);
		UNKNOWN_CountyCountfra = jdbcTemplate.queryForObject(unknown_CountyCountfra,Integer.class);
		UNKNOWN_CountyCountgbr = jdbcTemplate.queryForObject(unknown_CountyCountgbr,Integer.class);
		UNKNOWN_CountyCountgin = jdbcTemplate.queryForObject(unknown_CountyCountgin,Integer.class);
		UNKNOWN_CountyCountgum = jdbcTemplate.queryForObject(unknown_CountyCountgum,Integer.class);
		UNKNOWN_CountyCountidn = jdbcTemplate.queryForObject(unknown_CountyCountidn,Integer.class);
		UNKNOWN_CountyCountirq = jdbcTemplate.queryForObject(unknown_CountyCountirq,Integer.class);
		UNKNOWN_CountyCountjpn = jdbcTemplate.queryForObject(unknown_CountyCountjpn,Integer.class);
		UNKNOWN_CountyCountkhm = jdbcTemplate.queryForObject(unknown_CountyCountkhm,Integer.class);
		UNKNOWN_CountyCountkor = jdbcTemplate.queryForObject(unknown_CountyCountkor,Integer.class);
		UNKNOWN_CountyCountlao = jdbcTemplate.queryForObject(unknown_CountyCountlao,Integer.class);
		UNKNOWN_CountyCountmex = jdbcTemplate.queryForObject(unknown_CountyCountmex,Integer.class);
		UNKNOWN_CountyCountnzl = jdbcTemplate.queryForObject(unknown_CountyCountnzl,Integer.class);
		UNKNOWN_CountyCountphl = jdbcTemplate.queryForObject(unknown_CountyCountphl,Integer.class);
		UNKNOWN_CountyCounttha = jdbcTemplate.queryForObject(unknown_CountyCounttha,Integer.class);
		UNKNOWN_CountyCounttwn = jdbcTemplate.queryForObject(unknown_CountyCounttwn,Integer.class);
		UNKNOWN_CountyCountusa = jdbcTemplate.queryForObject(unknown_CountyCountusa,Integer.class);
		UNKNOWN_CountyCountvnm = jdbcTemplate.queryForObject(unknown_CountyCountvnm,Integer.class);

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
		
		map = new HashMap<Object,Object>(); map.put("name", "SKT"); map.put("y", SK); map.put("color", "#E7823A");dataPoints4.add(map);
		map = new HashMap<Object,Object>(); map.put("name", "LG"); map.put("y", LG); map.put("color", "#546BC1");dataPoints4.add(map);
		map = new HashMap<Object,Object>(); map.put("name", "KT"); map.put("y", KT); map.put("color", "#54c158");dataPoints4.add(map);
		map = new HashMap<Object,Object>(); map.put("name", "UNKWON"); map.put("y", UNKWON); map.put("color", "#c15454");dataPoints4.add(map);
		//LG
		map = new HashMap<Object,Object>(); map.put("label", "bra"); map.put("y", LG_CountyCountbra);dataPoints5.add(map);
		map = new HashMap<Object,Object>(); map.put("label", "can"); map.put("y", LG_CountyCountcan);dataPoints5.add(map);
		map = new HashMap<Object,Object>(); map.put("label", "che"); map.put("y", LG_CountyCountche);dataPoints5.add(map);
		map = new HashMap<Object,Object>(); map.put("label", "chn"); map.put("y", LG_CountyCountchn);dataPoints5.add(map);
		map = new HashMap<Object,Object>(); map.put("label", "deu"); map.put("y", LG_CountyCountdeu);dataPoints5.add(map);
		map = new HashMap<Object,Object>(); map.put("label", "dza"); map.put("y", LG_CountyCountdza);dataPoints5.add(map);
		map = new HashMap<Object,Object>(); map.put("label", "fra"); map.put("y", LG_CountyCountfra);dataPoints5.add(map);
		map = new HashMap<Object,Object>(); map.put("label", "gbr"); map.put("y", LG_CountyCountgbr);dataPoints5.add(map);
		map = new HashMap<Object,Object>(); map.put("label", "gin"); map.put("y", LG_CountyCountgin);dataPoints5.add(map);
		map = new HashMap<Object,Object>(); map.put("label", "gum"); map.put("y", LG_CountyCountgum);dataPoints5.add(map);
		map = new HashMap<Object,Object>(); map.put("label", "idn"); map.put("y", LG_CountyCountidn);dataPoints5.add(map);
		map = new HashMap<Object,Object>(); map.put("label", "irq"); map.put("y", LG_CountyCountirq);dataPoints5.add(map);
		map = new HashMap<Object,Object>(); map.put("label", "jpn"); map.put("y", LG_CountyCountjpn);dataPoints5.add(map);
		map = new HashMap<Object,Object>(); map.put("label", "khm"); map.put("y", LG_CountyCountkhm);dataPoints5.add(map);
		map = new HashMap<Object,Object>(); map.put("label", "kor"); map.put("y", LG_CountyCountkor);dataPoints5.add(map);
		map = new HashMap<Object,Object>(); map.put("label", "lao"); map.put("y", LG_CountyCountlao);dataPoints5.add(map);
		map = new HashMap<Object,Object>(); map.put("label", "mex"); map.put("y", LG_CountyCountmex);dataPoints5.add(map);
		map = new HashMap<Object,Object>(); map.put("label", "nzl"); map.put("y", LG_CountyCountnzl);dataPoints5.add(map);
		map = new HashMap<Object,Object>(); map.put("label", "phl"); map.put("y", LG_CountyCountphl);dataPoints5.add(map);
		map = new HashMap<Object,Object>(); map.put("label", "tha"); map.put("y", LG_CountyCounttha);dataPoints5.add(map);
		map = new HashMap<Object,Object>(); map.put("label", "twn"); map.put("y", LG_CountyCounttwn);dataPoints5.add(map);
		map = new HashMap<Object,Object>(); map.put("label", "usa"); map.put("y", LG_CountyCountusa);dataPoints5.add(map);
		map = new HashMap<Object,Object>(); map.put("label", "vnm"); map.put("y", LG_CountyCountvnm);dataPoints5.add(map);
		//KT
		map = new HashMap<Object,Object>(); map.put("label", "bra"); map.put("y", KT_CountyCountbra);dataPoints6.add(map);
		map = new HashMap<Object,Object>(); map.put("label", "can"); map.put("y", KT_CountyCountcan);dataPoints6.add(map);
		map = new HashMap<Object,Object>(); map.put("label", "che"); map.put("y", KT_CountyCountche);dataPoints6.add(map);
		map = new HashMap<Object,Object>(); map.put("label", "chn"); map.put("y", KT_CountyCountchn);dataPoints6.add(map);
		map = new HashMap<Object,Object>(); map.put("label", "deu"); map.put("y", KT_CountyCountdeu);dataPoints6.add(map);
		map = new HashMap<Object,Object>(); map.put("label", "dza"); map.put("y", KT_CountyCountdza);dataPoints6.add(map);
		map = new HashMap<Object,Object>(); map.put("label", "fra"); map.put("y", KT_CountyCountfra);dataPoints6.add(map);
		map = new HashMap<Object,Object>(); map.put("label", "gbr"); map.put("y", KT_CountyCountgbr);dataPoints6.add(map);
		map = new HashMap<Object,Object>(); map.put("label", "gin"); map.put("y", KT_CountyCountgin);dataPoints6.add(map);
		map = new HashMap<Object,Object>(); map.put("label", "gum"); map.put("y", KT_CountyCountgum);dataPoints6.add(map);
		map = new HashMap<Object,Object>(); map.put("label", "idn"); map.put("y", KT_CountyCountidn);dataPoints6.add(map);
		map = new HashMap<Object,Object>(); map.put("label", "irq"); map.put("y", KT_CountyCountirq);dataPoints6.add(map);
		map = new HashMap<Object,Object>(); map.put("label", "jpn"); map.put("y", KT_CountyCountjpn);dataPoints6.add(map);
		map = new HashMap<Object,Object>(); map.put("label", "khm"); map.put("y", KT_CountyCountkhm);dataPoints6.add(map);
		map = new HashMap<Object,Object>(); map.put("label", "kor"); map.put("y", KT_CountyCountkor);dataPoints6.add(map);
		map = new HashMap<Object,Object>(); map.put("label", "lao"); map.put("y", KT_CountyCountlao);dataPoints6.add(map);
		map = new HashMap<Object,Object>(); map.put("label", "mex"); map.put("y", KT_CountyCountmex);dataPoints6.add(map);
		map = new HashMap<Object,Object>(); map.put("label", "nzl"); map.put("y", KT_CountyCountnzl);dataPoints6.add(map);
		map = new HashMap<Object,Object>(); map.put("label", "phl"); map.put("y", KT_CountyCountphl);dataPoints6.add(map);
		map = new HashMap<Object,Object>(); map.put("label", "tha"); map.put("y", KT_CountyCounttha);dataPoints6.add(map);
		map = new HashMap<Object,Object>(); map.put("label", "twn"); map.put("y", KT_CountyCounttwn);dataPoints6.add(map);
		map = new HashMap<Object,Object>(); map.put("label", "usa"); map.put("y", KT_CountyCountusa);dataPoints6.add(map);
		map = new HashMap<Object,Object>(); map.put("label", "vnm"); map.put("y", KT_CountyCountvnm);dataPoints6.add(map);
		//SKT
		map = new HashMap<Object,Object>(); map.put("label", "bra"); map.put("y", SKT_CountyCountbra);dataPoints7.add(map);
		map = new HashMap<Object,Object>(); map.put("label", "can"); map.put("y", SKT_CountyCountcan);dataPoints7.add(map);
		map = new HashMap<Object,Object>(); map.put("label", "che"); map.put("y", SKT_CountyCountche);dataPoints7.add(map);
		map = new HashMap<Object,Object>(); map.put("label", "chn"); map.put("y", SKT_CountyCountchn);dataPoints7.add(map);
		map = new HashMap<Object,Object>(); map.put("label", "deu"); map.put("y", SKT_CountyCountdeu);dataPoints7.add(map);
		map = new HashMap<Object,Object>(); map.put("label", "dza"); map.put("y", SKT_CountyCountdza);dataPoints7.add(map);
		map = new HashMap<Object,Object>(); map.put("label", "fra"); map.put("y", SKT_CountyCountfra);dataPoints7.add(map);
		map = new HashMap<Object,Object>(); map.put("label", "gbr"); map.put("y", SKT_CountyCountgbr);dataPoints7.add(map);
		map = new HashMap<Object,Object>(); map.put("label", "gin"); map.put("y", SKT_CountyCountgin);dataPoints7.add(map);
		map = new HashMap<Object,Object>(); map.put("label", "gum"); map.put("y", SKT_CountyCountgum);dataPoints7.add(map);
		map = new HashMap<Object,Object>(); map.put("label", "idn"); map.put("y", SKT_CountyCountidn);dataPoints7.add(map);
		map = new HashMap<Object,Object>(); map.put("label", "irq"); map.put("y", SKT_CountyCountirq);dataPoints7.add(map);
		map = new HashMap<Object,Object>(); map.put("label", "jpn"); map.put("y", SKT_CountyCountjpn);dataPoints7.add(map);
		map = new HashMap<Object,Object>(); map.put("label", "khm"); map.put("y", SKT_CountyCountkhm);dataPoints7.add(map);
		map = new HashMap<Object,Object>(); map.put("label", "kor"); map.put("y", SKT_CountyCountkor);dataPoints7.add(map);
		map = new HashMap<Object,Object>(); map.put("label", "lao"); map.put("y", SKT_CountyCountlao);dataPoints7.add(map);
		map = new HashMap<Object,Object>(); map.put("label", "mex"); map.put("y", SKT_CountyCountmex);dataPoints7.add(map);
		map = new HashMap<Object,Object>(); map.put("label", "nzl"); map.put("y", SKT_CountyCountnzl);dataPoints7.add(map);
		map = new HashMap<Object,Object>(); map.put("label", "phl"); map.put("y", SKT_CountyCountphl);dataPoints7.add(map);
		map = new HashMap<Object,Object>(); map.put("label", "tha"); map.put("y", SKT_CountyCounttha);dataPoints7.add(map);
		map = new HashMap<Object,Object>(); map.put("label", "twn"); map.put("y", SKT_CountyCounttwn);dataPoints7.add(map);
		map = new HashMap<Object,Object>(); map.put("label", "usa"); map.put("y", SKT_CountyCountusa);dataPoints7.add(map);
		map = new HashMap<Object,Object>(); map.put("label", "vnm"); map.put("y", SKT_CountyCountvnm);dataPoints7.add(map);
		//UNKWON
		map = new HashMap<Object,Object>(); map.put("label", "bra"); map.put("y", UNKNOWN_CountyCountbra);dataPoints8.add(map);
		map = new HashMap<Object,Object>(); map.put("label", "can"); map.put("y", UNKNOWN_CountyCountcan);dataPoints8.add(map);
		map = new HashMap<Object,Object>(); map.put("label", "che"); map.put("y", UNKNOWN_CountyCountche);dataPoints8.add(map);
		map = new HashMap<Object,Object>(); map.put("label", "chn"); map.put("y", UNKNOWN_CountyCountchn);dataPoints8.add(map);
		map = new HashMap<Object,Object>(); map.put("label", "deu"); map.put("y", UNKNOWN_CountyCountdeu);dataPoints8.add(map);
		map = new HashMap<Object,Object>(); map.put("label", "dza"); map.put("y", UNKNOWN_CountyCountdza);dataPoints8.add(map);
		map = new HashMap<Object,Object>(); map.put("label", "fra"); map.put("y", UNKNOWN_CountyCountfra);dataPoints8.add(map);
		map = new HashMap<Object,Object>(); map.put("label", "gbr"); map.put("y", UNKNOWN_CountyCountgbr);dataPoints8.add(map);
		map = new HashMap<Object,Object>(); map.put("label", "gin"); map.put("y", UNKNOWN_CountyCountgin);dataPoints8.add(map);
		map = new HashMap<Object,Object>(); map.put("label", "gum"); map.put("y", UNKNOWN_CountyCountgum);dataPoints8.add(map);
		map = new HashMap<Object,Object>(); map.put("label", "idn"); map.put("y", UNKNOWN_CountyCountidn);dataPoints8.add(map);
		map = new HashMap<Object,Object>(); map.put("label", "irq"); map.put("y", UNKNOWN_CountyCountirq);dataPoints8.add(map);
		map = new HashMap<Object,Object>(); map.put("label", "jpn"); map.put("y", UNKNOWN_CountyCountjpn);dataPoints8.add(map);
		map = new HashMap<Object,Object>(); map.put("label", "khm"); map.put("y", UNKNOWN_CountyCountkhm);dataPoints8.add(map);
		map = new HashMap<Object,Object>(); map.put("label", "kor"); map.put("y", UNKNOWN_CountyCountkor);dataPoints8.add(map);
		map = new HashMap<Object,Object>(); map.put("label", "lao"); map.put("y", UNKNOWN_CountyCountlao);dataPoints8.add(map);
		map = new HashMap<Object,Object>(); map.put("label", "mex"); map.put("y", UNKNOWN_CountyCountmex);dataPoints8.add(map);
		map = new HashMap<Object,Object>(); map.put("label", "nzl"); map.put("y", UNKNOWN_CountyCountnzl);dataPoints8.add(map);
		map = new HashMap<Object,Object>(); map.put("label", "phl"); map.put("y", UNKNOWN_CountyCountphl);dataPoints8.add(map);
		map = new HashMap<Object,Object>(); map.put("label", "tha"); map.put("y", UNKNOWN_CountyCounttha);dataPoints8.add(map);
		map = new HashMap<Object,Object>(); map.put("label", "twn"); map.put("y", UNKNOWN_CountyCounttwn);dataPoints8.add(map);
		map = new HashMap<Object,Object>(); map.put("label", "usa"); map.put("y", UNKNOWN_CountyCountusa);dataPoints8.add(map);
		map = new HashMap<Object,Object>(); map.put("label", "vnm"); map.put("y", UNKNOWN_CountyCountvnm);dataPoints8.add(map);
		
		list.add(dataPoints1);                                               
		list.add(dataPoints2);                                               
		list.add(dataPoints3);                                           
		list.add(dataPoints4);
		list.add(dataPoints5);
		list.add(dataPoints6);
		list.add(dataPoints7);
		list.add(dataPoints8);
		
		System.out.println("Thread End");
		return new AsyncResult<String>("End");

	}
	
	public static List<List<Map<Object, Object>>> PostCanvasjsDataList(){
		return list;
	}
}             