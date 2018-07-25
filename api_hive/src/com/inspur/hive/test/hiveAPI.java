package com.inspur.hive.test;


import java.io.PrintStream;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.inspur.hive.test.hiveAPI;
import com.inspur.hive.util.dao;
import com.inspur.hive.util.logException;

public class hiveAPI {
	public static Logger logger = Logger.getLogger(hiveAPI.class);
	static logException le = new logException();

	public static void main(String[] args) {
		int i = 0, j = 0;
		HiveUtil.confLoad();
		try {
			HiveUtil.authentication();
			HiveUtil.initConnection();
			PrintStream ps = new PrintStream("hive_report.txt");
			dao d = new dao();
			SimpleDateFormat sdf = new SimpleDateFormat("mmss");
			Date date = new Date();
			String time = sdf.format(date);		
			String databasename = "test" + time;
			String hdfsurl = "/test" + time;
			boolean ctdatabase = HiveUtil.createDatabase(databasename, hdfsurl);
			if (ctdatabase) {
				i++;
				String message = i + "、创建hive库：" + databasename + " 成功";
				ps.println(message);
				System.out.println(message);
				d.save(i, message);
			} else {
				j++;
				ps.println(j + "、创建hive库：" + databasename + " 失败");

			}

			String tablename = "test" + time;
			boolean ctTable = HiveUtil.createTables(databasename, tablename);
			if (ctTable) {
				i++;
				String message = i + "、在" + databasename + " 库下创建：" + tablename + "表 成功";
				ps.println(message);
				System.out.println(message);
				d.save(i, message);
			} else {
				j++;
				ps.println(j + "、在" + databasename + " 库下创建：" + tablename + " 失败");
			}

			String hdfspath = "/mapred/hive.txt";
			String table = databasename + "." + tablename;
			boolean loaddata = HiveUtil.loadDataIntoTable(hdfspath, table);
			if (loaddata) {
				i++;
				String message = i + "、将" + hdfspath + " 数据加载到" + table + " 表中，成功";
				ps.println(message);
				System.out.println(message);
				d.save(i, message);
			} else {
				j++;
				ps.println(j + "、将" + hdfspath + " 数据加载到" + table + " 表中，失败");
			}

			HiveUtil hu=new HiveUtil();		
			if (hu.staticTables(table)!=null) {
				i++;
				String message = i + "、统计" + table + " 表中数据成功,表中数据量为："+hu.staticTables(table).get(0);
				ps.println(message);
				System.out.println(message);
				d.save(i, message);
			} else {
				j++;
				ps.println(j + "、统计" + table + " 表中数据失败");
			}

			if(hu.showDbs()!=null) {
				i++;
				List<String> dblist=hu.showDbs();
				String s="";
				for(int l=0;l<dblist.size();l++) {
					s +=" "+dblist.get(l)+" ";
				}
				String message = i + "、查询数据库成功，数据库有：" + s ;
				ps.println(message);
				System.out.println(message);
				d.save(i, message);
			}else {
				j++;
				ps.println(j + "、查询数据库失败" );
			}
			
			if(hu.showTables(databasename)!=null) {
				i++;
				List<String> tablelist=hu.showTables(databasename);
				String s="";
				for(int l=0;l<tablelist.size();l++) {
					s +=" "+tablelist.get(l)+" ";
				}
				String message = i + "、查询数据库："+databasename+"下表成功，表有：" + s ;
				ps.println(message);
				System.out.println(message);
				d.save(i, message);
			}else {
				j++;
				ps.println(j + "、查询数据库："+databasename+"下表失败" );
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(le.getTrace(e));
		}
		System.out.println("---------------------------------------------");
    	System.out.println("sucesss :  "+i +"       fail :  " + j);
	}
}
