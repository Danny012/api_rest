package inspur.api.hdfs;

import java.util.List;

import inspur.jdbcutil;

public class hdfs_service {
   public List<hdfs_api> findall() {
	   hdfs_dao d=new hdfs_dao();
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
