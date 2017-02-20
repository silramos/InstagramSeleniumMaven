package com.MavenSelenium.SeleniumMaven;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.By.ByXPath;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import com.MavenSelenium.SeleniumMaven.HelperFile.Helper;
import com.MavenSelenium.SeleniumMaven.HelperFile.Xpath;

import jxl.JXLException;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

public class App 
{	
	static WebDriver driver;
    public static void main( String[] args )throws InterruptedException, IOException, JXLException
    {			
    			//Initializing the helper class
    			Helper helper = new Helper();
    			
    			File f  = new File("C:\\Users\\Vaibhav\\Desktop\\InstagramAnalytics.xls");
    			WritableWorkbook myexcel = Workbook.createWorkbook(f);
    			WritableSheet mySheet = myexcel.createSheet("mySheet", 0);
    			//Label l = new Label(0,1,"Sata 1");
    			//mySheet.addCell(l);
    			//myexcel.write();
    			//myexcel.close();
    		
    			
    			//Initializing the ChromeBrowzer
				//Step 1. Download ChromeDriver suitable for your system 
				//Step 3. Use "key" as "webdriver.chrome.driver" in -  System.setProperty(key, value);
				//Step 4. Give the path for location of your chrome driver for the "value" in - System.setProperty(key, value);
				//Example : System.setProperty("webdriver.chrome.driver", "C:\\Users\\Vaibhav\\Downloads\\chromedriver_win32\\chromedriver.exe");
    			System.setProperty("webdriver.chrome.driver", "C:\\Users\\Vaibhav\\Downloads\\sikuli\\chromedriver_win32(1)\\chromedriver.exe");
    			//System.setProperty(key, value);
    			ChromeOptions chromeOptions = new ChromeOptions();
    			chromeOptions.addArguments("--start-maximized");
    			driver = new ChromeDriver(chromeOptions);
    			
    			//Declaring the Handle Names here 
    			String[] names = helper.handleNames.userHandleNames;
    			
    			//Declaring wait time variable which will be used inside the loop to show it down
    			int waitTime=0;
    			
    			// For future use
    			//SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
    			
    			// Counting number of UserNames that will be used in the program.
    			System.out.println("Total Number of Entries in the Array : "+ names.length);
    			Thread.sleep(2000);
    			
    			// Getting the system start time of the process for future use (Analytics)
    			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
    			DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
    			Date date = new Date();
    			System.out.println(dateFormat.format(date));
    			
    			//Start
    			long lStartTime = System.currentTimeMillis();
    			System.out.println("Time When Started = "+lStartTime);

    			
    			//Open Instagram Browzer
    			System.out.println("Opening Instagram.com");
    			driver.get("http://www.instagram.com");
    			Thread.sleep(4000);
    			
    			//1. Find the login button
    			driver.findElement(By.xpath(helper.xpath.loginText)).click();
    			
    			//2. Fill in the Credentials
    			System.out.println("Entering the Credentials . . . ");
    			driver.findElement(By.xpath(helper.xpath.userNameField)).sendKeys(helper.credentials.UserName);
    			Thread.sleep(2000);
    			driver.findElement(By.xpath(helper.xpath.passwordField)).sendKeys(helper.credentials.Password);
    			Thread.sleep(2000);
    			
    			//3. Hit the Login Button
    			driver.findElement(By.xpath(helper.xpath.loginButton)).click();
    			Thread.sleep(2000);
    			System.out.println("LogIn Successful!");
    			
    			//4. Loop for Celebrity Liking

//    				Date StartDate_Time = new Date(); // Get the start date & Time
    				for (int i=0; i<=names.length-1;i++)
    					{
    						openInstagramProfileForHandleName("http://www.instagram.com/"+names[i]);
    						waitTime = randomNumber(1,4); // Controls the speed of the process randomNumber(min wait time, max wait time)
    						System.out.println(i+1+". Opened "+names[i]+ "'s profile with waitTime = "+waitTime+ " seconds");
    						Thread.sleep(waitTime*1000); 
    					}
//    				Date EndDate_Time = new Date(); // Get final date & Time
    			
    				System.out.println("Done");
    			
    			//Stop
    			long lEndTime = System.currentTimeMillis();
    			System.out.println("Time When Ended = "+lEndTime);
    			long elapsedTime = lEndTime - lStartTime;
    		    System.out.println("Elapsed time in seconds: " + elapsedTime/1000);
    		    
    		    //Get the Number of Followers and Following
    		    driver.get("https://www.instagram.com/photato_head/");
    		    String numberOfFollowersString =driver.findElement(By.xpath("//*/section/main/article/header/div[2]/ul/li[2]/a/span")).getAttribute("title");
    			int numberOfFollowers = Integer.parseInt(numberOfFollowersString);
    		    
    		    
//    			Label l = new Label(dateFormat.format(date),timeFormat.format(date),lStartTime,lEndTime,elapsedTime/1000,numberOfFollowersString);
//				mySheet.addCell(l);
//    			myexcel.write();
//    			myexcel.close();
    		    
    			
    			Thread.sleep(2000);
    			driver.close();
    }
    public static WebDriver openInstagramProfileForHandleName(String HandleName)
    {
    	driver.get(HandleName);
    	driver.findElement(By.xpath("//*/section/main/article/header/div[2]/div[1]/span[2]/span[1]/button")).click(); //click on follow button
    	return driver;
    }
    	
    public static int randomNumber(int minimum,int maximum)
    {
    	Random rn = new Random();
    	int range = maximum - minimum + 1;
    	int randomNum =  rn.nextInt(range) + minimum;
    	return randomNum;
    	
    }
    
}
