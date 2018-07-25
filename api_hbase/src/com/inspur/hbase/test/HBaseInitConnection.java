package com.inspur.hbase.test;
import java.io.File;
import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.security.UserGroupInformation;
import org.apache.log4j.Logger;

import com.inspur.hbase.util.logException;

/**
 * 连接HBase数据库示�?
 *
 */
public class HBaseInitConnection {
	public static Logger logger= Logger.getLogger(HBaseInitConnection.class);
	static logException le=new logException();
	private static Connection conn;
//	private static String HIVE_JDBC_URL = "jdbc:hive2://indata-10-110-13-164.indata.com:10000/default;principal=hive/indata-10-110-13-164.indata.com@INDATA.COM";
//	private static String principal = "hbase/indata-10-110-13-164.indata.com@INDATA.COM";
//	private static String keytab = "resource/hive.service.keytab";
//	private static String krb5 = "resource/krb5.conf";
	static Configuration conf = new Configuration();
	/**
	 * 参数可从集群的hbase-site.xml中获�?
	 * 
	 * @return
	 */
	public static Configuration initConfig() {
		//Configuration conf = HBaseConfiguration.create();
		conf.set("hbase.zookeeper.quorum",
				"indata-10-110-13-165.indata.com:2181,indata-10-110-13-164.indata.com:2181,indata-10-110-13-163.indata.com:2181");
		//System.out.println("hbase.zookeeper.quorum");
		conf.set("zookeeper.znode.parent", "/hbase-secure");
		conf.set("hbase.zookeeper.property.clientPort", "2181");
		conf.set("hadoop.security.authentication", "kerberos");
		conf.set("hadoop.security.authorization", "true");
		conf.set("hbase.security.authentication", "kerberos");
		conf.set("hbase.security.authorization", "true");
		// HMaster地址
		conf.set("hbase.master.kerberos.principal", "hbase/_HOST@INDATA.COM");
		conf.set("hbase.regionserver.kerberos.principal", "hbase/_HOST@INDATA.COM");
		// 自定义超时时间，方便调试，可不设�?
//		conf.setInt("hbase.rpc.timeout", 20000);
//		conf.setInt("hbase.client.operation.timeout", 30000);
//		conf.setInt("hbase.client.scanner.timeout.period", 20000);
		return conf;
	}

	/**
	 * kerberos认证
	 * 
	 * krb5.conf和hbase.service.keytab这两个文件是从hbase的master�??在机器获取的，放到类路径�??
	 * principal中的idap-agent-105.inspur.com，是hbase.service.keytab�??在机器的hostname,
	 * 
	 * @param conf
	 * @return 
	 * @throws IOException
	 */
	public static Configuration authentication() throws IOException {
		//if ("kerberos".equalsIgnoreCase(conf.get("hbase.security.authentication"))) {			
			//File f1=new File("/opt/shell/auto-test/api/resource/krb5.conf");
			//String krbStr = f1.getAbsolutePath();
			//String krbStr = "/opt/shell/auto-test/api/resource/krb5.conf";
		Configuration con= initConfig();
			String krbStr = "resource/krb5.conf";
			//System.out.println(krbStr);
			System.setProperty("java.security.krb5.conf", krbStr);
			String principal = "hbase/indata-10-110-13-164.indata.com@INDATA.COM";
			//File f2=new File("/opt/shell/auto-test/api/resource/hbase.service.keytab");
			//String keytabFilePath = f2.getAbsolutePath();
			//String keytabFilePath = "/opt/shell/auto-test/api/resource/hbase.service.keytab";
			String keytabFilePath = "resource/hbase.service.keytab";
			//System.out.println(keytabFilePath);
			UserGroupInformation.setConfiguration(conf);
			UserGroupInformation.loginUserFromKeytab(principal, keytabFilePath);
			//System.out.println("authentication  is  ok");
	//	}
			return con;
	}

		
	 
	public static Connection getConnection(Configuration conf) throws IOException {
		Connection connection = ConnectionFactory.createConnection(conf);
		return connection;
	}

	public static Connection returnConnection() {
		Configuration conf = initConfig();
		Connection connection = null;
		try {
			//HBaseInitConnection hc=new HBaseInitConnection();
			authentication();
			//System.out.println("returnconnection  is  ok");
			connection = getConnection(conf);
			return connection;
		} catch (Throwable e) {
			System.out.println(e.getMessage());
			String fullStackTrace = org.apache.commons.lang.exception.ExceptionUtils.getFullStackTrace(e);
			System.out.println("打印异常:" + fullStackTrace);
			return null;
		}
		
	}
}
