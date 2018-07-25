package com.inspur.kafka.util;

import com.mysql.jdbc.PreparedStatement;

public class dao {
   public void save(int id,String result) throws  Exception {	   
	   jdbcutil  ju=new jdbcutil();	
	   String sql1="UPDATE `kafka_api` SET `result`= ?  WHERE (`id`= ?) LIMIT 1 ;";
	  // String sql="INSERT INTO `hbase_api` (`id`, `result`, `date`) VALUES (?, ?, '2018-10-11');";
	   //String sql ="select * from web_api";
	   PreparedStatement stmt = (PreparedStatement)ju.getconnection().prepareStatement(sql1);
	   stmt.setString(1, result);
	   stmt.setInt(2, id);
	   stmt.executeUpdate();   	
   }
}
