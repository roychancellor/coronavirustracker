package com.royware.corona.dashboard.model.data;

import java.time.LocalDate;

public class DatesCDC {
	private String dateAsStringYYYYMMDD;	
	private int dateAsIntegerYYYYMMDD;
	private LocalDate dateAsLocalDate;
	
	public void setDateFields(String dateTimeString_YYYY_MM_DD) {
		dateAsStringYYYYMMDD = dateTimeString_YYYY_MM_DD.substring(0, 3) + dateTimeString_YYYY_MM_DD.substring(5, 6) + dateTimeString_YYYY_MM_DD.substring(8, 9);
		dateAsIntegerYYYYMMDD = Integer.parseInt(dateAsStringYYYYMMDD);
		dateAsLocalDate = LocalDate.of(dateAsIntegerYYYYMMDD/10000, (dateAsIntegerYYYYMMDD % 10000)/100, dateAsIntegerYYYYMMDD % 100);
	}

	public String getDateAsStringYYYYMMDD() {
		return dateAsStringYYYYMMDD;
	}

	public int getDateAsIntegerYYYYMMDD() {
		return dateAsIntegerYYYYMMDD;
	}

	public LocalDate getDateAsLocalDate() {
		return dateAsLocalDate;
	}
}
