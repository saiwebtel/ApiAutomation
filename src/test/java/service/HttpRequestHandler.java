package service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import base.Testbase;

public class HttpRequestHandler extends Testbase{
    public Testbase testbase;
    public static int i=0;
    HttpClient httpclient;
    HttpResponse httpresponse;
    String FinalURL;
    HttpPut httpput;
    //HttpRequestHandler httprequesthandler=new HttpRequestHandler();
    
	
    public HttpResponse SendRequest(String URL,String json) throws ClientProtocolException
     {
		httpclient=HttpClientBuilder.create().build();
    	httpput=new HttpPut(URL);
    	try {
			httpput.setEntity(new StringEntity(json));
		    } 
    	catch (UnsupportedEncodingException e)
    	    {
			e.printStackTrace();
		     }
    	httpput.addHeader("Content-Type", "application/json");
    	HttpResponse httpresponse=null;
    	try {
			httpresponse=httpclient.execute(httpput);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
    	return httpresponse;
	
      }

}
