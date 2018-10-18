package com.inspur.util;

import java.security.AccessControlContext;
import java.util.HashMap;
import java.util.Map;

import com.inspur.restApi.apiTest;
import com.inspur.restApi.restApi;

import net.sf.json.JSONObject;

public class cluster_standard {
	public static void main(String[] args) throws Exception {
		   accseeCluster();
	}
     public static boolean accseeCluster() throws Exception {
    	 restApi ra=new restApi();
    	 String url="http://10.110.13.124:9000/manage-cluster/service/indata/component/catalog";
    	 JSONObject map = new JSONObject();
    	 access_token    token=new access_token();         			 
    	 map.put("token", token.spliteString());
    	 apiTest rp=new apiTest();
	     String sumString =  rp.doPost(url, map.toString());
	     ra.ps.println(sumString);
	    //System.out.println(sumString);
    	 return true;
     }
}
