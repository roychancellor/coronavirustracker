package com.royware.corona.dashboard.model;

import org.springframework.stereotype.Service;

@Service
public class DashboardStatistics {
	private int casesTotal;
	private int casesToday;
	private double rateOfCasesToday;
	private double accelOfCasesToday;
	private int deathsTotal;
	private int deathsToday;
	private double rateOfDeathsToday;
	private double rateOfCasesPerCasesToday;
	private double rateOfDeathsPerDeathsToday;
	
	public DashboardStatistics() {
	}

	public int getCasesTotal() {
		return casesTotal;
	}

	public void setCasesTotal(int casesTotal) {
		this.casesTotal = casesTotal;
	}

	public int getCasesToday() {
		return casesToday;
	}

	public void setCasesToday(int casesToday) {
		this.casesToday = casesToday;
	}

	public double getRateOfCasesToday() {
		return rateOfCasesToday;
	}

	public void setRateOfCasesToday(double rateOfCasesToday) {
		this.rateOfCasesToday = rateOfCasesToday;
	}

	public double getAccelOfCasesToday() {
		return accelOfCasesToday;
	}

	public void setAccelOfCasesToday(double accelOfCasesToday) {
		this.accelOfCasesToday = accelOfCasesToday;
	}

	public int getDeathsTotal() {
		return deathsTotal;
	}

	public void setDeathsTotal(int deathsTotal) {
		this.deathsTotal = deathsTotal;
	}

	public int getDeathsToday() {
		return deathsToday;
	}

	public void setDeathsToday(int deathsToday) {
		this.deathsToday = deathsToday;
	}

	public double getRateOfDeathsToday() {
		return rateOfDeathsToday;
	}

	public void setRateOfDeathsToday(double rateOfDeathsToday) {
		this.rateOfDeathsToday = rateOfDeathsToday;
	}

	public double getRateOfCasesPerCasesToday() {
		return rateOfCasesPerCasesToday;
	}

	public void setRateOfCasesPerCasesToday(double rateOfCasesPerCasesToday) {
		this.rateOfCasesPerCasesToday = rateOfCasesPerCasesToday;
	}

	public double getRateOfDeathsPerDeathsToday() {
		return rateOfDeathsPerDeathsToday;
	}

	public void setRateOfDeathsPerDeathsToday(double rateOfDeathsPerDeathsToday) {
		this.rateOfDeathsPerDeathsToday = rateOfDeathsPerDeathsToday;
	}
}
