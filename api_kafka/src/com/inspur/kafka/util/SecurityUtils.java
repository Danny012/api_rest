package com.inspur.kafka.util;

import java.io.File;
import java.util.Properties;

public class SecurityUtils {
	private static final String USER_KEYTAB = "kafka.service.keytab";
	private static final String USER_PRINCIPAL = "kafka/indata-10-110-13-164.indata.com@INDATA.COM";

	public static Properties getSecurityProperties() {
		Properties props = new Properties();	
		if (USER_KEYTAB != null && USER_PRINCIPAL != null && 
				USER_KEYTAB.length() != 0 && USER_PRINCIPAL.length() != 0
				) {
			File kt_file=new File("resource/kafka.service.keytab");
			String keytab=kt_file.getAbsolutePath().replaceAll("[\\\\/]", "/") + "/";
			File krb5_file=new File("resource/krb5.conf");
			String krb5=krb5_file.getAbsolutePath().replaceAll("[\\\\/]", "/") ;	
			System.setProperty("java.security.krb5.conf", krb5);
			StringBuilder jaas = new StringBuilder("com.sun.security.auth.module.Krb5LoginModule required");
			jaas.append(" useKeyTab=true storeKey=true");		
			jaas.append(" keyTab=\"" + keytab+ "\"");           
			jaas.append(" principal=\"" + USER_PRINCIPAL + "\";");			
			//System.out.println(" principal=\"" + USER_PRINCIPAL + "\";");
			props.put("sasl.jaas.config", jaas.toString());
			props.put("security.protocol", "SASL_PLAINTEXT");
			props.put("sasl.kerberos.service.name", "kafka");
			props.put("sasl.mechanism", "GSSAPI");

		}

		return props;
	}

}
