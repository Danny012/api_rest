package com.inspur.util;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import com.inspur.restApi.apiTest;
import com.inspur.restApi.restApi;

import net.sf.json.JSONObject;

public class delete {
public static void main(String[] args) throws Exception {
	deleteService(id());
}
public static boolean deleteService(String id) throws Exception {
	/*
	 *��ȡID 
	 */	
	 restApi ra=new restApi();
	 String url="http://10.110.13.163:9000/manage-cluster/service/indata/component/delete/instance";	                           
	 JSONObject map = new JSONObject();
	 map.put("instanceId", id);	
	 access_token    token=new access_token();         			 
	 map.put("token", token.spliteString());
	 map.put("realm", "realm1234");
	 map.put("masterUser", "tenant1234-master");
	 apiTest rp=new apiTest();
	 String sumString =  rp.doPost(url, map.toString());
	 ra.ps.println(sumString);
	// System.out.println(sumString);
	 return true;
 }
public static String id() throws Exception{	
	    File file = new File("Id.txt");
	    if(file.isFile() && file.exists()) {
	      InputStreamReader isr = new InputStreamReader(new FileInputStream(file), "utf-8");
	      BufferedReader br = new BufferedReader(isr);	      
	        String lineTxt = br.readLine();
//	        System.out.println(lineTxt);	      
	      br.close();
	      return lineTxt;
	    } else {
	      //System.out.println("");
	      return null;
	    }
	  
}
}
