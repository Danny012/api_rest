package com.inspur.kafka.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import com.inspur.kafka.util.SecurityUtils;

public class Producer {

	public static List<String> produceMessage(String topicName) {
		 Properties props = SecurityUtils.getSecurityProperties();
		props.put("bootstrap.servers", "indata-10-110-13-164.indata.com:6667");
		props.put("acks", "all");
		props.put("retries", 0);
		props.put("batch.size", 16384);
		props.put("linger.ms", 1);
		props.put("buffer.memory", 33554432);
		props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		KafkaProducer<String, String> producer = new KafkaProducer<>(props);
		List<String> newsList=new ArrayList<>();
		for (int i = 1; i < 5; i++) {		
			String message="\t向kafka写入第  "+i+"  条消息 \n";
			producer.send(new ProducerRecord<String, String>(topicName, message));			
		   newsList.add(message);
		}	
		
		producer.flush();
		producer.close();
		return newsList;
	//	System.out.println("ok");
	}

}
