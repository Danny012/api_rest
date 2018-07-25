package com.inspur.restApi;

import java.io.PrintStream;

import org.apache.log4j.Logger;

import com.inspur.util.dao;
import com.inspur.util.access_token;
import com.inspur.util.cluster_standard;
import com.inspur.util.create_service;
import com.inspur.util.delete;
import com.inspur.util.inquire_service;
import com.inspur.util.logException;

public class restApi {
	public static Logger logger = Logger.getLogger(restApi.class);
	static logException le = new logException();
	public static PrintStream ps;

	public static void main(String[] args) {
		{
			int i = 1;
			int j = 0;
			dao d = new dao();
			try {
				ps = new PrintStream("restApi_report.txt");				
				access_token at = new access_token();			
				String token=at.spliteString().substring(0,5)+"..."+at.spliteString().substring(at.spliteString().length()-6, at.spliteString().length()-1);
			    //System.out.println(token);
				if (true) {
					String message=i + "����ȡtokenֵ�ɹ�, tokenֵΪ��"+ token;
					System.out.println( message);
					ps.print(i+"��"+at.spliteString());
					d.save(i, message);
					i++;
				} else {
					j++;
				}	
				ps.print(i+"��");
				cluster_standard cs = new cluster_standard();
				if (cs.accseeCluster()) {
					String message=i + "����ѯ��Ⱥ������ɹ���tokenֵΪ��" +token;
					System.out.println(message);
					d.save(i, message);
					i++;
				} else {
					j++;
				}
				ps.print(i+"��");
				create_service crs = new create_service();
				String user=crs.createService();
				if (user!=null) {
					crs.spliteService();	
					String message=i + "�����񴴽��ɹ�,�û���Ϊ�� "+user;
					System.out.println(message);
					d.save(i, message);
					i++;
				} else {
					j++;
				}
				ps.print(i+"��");
				inquire_service is=new inquire_service();
				if (is.getInstance()) {	
					String message=i + "����ѯ����ɹ����û�Ϊ: "+user;
					System.out.println(message);
					d.save(i, message);
					i++;
				} else {
					j++;
				}
				
				ps.print(i+"��");
				delete de = new delete();
				if (de.deleteService(de.id())) {
					String message=i + "��ɾ������ɹ�,�û�Ϊ��"+user;
					System.out.println(message);
					d.save(i, message);
				} else {
					j++;
				}
			} catch (Exception e) {
				logger.error(logException.getTrace(e));
			}
			System.out.println("---------------------------------------------");
			System.out.println("sucesss :  " + i + "       fail :  " + j);
		}

	}

}
