package com.inspur.kafka.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import com.inspur.kafka.util.SecurityUtils;

public class Consumer {

	public static List<String> consumeMessage(String topicName) {

		 Properties props = SecurityUtils.getSecurityProperties();
		props.put("bootstrap.servers", "indata-10-110-13-164.indata.com:6667");
     	props.put("client.id", "client");
		props.put("group.id", UUID.randomUUID().toString());
		props.put("enable.auto.commit", "true");
		props.put("auto.offset.reset", "earliest");
		props.put("auto.commit.interval.ms", "1000");
		props.put("session.timeout.ms", "30000");
		props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
		consumer.subscribe(Arrays.asList(topicName));
		int i = 0;		
		List<String> newsList=new ArrayList<>();
			while(i<2) {
				ConsumerRecords<String, String> records = consumer.poll(2);			
			    for (ConsumerRecord<String, String> record : records) {	
			    	String message=record.value();
				// System.out.println(i + "   " + record.value());
				 newsList.add(message);
				i++;						
		}
			}			
	return newsList;
	}
	public static void main(String[] args) {
		consumeMessage("kafka_topic4702");
	}

}
