package com.inspur.restApi;

import java.io.BufferedReader;  
import java.io.IOException;  
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;  
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;  
import java.util.List;  
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;  
import org.apache.http.HttpResponse;  
import org.apache.http.HttpStatus;  
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;  
import org.apache.http.client.entity.UrlEncodedFormEntity;  
import org.apache.http.client.methods.CloseableHttpResponse;  
import org.apache.http.client.methods.HttpGet;  
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;  
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;  
import org.apache.http.message.BasicNameValuePair;  
import org.apache.http.protocol.HTTP;  
import org.apache.http.util.EntityUtils;
import org.springframework.http.HttpMethod;


import com.inspur.util.access_token;
import com.inspur.util.logException;

import net.sf.json.JSONObject;  

  
/** 
 * @author 
 * @date 2017��10��23�� ����2:49 
 * HttpClient������ 
 */  
@SuppressWarnings("deprecation")
public class apiTest {  
	private static Log LOG = LogFactory.getLog(apiTest.class);
    
//     public static void main(String[] args) {
//    	 String url="http://10.110.13.166:9000/manage-cluster/service/indata/component/catalog";
//    	 Map  map=new HashMap<>();
//    	 access_token    token=new access_token();         			 
//    	 map.put("token", token.spliteString());
////    	 map.put("scope", "openid profile");
////    	 map.put("username", "superadmin");
////    	 map.put("password", "superadmin");
////    	 map.put("client_id", "indata-manage-portal");
//	    doPost(url, map);
//}

    /** 
     * get���� 
     * @return 
     */  
    @SuppressWarnings("resource")
	public static String doGet(String url) {  
        try {  
        	CloseableHttpClient client = HttpClients.createDefault();
        	HttpGet request = new HttpGet(url);  
            HttpResponse response = client.execute(request);     
            /**�����ͳɹ������õ���Ӧ**/  
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {  
                /**��ȡ���������ع�����json�ַ�������**/  
                String strResult = EntityUtils.toString(response.getEntity());  
                  
                return strResult;  
            }  
        }   
        catch (IOException e) {  
            e.printStackTrace();  
            LOG.error(logException.getTrace(e));
        }  
          
        return null;  
    }  
      
    
    public String doPost(String url, String data) throws Exception {
		HttpPost httpPost = new HttpPost(url);
		try {
			StringEntity se = new StringEntity(data);
			se.setContentType("text/json");
			httpPost.setEntity(se);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			LOG.error(logException.getTrace(e));
		}
		return executeRequest(httpPost, HttpMethod.POST);
	}
    
    
    public String executeRequest(HttpUriRequest httpRequest, HttpMethod method) throws Exception {
		httpRequest.setHeader("Content-Type", "application/json");
//		httpRequest.setHeader("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");
		httpRequest.setHeader("Accept", "application/json");
//		String decodeUserPassword = Base64.encodeBytes(("admin:admin").getBytes("UTF-8"));
		// �û�������Ϊadmin:admin
//		httpRequest.setHeader("Authorization", "Basic YWRtaW46YWRtaW4=");
		CloseableHttpClient httpclient = HttpClients.createDefault();
		
		String result = new JSONObject().toString();
		CloseableHttpResponse response = null;
		try {
			response = httpclient.execute(httpRequest);
			HttpEntity entity = response.getEntity();
			if(entity != null && !"".equals(entity)) {
				result =  EntityUtils.toString(entity);
				if(result == null || "".equals(result)) {
					result = new JSONObject().toString();
				}
			}
		} catch (Exception e) {
			LOG.error(logException.getTrace(e));
		}finally{
			try {
				httpclient.close();
				response.close();
			} catch (IOException e) {
				e.printStackTrace();
				LOG.error(logException.getTrace(e));
			}
		}
		
		if(response != null && response.getStatusLine().getStatusCode()/100 == 2 ) {
			LOG.debug("Request success, request=" + httpRequest + ", result=" + result);
			return result;
		}else{
			LOG.error("Request error! request=" + httpRequest + ", result=" + result);
			return result;
		}
	}
    
    /** 
     * post����(����key-value��ʽ�Ĳ���) 
     * @param url 
     * @param params 
     * @return 
     */  
    public static String doPost(String url, Map params){  
          
        BufferedReader in = null;    
        try {    
            // ����HttpClient    
        	CloseableHttpClient client = HttpClients.createDefault();  
            // ʵ����HTTP����    
            HttpPost request = new HttpPost();    
            request.setURI(new URI(url));  
              
            //���ò���  
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();   
            for (Iterator iter = params.keySet().iterator(); iter.hasNext();) {  
                String name = (String) iter.next();  
                String value = String.valueOf(params.get(name));  
                nvps.add(new BasicNameValuePair(name, value));  
                  
                //System.out.println(name +"-"+value);  
            }  
            request.setEntity(new UrlEncodedFormEntity(nvps,HTTP.UTF_8));               
            HttpResponse response = client.execute(request);    
            int code = response.getStatusLine().getStatusCode(); 
            if(code == 200){    //����ɹ�              	
                in = new BufferedReader(new InputStreamReader(response.getEntity()    
                        .getContent(),"utf-8"));  
                StringBuffer sb = new StringBuffer("");    
                String line = "";    
                String NL = System.getProperty("line.separator");    
                while ((line = in.readLine()) != null) {    
                    sb.append(line + NL);    
                }  
                  
                in.close();    
              // System.out.println(sb.toString());
                return sb.toString();  
               
            }  
            else{   //  
                System.out.println("״̬�룺" + code);  
                
                return null;  
            }  
        }  
        catch(Exception e){  
            e.printStackTrace();  
            LOG.error(logException.getTrace(e));
            return null;  
        }  
    }  
      
    /** 
     * post������������json��ʽ�Ĳ����� 
     * @param url 
     * @param params 
     * @return 
     */  
//    public static String doPost(String url, String params) throws Exception {  
//          
//        CloseableHttpClient httpclient = HttpClients.createDefault();  
//        HttpPost httpPost = new HttpPost(url);// ����httpPost     
//        httpPost.setHeader("Accept", "application/json");   
//        httpPost.setHeader("Content-Type", "application/json");  
//        String charSet = "UTF-8";  
//        StringEntity entity = new StringEntity(params, charSet);  
//        httpPost.setEntity(entity);          
//        CloseableHttpResponse response = null;  
//          
//        try {  
//              
//            response = httpclient.execute(httpPost);  
//            StatusLine status = response.getStatusLine();  
//            int state = status.getStatusCode();  
//            if (state == HttpStatus.SC_OK) {  
//                HttpEntity responseEntity = response.getEntity();  
//                String jsonString = EntityUtils.toString(responseEntity);  
//                return jsonString;  
//            }  
//            else{  
//                // logger.error("���󷵻�:"+state+"("+url+")");  
//                 System.out.println("���󷵻�:"+state+"("+url+")");
//            }  
//        }  
//        finally {  
//            if (response != null) {  
//                try {  
//                    response.close();  
//                } catch (IOException e) {  
//                    e.printStackTrace();  
//                }  
//            }  
//            try {  
//                httpclient.close();  
//            } catch (IOException e) {  
//                e.printStackTrace();  
//            }  
//        }  
//        return null;  
//    }  
      
}  