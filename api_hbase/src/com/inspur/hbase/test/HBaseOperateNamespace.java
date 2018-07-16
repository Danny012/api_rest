package com.inspur.hbase.test;


import org.apache.hadoop.hbase.NamespaceDescriptor;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;

import com.inspur.hbase.test.*;

import java.io.PrintStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 操作HBase数据库命名空间示例
 * 
 *
 */
public class HBaseOperateNamespace {
	private static Log log = LogFactory.getLog(HBaseOperateNamespace.class);

	public static void main(String[] args) {
		createNamespace("nsll");
		//listNamespace();
		//deleteNamespace("ns1");

	}

	public static boolean createNamespace(String namespace) {

		try (Connection connect = HBaseInitConnection.returnConnection(); 
				Admin admin = connect.getAdmin();) {
			NamespaceDescriptor ns = NamespaceDescriptor.create(namespace).build();
			admin.createNamespace(ns);
			return true;
		} catch (Exception e) {
			log.error("在HBase中创建名称为" + namespace + "的命名空间出问题:" + e.getMessage());
		    return  false;
		}
	}
	
	public static boolean listNamespace() {
		try (Connection connect = HBaseInitConnection.returnConnection(); Admin admin = connect.getAdmin();) {
			NamespaceDescriptor[] nss = admin.listNamespaceDescriptors();
			/**for(int i=0;i<nss.length;i++){
				test_Api.ps.println(nss[i].getName());
				//System.out.println(nss[i].getName());
			} **/
			return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
	}
	

	public static boolean deleteNamespace(String namespace) {
		try (Connection connect = HBaseInitConnection.returnConnection(); Admin admin = connect.getAdmin();) {
			admin.deleteNamespace(namespace);
			//System.out.println("删除命名空间成功:"+namespace);
			return true;
		} catch (Exception e) {
			//System.out.println(e.getMessage());
			log.error("在HBase中删除名称为" + namespace + "的命名空间出�?:" + e.getMessage());
			return false;
		}
	}
}
