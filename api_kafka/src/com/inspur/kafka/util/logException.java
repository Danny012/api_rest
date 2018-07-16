package com.inspur.kafka.util;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.log4j.PropertyConfigurator;

public class logException {
	public static String getTrace(Throwable t) {   
		  PropertyConfigurator.configure("resource/log4j.properties");
		StringWriter stringWriter= new StringWriter();   
		PrintWriter writer= new PrintWriter(stringWriter);   
		t.printStackTrace(writer);   
		StringBuffer buffer= stringWriter.getBuffer();   
		return buffer.toString();   
		}  
}
