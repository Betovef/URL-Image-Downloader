import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Scanner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

public class dynamic_scraping {
	private static String filePath;
	private static String url = "https://www.redfin.com/";
	private String img;

	public dynamic_scraping(String filePath) {
		this.filePath =filePath;
	}
	public String getImg() {
		return img;
	}

	public static void scrapeImg() {
		Scanner scanIn = null;
		String InputLine = "";
		try {
			System.setProperty("webdriver.chrome.driver", "C:\\webdrivers\\chromedriver.exe"); 
			WebDriver driver = new ChromeDriver();
			Actions act = new Actions(driver);
			WebElement elem = null;

			scanIn = new Scanner(new BufferedReader(new FileReader(filePath)));
			int i = 0;

			while(scanIn.hasNextLine()) {
				InputLine = scanIn.nextLine();

				try {
					driver.get("https://www.redfin.com/");
					Thread.sleep(1000);
					act.moveToElement(driver.findElement(By.xpath("/html/body/div[1]/div[6]/div[2]/div/section/div/div[1]/div/div/div/div/div/div[2]/div/div/form/div/div/input"))).click().build().perform();
					elem = driver.findElement(By.id("search-box-input"));
					elem.sendKeys(Keys.chord(Keys.CONTROL,"a", Keys.DELETE)); // delete id input 
					elem.sendKeys(InputLine);// input text
					elem.sendKeys(Keys.ENTER); // search
					driver.getCurrentUrl();
					Thread.sleep(4000);	
					elem = driver.findElement(By.xpath("/html/body/div[1]/div[8]/div[5]/section/div/div[2]/div[2]/span[1]/div/img")); //find the element of the picture
					if(elem.isDisplayed()) {
						System.out.println(elem.getAttribute("src")); //print src attributes
					}
				}
				catch(Exception e) {
					url = driver.getCurrentUrl();
					//					System.out.println(url);
					Document page;
					try {
						page = Jsoup.connect(url).get();
						Elements img = page.select(".streetViewImage.img-card");
						String src = "";
						System.out.println(img.attr("src").toString());
					} catch (IOException e1) {
						System.out.println("");
					}
				}
				i++;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

}
