package test;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import junit.framework.Assert;
import model.Consents;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import service.HttpRequestHandler;
import base.Testbase;

public class testCusService 
{
	    public Testbase testbase=new Testbase();
	    HttpRequestHandler httprequesthandler=new HttpRequestHandler();
	    public static int i=0;
	    HttpClient httpclient;
	    HttpResponse httpresponse;
	    String FinalURL,FinalURLForCRS;
	    HttpPut httpput;
	    HttpGet httpget;
	    String stvid;
	    List<String> dataFromComic;

  @BeforeMethod
  public void createURLForCusHttpRequest() throws SQLException, IOException
	{
		testbase.getConnectionForUUSDDatabse();
		testbase.loadPropertiesFile();
		String ip=Testbase.Repository.getProperty("comic_ip");
		String port=Testbase.Repository.getProperty("comic_port");
		String base=Testbase.Repository.getProperty("BASE_FOR_COMIC");
		String base1=Testbase.Repository.getProperty("BASE_FOR_COMIC_GET");
		stvid=testbase.getTvidFromUUSD();
		FinalURL="http://" + ip +":"+ port + base + stvid ;
		FinalURLForCRS="http://" + ip +":"+ port + base1 + stvid ;
	}

	@DataProvider()
	public Object[][] sendDataForCusAPI_Tvta_optin()
	{
		Object[][] obj=new Object[1][4];
		obj[0][0]="TVTA";
		obj[0][1]="OPTIN";
		obj[0][2]="Automation";
		obj[0][3]="Script";
		
		return obj;
		
	}
	@Test(dataProvider="sendDataForCusAPI_Tvta_optin",description="This Test Verifies the CUS call with TVTA-OPTIN")
	public void VerifyCUS_With_Tvta_Optin(String consentType,String consentValue,String lastupdatedby,String tvconsentmessageid) throws JsonGenerationException, JsonMappingException, IOException, SQLException
	{
		Consents consents = new Consents();
		consents.setConsentType(consentType);
		consents.setConsentValue(consentValue);
		consents.setLastUpdatedBy(lastupdatedby);
		consents.setConsentMessage(tvconsentmessageid);
		
		httpput=new HttpPut();
		//httpput.addHeader("Content-Type","application/json");
		ObjectMapper objectmapper=new ObjectMapper();
		//object to json file
		objectmapper.writeValue(new File("C:/Users/vinay.singh.IND-DEL/APIAUTOMATION_PROJECT/ApiAutomation/src/resources/consents.json"),consents);
		
		String userJsonstring=objectmapper.writeValueAsString(consents);
		/*System.out.println(userJsonstring);
		System.out.println(FinalURL);*/
		httpresponse=httprequesthandler.SendRequest(FinalURL,userJsonstring);
	  
		int statusCode=httpresponse.getStatusLine().getStatusCode();
		Assert.assertEquals(testbase.RESPONSE_STATUS_CODE_200, statusCode);
		//Reporter.log("Data stored in Comic Database for TVID"+stvid);
		dataFromComic=testbase.getDataFromComicdatabase();
		System.out.println("Data stored in Comic Database for TVID===> "+stvid);
		System.out.println("Data stored in Comic Database for TVID===> "+dataFromComic);
		Assert.assertEquals("TVTA",dataFromComic.get(0));
		Assert.assertEquals("OPTIN",dataFromComic.get(1));
		Assert.assertEquals("ACT",dataFromComic.get(2));
		Assert.assertEquals("Automation",dataFromComic.get(3));
		Assert.assertEquals("Script",dataFromComic.get(4));
	}
	@DataProvider()
	public Object[][] sendDataForCusAPI_TVReco_optin()
	{
		Object[][] obj=new Object[1][4];
		obj[0][0]="TVReco";
		obj[0][1]="OPTIN";
		obj[0][2]="Automation";
		obj[0][3]="Script";
		
		return obj;
		
	}
	@Test(dataProvider="sendDataForCusAPI_TVReco_optin",description="This Test Verifies the CUS call with TVReco-OPTIN")
	public void VerifyCUS_With_TVReco_Optin(String consentType,String consentValue,String lastupdatedby,String tvconsentmessageid) throws JsonGenerationException, JsonMappingException, IOException, SQLException
	{
		Consents consents = new Consents();
		consents.setConsentType(consentType);
		consents.setConsentValue(consentValue);
		consents.setLastUpdatedBy(lastupdatedby);
		consents.setConsentMessage(tvconsentmessageid);
		
		httpput=new HttpPut();
		//httpput.addHeader("Content-Type","application/json");
		ObjectMapper objectmapper=new ObjectMapper();
		//object to json file
		objectmapper.writeValue(new File("C:/Users/vinay.singh.IND-DEL/APIAUTOMATION_PROJECT/ApiAutomation/src/resources/consents.json"),consents);
		
		String userJsonstring=objectmapper.writeValueAsString(consents);
		/*System.out.println(userJsonstring);
		System.out.println(FinalURL);*/
		httpresponse=httprequesthandler.SendRequest(FinalURL,userJsonstring);
	  
		int statusCode=httpresponse.getStatusLine().getStatusCode();
		Assert.assertEquals(testbase.RESPONSE_STATUS_CODE_200, statusCode);
		//Reporter.log("Data stored in Comic Database for TVID"+stvid);
		dataFromComic=testbase.getDataFromComicdatabase();
		System.out.println("Data stored in Comic Database for TVID===> "+stvid);
		System.out.println("Data stored in Comic Database for TVID===> "+dataFromComic);
		Assert.assertEquals("TVReco",dataFromComic.get(0));
		Assert.assertEquals("OPTIN",dataFromComic.get(1));
		Assert.assertEquals("ACT",dataFromComic.get(2));
		Assert.assertEquals("Automation",dataFromComic.get(3));
		Assert.assertEquals("Script",dataFromComic.get(4));
	}
	// CRS API TEST
	@Test(description="This Test Verifies the CRS API")
	public void VerifyCRSAPI() throws JsonGenerationException, JsonMappingException, IOException, SQLException
	{
		httpget=new HttpGet();
		httpresponse=httprequesthandler1.SendGetRequest(FinalURLForCRS);
		System.out.println("FINAL URL :::::::: "+FinalURLForCRS);
		int statusCode=httpresponse.getStatusLine().getStatusCode();
		Assert.assertEquals(testbase.RESPONSE_STATUS_CODE_200, statusCode);
	}
	
public static void main(String args[]) throws SQLException, IOException
{
	
	testCusService ts=new testCusService();
	//String asd=ts.createURLForCusHttpRequest();
	//System.out.println(asd);
	//ts.VerifyCUS_With_Tvta_Optin("TVTA", "OPTIN", "a", "s");
	
}
}
