package inspur.api.hdfs;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mysql.jdbc.Statement;

import inspur.jdbcutil;

public class hdfs_dao {
   public List<hdfs_api> getdao() throws  Exception {
	   List<hdfs_api> apiList=new ArrayList<hdfs_api>();
	   jdbcutil  ju=new jdbcutil();	   
	   String sql ="select * from hdfs_api order by id asc";
	   Statement stmt = (Statement)ju.getconnection().createStatement();
	   ResultSet rs = stmt.executeQuery(sql);		                                                          
			while (rs.next()) {
				hdfs_api  d=new hdfs_api();
			       d.setId(rs.getInt("id"));
			       d.setResult( rs.getString("result"));				       
			       d.setDate(rs.getTimestamp("date"));			      
			       apiList.add(d);
			  }
	   return apiList;
   }
}
