package com.hydrophilik.cso.parseHtml;

import java.util.HashMap;
import java.util.Map;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public abstract class MwrdCsoSynopsisParser {

	public static Map<String, CsoEvent> buildEvents(Document doc) {

		Map<String, CsoEvent> events = new HashMap<String, CsoEvent>();
		
		// Get the starting date
		String date = getUValue(doc.getElementById("lbdate"));	
		if (null == date)
			return null;
		
		// Some of the 
		

		Element eventTable = doc.getElementById("DG1");
		if (null == eventTable)
			return null;

		Elements eventsTags = eventTable.getElementsByTag("tr");
		
		for (Element event : eventsTags) {
			Elements theRow = event.getElementsByTag("td");
			
			int index = 0;
			String [] aTable = new String[5];
			
			for (Element field : theRow) {
				aTable[index] = field.text();
				index++;
			}
			
			if (false == aTable[0].equals("Outfall Location")) {
				CsoEvent csoEvent = new CsoEvent(date);
				csoEvent.buildCsoEvent(aTable);
				
				if (csoEvent.isValid()) {
					// events.put("",csoEvent);
				}
				
			}
		}
		
		return events;

	}
	
	private static String getUValue(Element content) {
		
		if (null == content)
			return null;
		
		Elements links = content.getElementsByTag("u");
		String retVal = null;
		if (null != links) {
			retVal = links.first().text();
		}
		
		return retVal;
	}
}
