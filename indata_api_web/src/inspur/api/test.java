package inspur.api;

import inspur.api.hbase.hbase_service;

public class test {
public static void main (String[] args) throws Exception {
//	dao  s=new dao();
//	//System.out .println(s.getdao().date);
//	apiContrallor ac=new apiContrallor();
//	ac.doPost(null, null);
hbase_service s=new hbase_service();
System.out.println(s.findall().get(2).date);
}
}
