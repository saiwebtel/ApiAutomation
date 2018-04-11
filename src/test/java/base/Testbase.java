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
import java.util.Properties;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import service.HttpRequestHandler;

import java.util.Random;
public class Testbase 
{
	public static Properties Repository = new Properties();
	public int RESPONSE_STATUS_CODE_200=200;
	public int RESPONSE_STATUS_CODE_400=400;
	public int RESPONSE_STATUS_CODE_500=500;
	public Connection con;
	public String URL_FOR_COMIC="jdbc:oracle:thin:@10.67.200.35:1521:ADELEDB";
	public String USERNAME_FOR_COMIC="SVT_COMICS_29NOV17";
	public String PASSWORD_FOR_COMIC="SVT_COMICS_29NOV17";
	public String URL_FOR_UUSD="jdbc:oracle:thin:@10.67.202.101:1521:bgcdb1";
	public String USERNAME_FOR_UUSD = "SVTUUSD_MIG_17MAR17";
	public String PASSWORD_FOR_UUSD="SVTUUSD_MIG_17MAR17";
	public File file;
	public FileInputStream fileinputstream;
	public ResultSet resultset;
	public Statement statement;
	public String tvid;
	public static int i=0;
    //Testbase testbase=new Testbase();
	Random rand = new Random();
	HttpRequestHandler httprequesthandler;

	
	@BeforeSuite
	public void initialiseDB()
	{
		getConnectionForComicDatabse();
		getConnectionForUUSDDatabse();
		httprequesthandler=new HttpRequestHandler();
	}
	
	@AfterSuite
	public void closeConnection() throws SQLException
	{
		//con.close();
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
			//con=DriverManager.getConnection(URL_FOR_UUSD,USERNAME_FOR_UUSD,PASSWORD_FOR_UUSD); 
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
			con=DriverManager.getConnection(URL_FOR_UUSD,USERNAME_FOR_UUSD,PASSWORD_FOR_UUSD); 
			System.out.println("Established");
             }
         catch(Exception e)
		    { 
				System.out.println(e);
		    }  
	}
	public String getTvidFromUUSD() throws SQLException
	{
		statement=con.createStatement();
		resultset=statement.executeQuery("select SUBSCRIBER_NA from CUSTOMER_NA where STATUS_CODE='A' and CNA_UUID between 1 and 2000");
		ArrayList<String> arraylist=new ArrayList<String>();

		while(resultset.next())  
		{
			arraylist.add(resultset.getString(1));
		}
		//System.out.println(arraylist.size());
		String tvid=arraylist.get(rand.nextInt(100) + 1);
		//System.out.println(tvid);
		return tvid;
	}
	public void getDataFromComic() throws SQLException
	{
		statement=con.createStatement();
		resultset=statement.executeQuery("select * from CONSENTS");
		ArrayList<String> arraylist=new ArrayList<String>();

		while(resultset.next())  
		{
			arraylist.add(resultset.getString(2)+""+resultset.getString(3)+""+resultset.getString(4)+""+resultset.getString(5)+""+resultset.getString(6)+""+resultset.getString(7)+""+resultset.getString(8)+""+resultset.getString(9)+""+resultset.getString(10)+""+resultset.getString(11));
		}

	}
	public static void main(String[] args) throws SQLException 
	{
	   
		Testbase ts=new Testbase();
		ts.getConnectionForUUSDDatabse();
		//ts.getConnectionForComicDatabse();
		ts.getTvidFromUUSD();
	}
	
}
