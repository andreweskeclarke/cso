package com.hydrophilik.cso.parseHtml;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/*
 * This class takes in a directory of html files that were scraped from the MWRD CSO site, parses them,
 * and creates a series of CSV files with the data.
 */
public class Parse {

	public static void main(String[] args) {

		// Set the directories
		String readDirectory = "/Users/scottbeslow/Documents/Orange Wall/Hydrophilik/csohno/scrapedSites/";		
		String writeDirectory = "/Users/scottbeslow/Documents/Orange Wall/Hydrophilik/csohno/Data/";
		File eventsFile = new File(writeDirectory + "events2013.csv");
		
		// Currently, the weather data (i.e. rain) collection is commented out.  I think we are going
		// to try and find a more reliable source for it.
		//File weatherFile = new File(writeDirectory + "weather2013.csv");

		// If the output files already exist, they will be deleted and re-written.
		if (eventsFile.exists()) {
			eventsFile.delete();
		}
		
		/*
		if (weatherFile.exists()) {
			weatherFile.delete();
		}
		*/
		
		FileWriter fwEv = null;
		//FileWriter fwW = null;
		
		try {
			eventsFile.createNewFile();
			//weatherFile.createNewFile();
			fwEv = new FileWriter(eventsFile.getAbsoluteFile());
			//fwW = new FileWriter(weatherFile.getAbsoluteFile());
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		BufferedWriter bwEv = new BufferedWriter(fwEv);
		//BufferedWriter bwW = new BufferedWriter(fwW);
		
		// Loop through all of the files, and parse the data into structures.
		File dir = new File(readDirectory);
		
		// The csoEvents map has the date as the key.  The plan is that there will be
		// one entry for each day.  Note that some of the MWRD synopsis pages stretch
		// over multiple days, which might create overlap or missed data.  We will
		// do our best to sort through that mess.
		Map<String, CsoEvent> csoEvents = new HashMap<String, CsoEvent>(366);
		
		for (File input : dir.listFiles()) {
			Document doc;

			try {
				doc = Jsoup.parse(input, "UTF-8", "");
			} catch (IOException e) {
				e.printStackTrace();
				continue;
			}

			Map<String, CsoEvent> thisDaysEvents = MwrdCsoSynopsisParser.buildEvents(doc);
			
			if (null == thisDaysEvents)
				continue;

/* TODO: Fix this, after having switched thisDaysEvents from a List to a Map.
			for (CsoEvent event : thisDaysEvents) {
				try {
					bwEv.write(event.parseToString() + "\n");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
*/			
		}
		
		try {
			bwEv.close();
			fwEv.close();
			//bwW.close();
			//fwW.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		

	}

}
