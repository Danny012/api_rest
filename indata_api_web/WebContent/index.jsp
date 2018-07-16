<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="GB18030"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GB18030">
<style type="text/css">
#header {
    background-color:black;
    color:white;
    text-align:center;
    padding:5px;
}
#nav {
    line-height:30px;
    background-color:#eeeeee;
    height:300px;
    width:100px;
    float:left;
    padding:5px;	      
}
#table{
       margin:10px 50px;   
     
}
#section {
    width:350px;
    float:left;
    padding:10px;	
    table-align:center; 	 
}
#footer {
    background-color:black;
    color:white;
    clear:both;
    text-align:center;
   padding:5px;	 	 
}
td{
    text-align:center;
}
</style>
<title>indata-api</title>
</head>
<body>
	<div id="header">
		<h1>大数据仓库平台API自动化测试结果</h1>
	<!-- 	<p align="center">更新时间：${requestScope.hdfs_apilist.get(2).date}</p>  -->
	</div>
	<div id="table">
		<table border="1" cellpadding="20" align="center">
		
			<tr>
				<th> <font size="5">模组</font></th>
				<th> <font size="6">执
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				行
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				结 
			    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				果</font></th>	
				<th> <font size="5">更新时间</font></th>				
			</tr>
			<tr>				
				<th rowspan="10"><p><font size="5" color="red"  >Hbase</font></p></th>					
				<c:forEach items="${requestScope.hbase_apilist}" var="hbase_api">												        
					<tr>	
						<td>	
							<font face="微软雅黑"><p align="left">	${hbase_api.result } </p></font>
						</td> 
						<td>	
							<font face="微软雅黑">	${hbase_api.date } </font>
						</td> 
					</tr>				
				</c:forEach>					
			</tr>		
			<tr>				
				<th rowspan="15"><p><font size="5" color="blue"  >Hdfs</font></p></th>						
				<c:forEach items="${requestScope.hdfs_apilist}" var="hdfs_api">	
					<tr>	
						<td>	
							<font face="微软雅黑"><p align="left">	${hdfs_api.result } </p></font>
						</td> 
						<td>	
						 	<font face="微软雅黑">	${hdfs_api.date } </font>
						</td> 
					</tr>			        
				</c:forEach>					
			</tr>
			<tr>	
				<td rowspan="7"><p><font size="5" color="Darkorange">Hive</font></p></td>						
				<c:forEach items="${requestScope.hive_apilist}" var="hive_api">		        
					<tr>	
						<td>	
							<font face="微软雅黑"><p align="left">	${hive_api.result } </p></font>
						</td> 
						<td>	
							<font face="微软雅黑">	${hive_api.date } </font>
						</td> 
					</tr>		
				 </c:forEach>									
			</tr>			
			<tr>				
				<td rowspan="3"><p><font size="5" color="black">Kafka</font></p></td>				 	
				<c:forEach items="${requestScope.kafka_apilist}" var="kafka_api">		        
					<tr>	
						<td>	
							<p align="left"><font face="微软雅黑">	${kafka_api.result } </font> </p>
						</td> 
						<td>	
							<font face="微软雅黑">	${kafka_api.date } </font>
						</td> 
					</tr>	
				 </c:forEach>									
			</tr>
			
		</table>
 	</div>
</body>
	
</html>