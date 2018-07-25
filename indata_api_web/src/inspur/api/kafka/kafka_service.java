package inspur.api.kafka;

import java.util.List;

import inspur.jdbcutil;

public class kafka_service {
   public List<kafka_api> findall() {
	   kafka_dao d=new kafka_dao();
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
