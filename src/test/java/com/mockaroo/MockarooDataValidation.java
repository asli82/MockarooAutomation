package com.mockaroo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class MockarooDataValidation {

	WebDriver driver;

	@BeforeClass
	public void setup() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.get("https://mockaroo.com");
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		// driver.manage().window().fullscreen();
	}

	// 3. Assert title is correct.
	// 4. Assert Mockaroo and realistic data generator are displayed
	@Test(priority = 0)
	public void checkTitle() {
		String mockaroo = driver.findElement(By.xpath("//div[@class='brand']")).getText();
		String realistic = driver.findElement(By.xpath("//div[@class='tagline']")).getText();
		Assert.assertEquals(mockaroo, "mockaroo");
		Assert.assertEquals(realistic, "realistic data generator");
	}

	// 5.Remove all existing fields by clicking on x icon link
	@Test(priority = 1)
	public void remove() {
		for (int i = 6; i >= 1; i--) {
			driver.findElement(By.xpath("(//a[@class='close remove-field remove_nested_fields'])[" + i + "]")).click();
		}
		// List<WebElement> buttons =
		// driver.findElements(By.xpath("//div[@id='fields']//a[@class='close
		// remove-field remove_nested_fields']"));
		//
		// for (WebElement eachButton : buttons) {
		//
		// eachButton.click();
		// }
	}

	@Test(priority = 2)
	public void removeClickX() throws InterruptedException {
		// 6. Assert that ‘Field Name’ , ‘Type’, ‘Options’ labels are displayed
		// ‘Field Name’
		String fieldName = driver.findElement(By.xpath("//div[@class='column column-header column-name']")).getText();
		// ‘Type’
		String type = driver.findElement(By.xpath("//div[@class='column column-header column-type']")).getText();
		// ‘Options’
		String options = driver.findElement(By.xpath("//div[@class='column column-header column-options']")).getText();

		Assert.assertEquals(fieldName, "Field Name");
		Assert.assertTrue(type.equals("Type"));
		Assert.assertTrue(options.equals("Options"));

	}

	// 7. Assert that ‘Add another field’ button is enabled. Find using xpath with
	// tagname and text. isEnabled() method in selenium
	@Test(priority = 3)
	public void checkAddAnotherField() {
		boolean enabled = driver.findElement(By.xpath("//a[.='Add another field']")).isEnabled();
		Assert.assertTrue(enabled);
	}

	@Test(priority = 4)
	public void checkDefaults() {
		// 8. Assert that default number of rows is 1000.
		String rows = driver.findElement(By.xpath("//input[@class='medium-number form-control']"))
				.getAttribute("value");
		Assert.assertEquals(rows, "1000");

		// 9. Assert that default format selection is CSV
		Assert.assertEquals(driver.findElement(By.xpath("//select[@name='schema[file_format]']/option[1]")).getText(),
				"CSV");

		// 10. Assert that Line Ending is Unix(LF)
		Assert.assertEquals(driver.findElement(By.xpath("//select[@name='schema[line_ending]']/option[1]")).getText(),
				"Unix (LF)");

	}

	// 11. Assert that header checkbox is checked and BOM is unchecked
	@Test(priority = 5)
	public void checkedUnckecked() {

		WebElement headerChkbx = driver.findElement(By.id("schema_include_header"));
		WebElement bomChkbx = driver.findElement(By.id("schema_bom"));

		Assert.assertTrue(headerChkbx.isSelected());
		Assert.assertFalse(bomChkbx.isSelected());

	}

	// 12. Click on ‘Add another field’ and enter name “City”
	@Test(priority = 6)
	public void addCity() throws InterruptedException {
		driver.findElement(By.xpath("//a[@class='btn btn-default add-column-btn add_nested_fields']")).click();
		driver.findElement(By.xpath("//div[@id='fields']/div[7]/div[2]/input")).sendKeys("City");
		// 13. Click on Choose type and assert that Choose a Type dialog box is
		// displayed.
		driver.findElement(By.xpath("//div[@class='table-body']/div/div[7]/div[3]/input[3]")).click();
		Thread.sleep(2000);
		// 14. Search for “city” and click on City on search results.
		driver.findElement(By.id("type_search_field")).sendKeys("city");
		driver.findElement(By.xpath(" //div[@class='type'][@tabindex='1']")).click();

	}

	@Test(priority = 7)
	public void addCountry() throws InterruptedException {
		// 15. Repeat steps 12-14 with field name and type “Country”

		// driver.findElement(By.xpath("//a[@class='btn btn-default add-column-btn add_nested_fields']")).click();
		// driver.findElement(By.xpath("//div[@id='fields']/div[8]/div[2]/input")).sendKeys("Country");
		// driver.findElement(By.xpath("//div[@id='fields']/div[8]/div[3]/input[3]")).click();
		// Thread.sleep(2000);
		// driver.findElement(By.id("type_search_field")).sendKeys("Country");
		// driver.findElement(By.xpath(" //div[@class='type'][@tabindex='1']")).click();

		driver.findElement(By.xpath("//a[@class = 'btn btn-default add-column-btn add_nested_fields']")).sendKeys(Keys.ENTER + "country");
		Thread.sleep(1000);
		driver.findElement(By.xpath("//div[@id='fields']/div[8]/div[@class='column column-type']")).click();
		Thread.sleep(1000);
		driver.findElement(By.id("type_search_field")).clear();
		driver.findElement(By.id("type_search_field")).sendKeys("country");
		driver.findElement(By.xpath("//div[@class = 'type-name' and .='Country']")).click();
		Thread.sleep(1000);

		// Assert.assertTrue(city.equals("City"));

	}
//	 16. Click on Download Data. 
	@Test (priority = 8)
	public void download() {
		driver.findElement(By.id("download")).click();
	}
 
		
	@Test (priority = 9)
	public void complete() throws IOException {	
//	 17. Open the downloaded file using BufferedReader. 
		FileReader fr = new FileReader("C:\\Users\\a_tur\\Downloads\\MOCK_DATA.csv");
		BufferedReader br = new BufferedReader(fr);
//	 18. Assert that first row is matching with Field names that we selected. 
		String fieldCheck = br.readLine();
		System.out.println(fieldCheck);
		Assert.assertEquals("city,country", fieldCheck);
		
//	 19. Assert that there are 1000 records 
		List<String> linesList = new ArrayList<>();
		String temp = br.readLine();
		while(temp != null) {
			linesList.add(temp);
			temp = br.readLine();
		}

		//linesList.remove(linesList.get(0));  line 168 den dolayi gerek yok
		System.out.println("linesList.size() = " + linesList.size());
		Assert.assertTrue(linesList.size() == 1000);
			
//	 20. From file add all Cities to Cities ArrayList 
		List<String> cityList = new ArrayList<>();
		for(String each : linesList) {
			cityList.add(each.substring(0, each.indexOf(",")));
		}
//	 21. Add all countries to Countries ArrayList 
		List<String> countryList = new ArrayList<>();
		for(String each : linesList) {
			countryList.add(each.substring(each.indexOf(",") + 1));
		}
		System.out.println("countries.size() = "  + countryList.size());
//	 22. Sort all cities and find the city with the longest name and shortest name 
		Collections.sort(cityList);
		int maxName = cityList.get(0).length();
		String maxCharName = "";
		int minName = cityList.get(0).length();
		String minCharName = "";
		for(String each : cityList) {
			if(each.length() > maxName) {
				maxName = each.length();
				maxCharName = each;
			}
		}
		for(String each : cityList) {
			if(each.length() < minName) {
				minName = each.length();
				minCharName = each;
			}
		}
		System.out.println("the city with the longest name : " + maxCharName + " and the city with the shortest name : " + minCharName);
//	 23. In Countries ArrayList, find how many times each Country is mentioned. and
//	 print out ex: Indonesia-10 Russia-7 etc 
		Map<String, Integer> map = new HashMap();
		map.put(countryList.get(0), 1);
		for(String each : countryList) {
			if(!map.containsKey(each)) {
				map.put(each, 1);
			}else {
				map.put(each, map.get(each) + 1);
			}
		}
		Set<Entry<String, Integer>> entry = map.entrySet();
		for(Entry<String, Integer> each : entry) {                                          
			System.out.println(each.getKey() + "-" + each.getValue());
		}
//	 24. From file add all Cities to citiesSet HashSet 
	
		Set<String> citySet = new HashSet(cityList);


//	 25. Count how many unique cities are in Cities ArrayList
//	 and assert that it is matching with the count of citiesSet HashSet. 
		List<String> uniqueCityList= new ArrayList<>();
		for(String each : cityList) {
			if(!uniqueCityList.contains(each))
				uniqueCityList.add(each);
		}
		System.out.println("citySet size = " + citySet.size());
		System.out.println("cityList size = "  + cityList.size());
		Assert.assertEquals(citySet.size(), uniqueCityList.size());
//	 26. Add all Countries to countrySet HashSet 
		Set<String> countrySet = new HashSet();
		for(String each : countryList) {
			countrySet.add(each);
		}
//	 27. Count how many unique Countries are in Countries ArrayList and assert that it
//	  is matching with the count of countrySet HashSet. 
		List<String> uniqueCountryList = new ArrayList<>();
		for(String each : countryList) {
			if(!uniqueCountryList.contains(each))
				uniqueCountryList.add(each);
		}
		System.out.println("countrySet size = " + countrySet.size());
		System.out.println("countryList size = "  + countryList.size());
		Assert.assertEquals(countrySet.size(), uniqueCountryList.size());;
		//Assert.assertEquals(actual, expected);
		
		
	}	
}
//	 28. Push the code to any GitHub repo that you have and submit the url
	 
	 
	 

