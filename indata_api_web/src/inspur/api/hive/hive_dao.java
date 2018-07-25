package inspur.api.hive;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mysql.jdbc.Statement;

import inspur.jdbcutil;

public class hive_dao {
   public List<hive_api> getdao() throws  Exception {
	   List<hive_api> apiList=new ArrayList<hive_api>();
	   jdbcutil  ju=new jdbcutil();	   
	   String sql ="select * from hive_api order by id asc";
	   Statement stmt = (Statement)ju.getconnection().createStatement();
	   ResultSet rs = stmt.executeQuery(sql);		                                                          
			while (rs.next()) {
				hive_api  d=new hive_api();
			       d.setId(rs.getInt("id"));
			       d.setResult( rs.getString("result"));				       
			       d.setDate(rs.getTimestamp("date"));			      
			       apiList.add(d);
			  }
	   return apiList;
   }
}
