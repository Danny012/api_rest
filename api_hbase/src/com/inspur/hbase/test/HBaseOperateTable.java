package com.inspur.hbase.test;


import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.NamespaceDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.io.compress.Compression;
import org.apache.hadoop.hbase.regionserver.BloomType;

import com.inspur.hbase.test.*;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 操作HBase数据库表示例
 * 
 *
 */
public class HBaseOperateTable {
	private static Log log = LogFactory.getLog(HBaseOperateNamespace.class);

	public static void main(String[] args) {
		//createTable("nsll:table3");
		// deleteTable("ns9:table3");
	}

	public static boolean createTable(String tableName) throws IOException {		
			Connection connect = HBaseInitConnection.returnConnection(); 
			Admin admin = connect.getAdmin();
			HColumnDescriptor family = new HColumnDescriptor("cf");
			family.setMaxVersions(1);
			family.setMinVersions(0);
			family.setInMemory(true);
			family.setBlockCacheEnabled(true);
			family.setBlocksize(65536);
			family.setTimeToLive(2147483647);
			family.setBloomFilterType(BloomType.valueOf("ROW"));
			family.setCompressionType(Compression.Algorithm.SNAPPY);
			HTableDescriptor hTableDescriptor = new HTableDescriptor(TableName.valueOf(tableName));
			hTableDescriptor.addFamily(family);
			admin.createTable(hTableDescriptor);
			//System.out.println(tableName+"创建成功");
			return true;
	
	}

	public static boolean deleteTable(String tableName) {
		try (Connection connect = HBaseInitConnection.returnConnection(); Admin admin = connect.getAdmin();) {
			admin.disableTable(TableName.valueOf(tableName));
			admin.deleteTable(TableName.valueOf(tableName));
			//System.out.println(tableName+"删除成功");
			return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			log.error("在HBase中删除名称为" + tableName + "的表出错:" + e.getMessage());
			return false;
		}
	}
}
