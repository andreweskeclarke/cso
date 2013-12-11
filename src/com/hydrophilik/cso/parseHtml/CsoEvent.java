package com.hydrophilik.cso.parseHtml;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class CsoEvent {
	private Calendar startTime = null;
	private Calendar endTime = null;
	private String outfallLocation = null;
	private int waterwaySegment;
	private int durationMinutes;
	String date;

	public CsoEvent(String date) {
		this.date = date;
	}

	public void buildCsoEvent(String [] eventTable) {
		
		outfallLocation = eventTable[0];
		waterwaySegment = Integer.parseInt(eventTable[1]);
		
		startTime = buildCalendar(eventTable[2], date);
		endTime =  buildCalendar(eventTable[3], date);

		
		long durationMs = endTime.getTimeInMillis() - startTime.getTimeInMillis();
		
		int minutes = (int) ((durationMs / (1000*60)) % 60);
		int hours   = (int) ((durationMs / (1000*60*60)) % 24);
		
		durationMinutes = hours * 60 + minutes;

	}
	
	private GregorianCalendar buildCalendar(String time, String date) {
		
		String [] dateSep = date.split("/");
		String [] timeSep = time.split(":");
		
		int month = Integer.parseInt(dateSep[0]) - 1;
		int day = Integer.parseInt(dateSep[1]);
		int year = Integer.parseInt(dateSep[2]);
		
		int hour = Integer.parseInt(timeSep[0]);
		int minute = Integer.parseInt(timeSep[1]);
		
		return (new GregorianCalendar(year, month, day, hour, minute));
		
	}
	
	public boolean isValid() {
		if (null == startTime)
			return false;
		return true;
	}
	
	public String parseToString() {

		return outfallLocation + ";" + waterwaySegment + ";" + date +
				";" + parseTime(startTime) + ";" + parseTime(endTime) + ";" +
				durationMinutes;

	}
	
	private String parseTime(Calendar time) {
		return Integer.toString(time.get(Calendar.HOUR)) + ":" +
				Integer.toString(time.get(Calendar.MINUTE));
	}
	
}
