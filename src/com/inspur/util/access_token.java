package com.inspur.util;

import java.util.HashMap;
import java.util.Map;

import com.inspur.restApi.apiTest;
import com.inspur.restApi.restApi;

public class access_token {
	
	public static void main(String[] args) {
		System.out.println(spliteString());
	}
     public static String   spliteString() {
    	 restApi ra=new restApi();
    	 String url="http://10.110.13.163:19080/auth/realms/master/protocol/openid-connect/token";
    	 Map  map=new HashMap();
    	 map.put("grant_type", "password");
    	 map.put("scope", "openid profile");
    	 map.put("username", "superadmin");
    	 map.put("password", "superadmin");
    	 map.put("client_id", "indata-manage-portal");
    	 apiTest rp=new apiTest();
	     String sumString =  rp.doPost(url, map);
	    String[] strs=sumString.split("\"");
	    String subString = null;
	    for(int i=0,len=strs.length;i<len;i++){
	    	subString=strs[3].toString();	          
	    }
	    ra.ps.println(subString);
	  // System.out.println(subString);	
    	 return  subString;
     }
}
