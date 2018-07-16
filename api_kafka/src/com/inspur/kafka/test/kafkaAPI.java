package com.inspur.kafka.test;

import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.inspur.kafka.util.dao;
import com.inspur.kafka.util.logException;

public class kafkaAPI {
	static Logger logger = Logger.getLogger(kafkaAPI.class);
	static logException le = new logException();
	public static void main(String[] args) {
		
			int i = 0, j = 0;
			try {
				PrintStream ps = new PrintStream("hive_report.txt");
				dao d = new dao();
				SimpleDateFormat sdf = new SimpleDateFormat("mmss");
				Date date = new Date();
				String time = sdf.format(date);	
				String topicName="kafka_topic"+time;
				Producer p=new Producer();
				List< String> messageList=p.produceMessage(topicName);				
				if (messageList !=null) {
					i++;					
					String s="";
					for(int l=0;l<messageList.size();l++) {
						s +=" "+messageList.get(l)+" ";
					}
					String message = i + "、向kafka主题"+topicName+"写入消息成功，消息为：\n"+s;
					ps.println(message);
					System.out.println(message);
					d.save(i, message);
				} else {
					j++;
					ps.println(j + "、kafka消息生产失败");
				}
				Consumer consume=new Consumer();
				List< String> consumeMessageList=consume.consumeMessage(topicName);	
				if (consumeMessageList !=null) {
					i++;					
					String s="";
					for(int l=0;l<consumeMessageList.size();l++) {
						s +=" "+consumeMessageList.get(l)+" ";
					}
					String message = i + "、消费kafka主题"+topicName+"中消息成功，消息为：\n"+s;
					ps.println(message);
					System.out.println(message);
					d.save(i, message);
				} else {
					j++;
					ps.println(j + "、kafka消息消费失败");
				}
				
				}catch(Exception e) {
					e.printStackTrace();
					logger.error(le.getTrace(e));
				}
			System.out.println("---------------------------------------------");
	    	System.out.println("sucesss :  "+i +"       fail :  " + j);
			}
}
