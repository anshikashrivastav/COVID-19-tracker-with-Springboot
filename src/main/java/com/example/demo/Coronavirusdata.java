package com.example.demo;

import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import org.apache.commons.*;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

//import com.sun.tools.javac.util.List;


@Service
public class Coronavirusdata {
	
	

	private static String VIRUS_DATA_URL="https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";
	
	private List<Locationstats> allstats;
	//list to store data
	
	public List<Locationstats> getAllstats() {
		return allstats;
	}

	public void setAllstats(List<Locationstats> allstats) {
		this.allstats = allstats;
	}
	
	//post construct basically tells that after instance is created, execute
	@PostConstruct
	//use scheduled to update continuously
	@Scheduled(cron="* * 1 * * *")
	//6 stars for seconds minute hour day month year
	public void fetchvirusdata() throws IOException, InterruptedException
	{
		List<Locationstats> newstats= new ArrayList<>();
		//we make new list so that we can construct here and later populate all stats and it wont show loading on site then
		
		HttpClient client=HttpClient.newHttpClient();
		HttpRequest request=HttpRequest.newBuilder()
				               .uri(URI.create(VIRUS_DATA_URL))
				               .build();
		//request basically converts the string to url
		HttpResponse<String> httpResponse=client.send(request, HttpResponse.BodyHandlers.ofString());
		//System.out.println(httpResponse.body());
		//below code and pom.xml dependency copied from online
		StringReader csvBodyReader=new StringReader(httpResponse.body());
		
		Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader);
		for (CSVRecord record : records) {
			
			Locationstats stat=new Locationstats();
			
			stat.setState(record.get("Province/State"));
			stat.setCountry(record.get("Country/Region"));
			
			//we need the latest of the number so the last column is size()-1
			//but note that it returns a string but we need int so we use
			//Integer.parseInt()
			int latestcases=Integer.parseInt(record.get(record.size()-1));
			int prevday=Integer.parseInt(record.get(record.size()-2));
			stat.setLatesttotal(latestcases);
			stat.setDifffromprevday(latestcases-prevday);
			//country/Region is a header in csv file
		    //String id = record.get("Country/Region");
		   System.out.println(stat.toString());
		   
		   newstats.add(stat);
		}
		this.allstats=newstats;
	}

}
