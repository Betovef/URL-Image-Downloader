import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class img_downloader {

	public img_downloader() {
	}
	
	public static void csvDownload(String filePath, String folderPath) throws FileNotFoundException {
		Scanner scanIn = null;
		scanIn = new Scanner(new BufferedReader(new FileReader(filePath)));
		int i = 0;
		String fileName = null, imgURL = null, row = null;
		double fileSize;
		;
		
		while(scanIn.hasNextLine()) {
			try {		
			row = scanIn.nextLine(); //reading every line in the csv file
			String[] data = row.split(","); //split the names of the files and the url
			if (data.length == 2 && (data[1] != "" || data[0] != "")) {		
			fileName = data[0]; // save names and url into variables 
			imgURL = data[1];
			
			URL url = new URL(imgURL); // setting up the url 
			HttpURLConnection http = (HttpURLConnection)url.openConnection();
			fileSize = (double)http.getContentLengthLong();
			BufferedInputStream in = new BufferedInputStream(http.getInputStream());
			
			File out = new File(folderPath + "\\" + fileName + ".jpg"); //writing the names and the path o
			FileOutputStream fos = new FileOutputStream(out);
			BufferedOutputStream bout = new BufferedOutputStream(fos, 1024);
			
			byte[] buffer = new byte[1024]; //writing code to visualize progress (per url)
			double downloaded = 0.00;
			int read = 0;
			double percentDownlaoded = 0.00;
			while((read = in.read(buffer, 0, 1024)) >= 0) {
				bout.write(buffer, 0, read);
				downloaded += read;
				percentDownlaoded = (downloaded*100)/fileSize;
				String percent = String.format("%4f",  percentDownlaoded);
				System.out.println("Downloaded " + percent + "% of a file.");
			}
			bout.close();
			in.close();
			System.out.println("Download " + i + " complete!");
			}
			else {
				continue;
			}
			}
			
			catch(Exception e) {
				System.out.println("Error on iteration " + i);
			}
			i++;
		}
		
	}
	public static void APIDownload(String key, String folderPath) {
		String imgURL = "https://maps.googleapis.com/maps/api/streetview?location=41.403609,2.174448&size=456x456&key=" + key;
		System.out.println(imgURL);
		try {
		URL url = new URL(imgURL); // setting up the url 
		HttpURLConnection http = (HttpURLConnection)url.openConnection();
		BufferedInputStream in = new BufferedInputStream(http.getInputStream());
		File out = new File(folderPath + "\\" + "experimentAPIkey.json"); 
		
		FileOutputStream fos = new FileOutputStream(out);
		BufferedOutputStream bout = new BufferedOutputStream(fos, 1024);
		bout.close();
		in.close();
		System.out.println("Download complete!");
		}
		catch(Exception e) {
			System.out.println("Error");
		}
		
	}
	public static void main(String[] args) throws FileNotFoundException {
		Scanner kbd = new Scanner(System.in);  
		int task; 
		String filePath, folderPath, key;
		
		System.out.println("Welcome to the image downloader! Please select the desired task:");
		System.out.println("1. Download image URLs from csv file");
		System.out.println("2. Get data from Zillow API");
		System.out.println("3. Dynamic scraping");
		task = kbd.nextInt();
		
		if(task == 1) {
			System.out.print("Please write the document path of your CSV file: ");
				filePath = kbd.nextLine();
				filePath = kbd.nextLine();
				
			System.out.print("Please write the folder path where images will be saved: ");
				folderPath = kbd.nextLine();
				
			System.out.println("File Path: " + filePath);
			System.out.println("Folder Path: " + folderPath);
				
			System.out.println("Downloading...");
			csvDownload(filePath, folderPath);
		}
		else if(task ==2) {
			System.out.println("Please enter Google API key: ");
			key = kbd.nextLine();
			key = kbd.nextLine();
			System.out.println("Please write the document path of your CSV file: ");
			folderPath = kbd.nextLine();
			APIDownload(key, folderPath);	
		}
		else if(task==3) {
			System.out.print("Please write the document path of your CSV file: ");
			filePath = kbd.nextLine();
			filePath = kbd.nextLine();
			dynamic_scraping Web = new dynamic_scraping(filePath);
			Web.scrapeImg();
		}
		else {
			System.out.println("Task not supported");
		}

	}

	

}
