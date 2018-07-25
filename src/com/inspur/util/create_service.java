package com.inspur.util;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

import com.inspur.restApi.apiTest;
import com.inspur.restApi.restApi;

import net.sf.json.JSONObject;

public class create_service {
	static restApi ra=new restApi();
	static String sumString=null;
  public static void main(String[] args) throws Exception {
	  createService();
	  //spliteService();
}
  public static String createService() throws Exception {
	 String url="http://10.110.13.163:9000/manage-cluster/service/indata/component/instance";	                           
	 // System.out.println(url);
	 JSONObject map = new JSONObject();
 	 map.put("planId", "component-plan-1");
 	 access_token    token=new access_token();         			 
 	 map.put("token", token.spliteString());
 	 map.put("userId", "api_test");
 	 map.put("realm", "realm1234");
 	 map.put("masterUser", "tenant1234-master");
 	 apiTest rp=new apiTest();
 	 sumString =  rp.doPost(url, map.toString());
 	String[] strs=sumString.split("\"");
 	String subString=null;
 	 for(int i=0,len=strs.length;i<len;i++){
	    	subString=strs[21].toString();	          
	    }
 	  ra.ps.println(sumString);
	// System.out.println(subString);
 	 return subString;
  }
  public static String  spliteService() throws FileNotFoundException {
	  
	  String[] strs=sumString.split("\"");
	    String subString = null;
	    for(int i=0,len=strs.length;i<len;i++){
	    	subString=strs[53].toString();	    
	    }
	    PrintStream ps = new PrintStream("Id.txt");
	    ps.println(subString);
	    ps.close();
	    ra.ps.println(subString);
	    //System.out.println(subString);
	 	 return subString;
	  }
}
