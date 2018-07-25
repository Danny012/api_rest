package inspur.api.kafka;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mysql.jdbc.Statement;

import inspur.jdbcutil;

public class kafka_dao {
   public List<kafka_api> getdao() throws  Exception {
	   List<kafka_api> apiList=new ArrayList<kafka_api>();
	   jdbcutil  ju=new jdbcutil();	   
	   String sql ="select * from kafka_api order by id asc";
	   Statement stmt = (Statement)ju.getconnection().createStatement();
	   ResultSet rs = stmt.executeQuery(sql);		                                                          
			while (rs.next()) {
				kafka_api  d=new kafka_api();
			       d.setId(rs.getInt("id"));
			       d.setResult( rs.getString("result"));				       
			       d.setDate(rs.getTimestamp("date"));			      
			       apiList.add(d);
			  }
	   return apiList;
   }
}
