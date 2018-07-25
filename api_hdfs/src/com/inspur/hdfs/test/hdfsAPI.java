package com.inspur.hdfs.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.inspur.hdfs.util.dao;
import com.inspur.hdfs.util.logException;

public class hdfsAPI {
	public static Logger logger= Logger.getLogger(hdfsAPI.class);
	static logException le=new logException();
	public static void main(String[] args) {
		 SimpleDateFormat sdf =new SimpleDateFormat("mmss");
	     Date date=new Date();
	     String time=sdf.format(date);
		int i=0,j=0;
		try {	
			PrintStream ps = new PrintStream("hdfs_report.txt");			
	        dao d=new dao();
	    	List resList = new ArrayList();							
			resList=HdfsUtil.listFileStatuses("/tmp");
			if(resList != null){
				i++;
				String message=i+"、获取tmp路径下文件和目录信息成功："+resList;
				ps.println(message);
				System.out.println(message);
				d.save(i, message);
			}else {
				ps.println(i+"、获取user路径下文件和目录信息失败："+resList+ "/ 失败");
				j++;				
			}
//		} catch (Exception e) {
//			logger.error(le.getTrace(e));
//		} 		
			String dir="/test"+time;
			boolean md=HdfsUtil.makeDir("/cuiaq");//是否创建目录成功
			if(md) {
				i++;
				String message=i+"、创建文件目录成功："+dir+" 创建成功";
				ps.println(message);
				System.out.println(message);
				d.save(i, message);
				}else {
					j++;
					ps.println(i+"、创建文件目录失败："+dir+"  创建失败");
				}	
				String dirRight="/test"+time;
			boolean mdar=HdfsUtil.makeDirAllRight(dirRight);//是否创建目录并且赋权成功
				if(mdar) {
					i++;
					String message=i+"、创建文件目录成功："+dirRight+ " 创建成功赋权成功";
					ps.println(message);
					System.out.println(message);
					d.save(i, message);
					}else {
						j++;
						ps.println(i+"、创建文件目录失败："+dirRight+"  创建成功赋权失败");
					}	

			String str = null;
            str=HdfsUtil.obtainPermission(dir);//获取文件或目录的权限信息
            if(str != null){
				i++;
				String message=i+"、获取"+dir+"的权限信息成功："+str+ "/ 成功";			
				ps.println(message);
				System.out.println(message);
				d.save(i, message);
			}else {
				j++;
				ps.println(i+"、获取文件或目录的权限信息失败："+str+ "/ 失败");
			}
		
			String file=dir+"/test.txt";
			boolean cf=HdfsUtil.createFile(file);//创建文件
			if(cf) {
				i++;
				String message=i+"、创建文件成功："+file+"  创建成功赋权成功";			
				ps.println(message);
				System.out.println(message);
				d.save(i, message);
				}else {
					j++;
					ps.println(i+"、创建文件失败："+file+" 失败");
				}
			HdfsUtil.deleteFile2(file);
			
				
			boolean id=HdfsUtil.isDirectory(dir);//判断是否为目录
			 if(id) {
					i++;
					String message=i+"、判断是否为目录成功，"+dir+"  是目录";					
					ps.println(message);
					System.out.println(message);
					d.save(i, message);
					}else {
						j++;
					}	
					
		String []strs = new String[]{dir};
		//String[] strs = new String[]{"/cuiaq", "/cuiaq1"};		
		boolean cb=HdfsUtil.canBeDeleted(strs);//判断目录或文件能否被删除
		 if(cb) {
				i++;
				String message=i+"、 判断目录或文件能被删除成功 "+dir;				
				ps.println(message);
				System.out.println(message);
				d.save(i, message);
				}else {
					j++;
				ps.println(i+"、 判断目录或文件能被删除失败 "+dir);	
				}
		 
		 String localpath="resource/hive.txt";	
		 String file1=dir+"/hdfs.txt";
		 HdfsUtil.uploadFile(localpath, "/mapred/hive.txt");
		 boolean uf=HdfsUtil.uploadFile(localpath,file1);//上传文件
		 if(uf) {
				i++;				
				String message=i+"、 上传文件成功 "+file1+"成功";			
				ps.println(message);
				System.out.println(message);
				d.save(i, message);
				// HdfsUtil.uploadFile(fin, "/user/hdfs.txt");
				}else {
					j++;
				ps.println(i+"、上传文件失败 resource/hdfs.txt 失败 ");	
				}
		
		// HdfsUtil.deleteFile2("/cuiaq/krb5.conf");
		// String file1=dir+"test4.txt";
		// HdfsUtil.createFile(file1);
		        Map res = new HashMap();						
				res=HdfsUtil.readFile(file1);//读取文件
				if(res != null){
					i++;
					String message=i+"、读取"+file1+"文件信息成功："+res;					
					ps.println(message);
					System.out.println(message);
					d.save(i, message);
				}else {
					ps.println(i+"、读取文件信息失败："+res+ "/ 失败");
					j++;				
				}
				
				 
				boolean df=HdfsUtil.deleteFile2(file1);//删除文件
				if(df){
					i++;
					String message=i+"、删除文件成功："+file1;					
					ps.println(message);
					System.out.println(message);
					d.save(i, message);
				}else {
					ps.println(i+"、删除文件失败："+file1);
					j++;				
				}	
				String file2=dir+"/people.txt";
				HdfsUtil.createFile(file2);
				boolean ep=HdfsUtil.existPath(file2);//判断文件或目录路径是否存在
				if(ep){
					i++;
					String message=i+"、文件已存在："+file2;				
					ps.println(message);
					System.out.println(message);
					d.save(i, message);
				}else {
					ps.println(i+"、文件不存在："+file2);
					j++;				
				}
				String file3=dir+"/people1.txt";
				boolean rn=HdfsUtil.rename(file2,file3);//重命名文件
				if(rn){
					i++;
					String message=i+"、文件重命名成功："+file3;				
					ps.println(message);
					System.out.println(message);
					d.save(i, message);
				}else {
					ps.println(i+"、文件重命名失败："+file3);
					j++;				
				}
				HdfsUtil.deleteFile2(file2);
	
				boolean cdr=HdfsUtil.changeDirAllRight(dir,7,7,7);//修改目录权限
				if(cdr){
					i++;
					String message=i+"、修改目录权限成功：" +dir;			
					ps.println(message);
					System.out.println(message);
					d.save(i, message);
				}else {
					ps.println(i+"、修改目录权限失败："+dir);
					j++;				
				}
				String file4=dir+"/bigsql.txt";
				HdfsUtil.createFile(file4);
				boolean cfr=HdfsUtil.changeFileAllRight(file4,7,7,7);//修改文件权限
				if(cfr){
					i++;
					String message=i+"、修改文件权限成功："+file4;					
					ps.println(message);
					System.out.println(message);
					d.save(i, message);
				}else {
					ps.println(i+"、修改文件权限失败："+file4);
					j++;				
				}
				
			} catch (Exception e) {
			logger.error(le.getTrace(e));
			}

		System.out.println("---------------------------------------------");
    	System.out.println("sucesss :  "+i +"       fail :  " + j);
	}
}
		
