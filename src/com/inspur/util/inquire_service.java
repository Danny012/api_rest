package com.inspur.util;


import com.inspur.restApi.apiTest;
import com.inspur.restApi.restApi;

import net.sf.json.JSONObject;

public class inquire_service {
    public static void main(String[] args) throws Exception {
		 getInstance();
	}
	public static boolean getInstance() throws Exception {
					
		 restApi ra=new restApi();
    	 String url="http://10.110.13.124:9000/manage-cluster/service/indata/component/catalog";
    	 JSONObject param = new JSONObject();
    	 access_token    token=new access_token();     
    	 //delete d=new delete();
    	 param.put("token", token.spliteString());
    	 param.put("instanceld",delete.id());
    	 apiTest rp=new apiTest();
	     String sumString =  rp.doPost(url, param.toString());
	     ra.ps.println(sumString);
	    //System.out.println(sumString);
    	 return true;
		
	}
}
