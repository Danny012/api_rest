package com.inspur.hbase.test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.NamespaceDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.log4j.Logger;

import com.inspur.hbase.util.dao;
import com.inspur.hbase.util.logException;



public class test_Api {
	public static Logger logger= Logger.getLogger(test_Api.class);
	static logException le=new logException();
    public	static PrintStream ps;
	public static void main(String[] args)   {
		dao d=new dao();
		int i=0,j=0;
		try{
			 SimpleDateFormat df =new SimpleDateFormat("mmss");
		      Date date=new Date();
		      String time=df.format(date);
			ps=new PrintStream("hbase_report.txt");
			HBaseInitConnection hbc=new HBaseInitConnection();
			Configuration conf= hbc.authentication();
			Connection connection = hbc.getConnection(conf);	
			System.out.println(connection);
			if(connection!=null){
				i++;
				String message=i+"、数据库连接成功，连接为："+connection +"   /"+time;
				ps.println(message);
				System.out.println(message);
				d.save(i,message);
			}else{
				j++;
			}
//			
			String namespace="test"+time;
			//HBaseOperateNamespace hons=new HBaseOperateNamespace();
			boolean cn=HBaseOperateNamespace.createNamespace(namespace);
			if(cn){
				i++;
				String message=i+"、Hbase命名空间："+namespace+"创建成功";
				ps.println(message);
				System.out.println(message);
				d.save(i, message);
			}else{
				j++;
			}
			boolean ln=HBaseOperateNamespace.listNamespace();
			Connection connect = HBaseInitConnection.returnConnection();
			Admin admin = connect.getAdmin();
			NamespaceDescriptor[] nss = admin.listNamespaceDescriptors();
			if(ln){
				i++;
				ps.println(i+"、当前数据库中的命名空间有：");
				String hs="";
				for(int k=0;k<nss.length;k++){
					ps.println(nss[k].getName());
			     hs +=nss[k].getName()+"、";
				}
				String message=i+"、当前数据库中的命名空间有："+hs;
				System.out.println(message);
				d.save(i, message);
			}
			
			String tableName="hbase_table"+time;
			//HBaseOperateTable hot=new HBaseOperateTable();
			String hbase_table=namespace+":"+tableName;
			boolean ct=HBaseOperateTable.createTable(hbase_table);
			if(ct){
				i++;
				String message=i+"、Hbase数据表："+tableName+"创建成功";
				ps.println(message);
				System.out.println(message);
				d.save(i, message);
			}else{
				j++;
			}
			
			
			//HBaseOperateTableData hotd=new HBaseOperateTableData();
			boolean pd=HBaseOperateTableData.putData1(hbase_table);
			if(pd){
				i++;
				String message=i+"、向Hbase数据表："+tableName+"插入数据"+HBaseOperateTableData.testdata+"成功";
				ps.println(message);
				System.out.println(message);
				d.save(i, message);
			}else{
				j++;
			}
			
			
			boolean scant=HBaseOperateTableData.scanData(hbase_table);
			if(scant){
				i++;				
				String message=i+"、Hbase数据表："+tableName+"下的数据为"+HBaseOperateTableData.value;
				ps.println(message);
				System.out.println(message);
				d.save(i, message);
			}else{
				j++;
			}
			
			boolean dtd=HBaseOperateTableData.deleteData(hbase_table);
			if(dtd){
				i++;
				String message=i+"、命名空间 "+hbase_table+" 下数据"+HBaseOperateTableData.value+" 删除成功";
				ps.println(message);
				System.out.println(message);
				d.save(i, message);
			}else{
				j++;
			}
			
			
			boolean dt=HBaseOperateTable.deleteTable(hbase_table);
			if(dt){
				i++;
				String message=i+"、"+namespace+" 命名空间下表： "+tableName+" 删除成功";
				ps.println(message);
				System.out.println(message);
				d.save(i, message);
			}else{
				j++;
			}			
			
		
			
			boolean dn=HBaseOperateNamespace.deleteNamespace(namespace);
			if(dn){
				i++;
				String message=i+"、Hbase命名空间："+namespace+"删除成功";
				ps.println(message);
				System.out.println(message);
				d.save(i, message);
			}else{
				j++;
			}
			
			
		}
		catch (Exception e) {
			logger.error(le.getTrace(e));
		}     
		
		System.out.println("---------------------------------------------");
    	System.out.println("sucesss :  "+i +"       fail :  " + j);
	}

}
