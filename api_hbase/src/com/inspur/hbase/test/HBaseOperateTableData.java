package com.inspur.hbase.test;

import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
/*import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;*/
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.HTable;
/*import org.apache.hadoop.hbase.client.Delete;*/
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
/*import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;*/
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.protobuf.ProtobufUtil;
import org.apache.hadoop.hbase.quotas.QuotaScope;
import org.apache.hadoop.hbase.quotas.QuotaSettingsFactory;
import org.apache.hadoop.hbase.quotas.QuotaUtil;
import org.apache.hadoop.hbase.quotas.ThrottleType;
import org.apache.hadoop.hbase.util.Bytes;

import com.inspur.hbase.test.*;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 操作HBase数据库表的数据示�?
 * 
 *
 */
public class HBaseOperateTableData {

	public static void main(String[] args) throws IOException {
		
		//setquota();
		//putData1("nsll:table3");
		//scanData("test0719:table");
		//scanData("niu:aa");
		 deleteData("test0719:table");
	}

      static String testdata="  API自动化测试数据  ";
	public static boolean putData1(String tableName) throws IOException {
		
		Connection connect = HBaseInitConnection.returnConnection();
		
		Table table = connect.getTable(TableName.valueOf(tableName));
		
	
			List<Put> puts = new ArrayList<Put>();
			
				Put put = new Put(Bytes.toBytes("id"));
				put.addColumn(Bytes.toBytes("cf"), Bytes.toBytes("name"), Bytes.toBytes(testdata));
				puts.add(put);
		
			table.put(puts);
		
		

		table.close();
		return true;
	
	}
	static String value="";
	public static boolean scanData(String tableName) {
		try (Connection connect = HBaseInitConnection.returnConnection();
				Admin admin = connect.getAdmin();
				Table table = connect.getTable(TableName.valueOf(tableName));) {
			Scan s = new Scan();
			//s.setCaching(10000);
			ResultScanner rs = table.getScanner(s);		
		
		for (Result r : rs) {
					for (Cell cell : r.rawCells()) {
//						System.out.println("Rowkey : " + Bytes.toString(r.getRow()) + "   Familiy:Quilifier : "
//								+ Bytes.toString(CellUtil.cloneQualifier(cell)) + "   Value : "
//								+ Bytes.toString(CellUtil.cloneValue(cell)));
						value=Bytes.toString(CellUtil.cloneValue(cell));
					}
				}		

			//System.out.println("done");
			return true;
		}catch (org.apache.hadoop.hbase.quotas.QuotaExceededException e) {
			System.out.println("在HBase中uploadToRow出错:" + e.getMessage());
			return false;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
		
	}

	public  static boolean deleteData(String tableName) {
		try (Connection connect = HBaseInitConnection.returnConnection();
				Admin admin = connect.getAdmin();
				Table table = connect.getTable(TableName.valueOf(tableName));) {
			Delete delete = new Delete(Bytes.toBytes("id"));
			table.delete(delete);
			//table.delete(delete);
			//System.out .println("删除成功");
			return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
	}
	 
	
	
	
	
	
}
