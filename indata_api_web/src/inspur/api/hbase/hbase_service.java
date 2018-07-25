package inspur.api.hbase;

import java.util.List;

import inspur.jdbcutil;

public class hbase_service {
   public List<hbase_api> findall() {
	   hbase_dao d=new hbase_dao();
	   try {
		return d.getdao();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return null;
	}finally{
		jdbcutil.closeConnection();
	}
   }
}
