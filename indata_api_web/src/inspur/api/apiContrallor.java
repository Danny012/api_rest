package inspur.api;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import inspur.api.hbase.hbase_api;
import inspur.api.hbase.hbase_service;
import inspur.api.hdfs.hdfs_api;
import inspur.api.hdfs.hdfs_service;
import inspur.api.hive.hive_api;
import inspur.api.hive.hive_service;
import inspur.api.kafka.kafka_api;
import inspur.api.kafka.kafka_service;

/**
 * Servlet implementation class apiContrallor
 */
@WebServlet("/index")
public class apiContrallor extends HttpServlet {
	private static final long serialVersionUID = 1L;       
 
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		hbase_service s=new hbase_service();
		List<hbase_api> hbase_apilist = null;
		try {
			hbase_apilist = s.findall();
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("hbase_apilist", hbase_apilist);
		//System.out.println(hbase_apilist.get(0).result);
		
		hdfs_service hdfs=new hdfs_service();
		List<hdfs_api> hdfs_apilist = null;
		try {
			hdfs_apilist = hdfs.findall();
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("hdfs_apilist", hdfs_apilist);
		
		hive_service hive=new hive_service();
		List<hive_api> hive_apilist = null;
		try {
			hive_apilist = hive.findall();
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("hive_apilist", hive_apilist);
		
		kafka_service kafka=new kafka_service();
		List<kafka_api> kafka_apilist = null;
		try {
			kafka_apilist = kafka.findall();
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("kafka_apilist", kafka_apilist);
		
		request.getRequestDispatcher("/index.jsp").forward(request, response);
	}

}
