package com.inspur.hive.test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.security.UserGroupInformation;

public class HiveUtil {
	private static Connection conn;
	private static String HIVE_JDBC_URL = "jdbc:hive2://indata-10-110-13-164.indata.com:10000/default;principal=hive/indata-10-110-13-164.indata.com@INDATA.COM";
	private static String principal = "hive/indata-10-110-13-163.indata.com@INDATA.COM";
	private static String keytab = "resource/hive.service.keytab";
	private static String krb5 = "resource/krb5.conf";
	static Configuration conf = new Configuration();

	public static void main(String[] args) throws Exception {
		confLoad();
		try {
			authentication();
			initConnection();
			// createDatabase("asd", "/hdfsLocation");
		   //showDbs();
			// showTables();
			// createTables();
			if( showDbs()!=null) {
				List<String> dblist=showDbs();
				String s="";
				for(int l=0;l<dblist.size();l++) {
					s  +=" "+dblist.get(l)+" ";
				}
				System.out.println(s);
			}else{
				System.out.println("no");
			};
			// loadDataIntoTable();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	 static void initConnection() {
		try {
			conn = DriverManager.getConnection(HIVE_JDBC_URL);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	static void confLoad() {
		conf.set("hadoop.security.authentication", "kerberos");
		conf.set("hadoop.security.authorization", "true");
	}

	static void authentication() throws IOException {
		// 初始化配置文件
		System.setProperty("java.security.krb5.conf", krb5);// krb5文件路径

		UserGroupInformation.setConfiguration(conf);
		UserGroupInformation.loginUserFromKeytab(principal, keytab);// 入参：票据、keytab文件
	}

	public static boolean createDatabase(String databaseName, String hdfsLocation) throws Exception {

		if (StringUtils.isBlank(databaseName) || StringUtils.isBlank(hdfsLocation)) {
			return false;
		}
		StringBuilder stringBuild = new StringBuilder();
		stringBuild.append("create database if not exists ");
		stringBuild.append(databaseName);
		stringBuild.append(" location ");
		stringBuild.append("'" + hdfsLocation).append("'");
		String createSql = stringBuild.toString();
		Statement stmt = conn.createStatement();
		stmt.execute(createSql);
		//System.out.println("success");
		return true;

	}

	/**
	 * 创建数据表
	 */
	public static boolean createTables(String database, String tablename) {
		String createSql = "create table if not exists " + database + "." + tablename
				+ "(name string,age string) row format delimited fields terminated by ',' stored as textfile";
		try {
			Statement stmt = conn.createStatement();
			stmt.execute(createSql);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("创建数据表失败");
			return false;
		}
		// System.out.println("创建数据表成功");
	}

	public static boolean loadDataIntoTable( String hdfsurl,String hivetable) {
		// 路径为HDFS或HiveServer2所在host的文件目录下的路径
		String sql = "load data  inpath '"+hdfsurl+"'   into table " + hivetable;
		System.out.println(sql);
		try {
			Statement stmt = conn.createStatement();
			stmt.execute(sql);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

	}

	/**
	 * 统计表信息
	 */
	public static List<String> staticTables(String table) {
		String sqlcount = "select count(0) from " + table;			
				try {
					
					Statement stmt = conn.createStatement();
					ResultSet rs = stmt.executeQuery(sqlcount);
					// int count=0;
					List<String> list = new ArrayList<String>();
					while (rs.next()) {
						//System.out.println(rs.getString(1));
						list.add(rs.getString(1));
					}				
						return list;
				}
				catch (SQLException e) {
					e.printStackTrace();
				 return null;
				}		
		}
	
	
	/**
	 * 查询所有数据库
	 */
	public static List<String> showDbs() {
		Statement stmt = null;
		ResultSet rs = null;
		try {
			List<String> database=new ArrayList<String>();
			stmt = conn.createStatement();
			rs = stmt.executeQuery("show databases");
			while (rs.next()) {
				//System.out.println(rs.getString(1));				
				database.add(rs.getString("database_name"));
			}
			return database;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			close(stmt);
			close(rs);
		}
	}

	public static List<String> showTables(String database) {
		Statement stmt = null;
		ResultSet rs = null;

		try {
			stmt = conn.createStatement();

			boolean flag = stmt.execute("use "+database);
			ResultSet tbrs = null;
			tbrs = stmt.executeQuery("show tables");
			List<String> table=new ArrayList<String>();
			while (tbrs.next()) {
				//System.out.println(tbrs.getString(1));
				table.add(tbrs.getString("tab_name"));
			}
			return table;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			close(stmt);
			close(rs);
		}
	}

	/**
	 * 关闭Statement
	 * 
	 * @param stmt
	 */
	private static void close(Statement stmt) {
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 关闭ResultSet
	 * 
	 * @param rs
	 */
	private static void close(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
