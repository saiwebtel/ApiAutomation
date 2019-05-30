package base;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import service.HttpRequestHandler;

import java.util.Random;
public class Testbase 
{
	public static Properties Repository = new Properties();
	public static int RESPONSE_STATUS_CODE_200=200;
	public static int RESPONSE_STATUS_CODE_400=400;
	public static int RESPONSE_STATUS_CODE_500=500;
	public Connection con,con1;
	public String URL_FOR_COMIC="jdbc:oracle:thin:@10.67.200.35:1521:ADELEDB";
	public String USERNAME_FOR_COMIC="SVT_COMICS_29NOV17";
	public String PASSWORD_FOR_COMIC="SVT_COMICS_29NOV17";
	public String URL_FOR_UUSD="jdbc:oracle:thin:@10.67.202.101:1521:bgcdb1";
	public String USERNAME_FOR_UUSD = "SVTUUSD_MIG_17MAR17";
	public String PASSWORD_FOR_UUSD="SVTUUSD_MIG_17MAR17";
	public File file;
	public FileInputStream fileinputstream;
	public ResultSet resultset,resultset1;
	public Statement statement,statement1;
	public String tvid;
	public static int i=0;
	public int subscriberNA=0;
    //Testbase testbase=new Testbase();
	Random rand = new Random();
	HttpRequestHandler httprequesthandler;

	
	@BeforeSuite(enabled=true)
	public void initialiseDB()
	{
		getConnectionForComicDatabse();
		getConnectionForUUSDDatabse();
		httprequesthandler=new HttpRequestHandler();
	}
	
	@AfterSuite
	public void closeConnection() throws SQLException
	{
		con.close();
		con1.close();
	}
	public  void loadPropertiesFile() throws IOException
	{
		file=new File(System.getProperty("user.dir")+"\\src\\test\\java\\config\\configuration.properties");
		fileinputstream=new FileInputStream(file);
		Repository.load(fileinputstream);
	}
	public void getConnectionForComicDatabse()
	{
		try{  
			//step1 load the driver class  
		    Class.forName("oracle.jdbc.driver.OracleDriver");  
			//step2 create  the connection object  
			con=DriverManager.getConnection(URL_FOR_COMIC,USERNAME_FOR_COMIC,PASSWORD_FOR_COMIC); 
			System.out.println("Comic connection Established");
			System.out.println(con);
             }
         catch(Exception e)
		    { 
				System.out.println(e);
		    }  
	}
	public void getConnectionForUUSDDatabse()
	{
		try{  
			//step1 load the driver class  
		    Class.forName("oracle.jdbc.driver.OracleDriver");  
			//step2 create  the connection object  
			con1=DriverManager.getConnection(URL_FOR_UUSD,USERNAME_FOR_UUSD,PASSWORD_FOR_UUSD); 
			System.out.println("UUSD connection Established");
			System.out.println(con1);
             }
         catch(Exception e)
		    { 
				System.out.println(e);
		    }  
	}
	public String getTvidFromUUSD() throws SQLException
	{
		initialiseDB();
		statement1=con1.createStatement();
		resultset1=statement1.executeQuery("select SUBSCRIBER_NA from CUSTOMER_NA where STATUS_CODE='A' and CNA_UUID between 1 and 3000");
		ArrayList<String> arraylist=new ArrayList<String>();
		while(resultset1.next())  
		{
			arraylist.add(resultset1.getString(1));
		}
		tvid=arraylist.get(rand.nextInt(100) + 1);
		return tvid;
	}

	public List<String> getDataFromComicdatabase() throws SQLException
	{
		ArrayList<String> arraylist=new ArrayList<String>();
		try{
		System.out.println("=========>"+tvid);
		statement=con.createStatement();
		resultset=statement.executeQuery("select * from CONSENTS where TVID="+tvid);
		System.out.println("=========>"+tvid);
		while(resultset.next())  
		{
			arraylist.add(resultset.getString(3));
			arraylist.add(resultset.getString(4));
			arraylist.add(resultset.getString(5));
			arraylist.add(resultset.getString(7));
		    arraylist.add(resultset.getString(8));
		   System.out.println("---->"+arraylist.get(0));
		} 
		int size = arraylist.size(); 
		System.out.println("size of ArrayList after clearing elements: " + size);

		
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		 return arraylist;		
		}
	


	public static void main(String[] args) throws SQLException 
	{
			Testbase ts=new Testbase();
			//ts.getConnectionForUUSDDatabse();
	  	    //ts.getTvidFromUUSD();
			//ts.getDataFromComic();
	  	    //ts.getDataFromComicdatabase();

	}
	
}
