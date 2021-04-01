package com.royware.corona.dashboard.model.data.us;

import java.time.LocalDate;

public class DatesCDC_CovActNow {
	private String dateAsStringYYYYMMDD;	
	private int dateAsIntegerYYYYMMDD;
	private LocalDate dateAsLocalDate;
	
	public DatesCDC_CovActNow() {
		super();
	}

	public void setDateFields(String dateTimeString_YYYY_MM_DD) {
		dateAsStringYYYYMMDD = dateTimeString_YYYY_MM_DD.substring(0, 4) + dateTimeString_YYYY_MM_DD.substring(5, 7) + dateTimeString_YYYY_MM_DD.substring(8, 10);
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + dateAsIntegerYYYYMMDD;
		result = prime * result + ((dateAsLocalDate == null) ? 0 : dateAsLocalDate.hashCode());
		result = prime * result + ((dateAsStringYYYYMMDD == null) ? 0 : dateAsStringYYYYMMDD.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DatesCDC_CovActNow other = (DatesCDC_CovActNow) obj;
		if (dateAsIntegerYYYYMMDD != other.dateAsIntegerYYYYMMDD)
			return false;
		if (dateAsLocalDate == null) {
			if (other.dateAsLocalDate != null)
				return false;
		} else if (!dateAsLocalDate.equals(other.dateAsLocalDate))
			return false;
		if (dateAsStringYYYYMMDD == null) {
			if (other.dateAsStringYYYYMMDD != null)
				return false;
		} else if (!dateAsStringYYYYMMDD.equals(other.dateAsStringYYYYMMDD))
			return false;
		return true;
	}
}
