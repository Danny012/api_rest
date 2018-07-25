package inspur.api.hive;

import java.util.List;

import inspur.jdbcutil;

public class hive_service {
   public List<hive_api> findall() {
	   hive_dao d=new hive_dao();
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
