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
	private double accelOfDeathsToday;
	private int totalTestsConducted;
	private double proportionOfPopulationTested;
	private double proportionOfPositiveTests;
	private double proportionOfDeathsFromPositives;
	private double proportionOfDeathsFromTested;
	private double proportionOfDeathsOfExtrapolatedCases;
	private double totalUsCases;
	private double proportionOfRegionCasesToUsCases;
	private double totalUsDeaths;
	private double proportionOfRegionDeathsToUsDeaths;
	private double proportionOfRegionPopToUsPop;
	
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

	public double getAccelOfDeathsToday() {
		return accelOfDeathsToday;
	}

	public void setAccelOfDeathsToday(double accelOFDeathsToday) {
		this.accelOfDeathsToday = accelOFDeathsToday;
	}

	public int getTotalTestsConducted() {
		return totalTestsConducted;
	}

	public void setTotalTestsConducted(int totalTestsConducted) {
		this.totalTestsConducted = totalTestsConducted;
	}

	public double getProportionOfPopulationTested() {
		return proportionOfPopulationTested;
	}

	public void setProportionOfPopulationTested(double proportionOfPopulationTested) {
		this.proportionOfPopulationTested = proportionOfPopulationTested;
	}

	public double getProportionOfPositiveTests() {
		return proportionOfPositiveTests;
	}

	public void setProportionOfPositiveTests(double proportionOfPositiveTests) {
		this.proportionOfPositiveTests = proportionOfPositiveTests;
	}

	public double getProportionOfDeathsFromPositives() {
		return proportionOfDeathsFromPositives;
	}

	public void setProportionOfDeathsFromPositives(double proportionOfDeathsFromPositives) {
		this.proportionOfDeathsFromPositives = proportionOfDeathsFromPositives;
	}

	public double getProportionOfDeathsFromTested() {
		return proportionOfDeathsFromTested;
	}

	public void setProportionOfDeathsFromTested(double proportionOfDeathsFromTested) {
		this.proportionOfDeathsFromTested = proportionOfDeathsFromTested;
	}

	public double getProportionOfDeathsOfExtrapolatedCases() {
		return proportionOfDeathsOfExtrapolatedCases;
	}

	public void setProportionOfDeathsOfExtrapolatedCases(double proportionOfDeathsOfExtrapolatedCases) {
		this.proportionOfDeathsOfExtrapolatedCases = proportionOfDeathsOfExtrapolatedCases;
	}

	public double getTotalUsCases() {
		return totalUsCases;
	}

	public void setTotalUsCases(double totalUsCases) {
		this.totalUsCases = totalUsCases;
	}

	public double getProportionOfRegionCasesToUsCases() {
		return proportionOfRegionCasesToUsCases;
	}

	public void setProportionOfRegionCasesToUsCases(double proportionOfRegionCasesToUsCases) {
		this.proportionOfRegionCasesToUsCases = proportionOfRegionCasesToUsCases;
	}

	public double getTotalUsDeaths() {
		return totalUsDeaths;
	}

	public void setTotalUsDeaths(double totalUsDeaths) {
		this.totalUsDeaths = totalUsDeaths;
	}

	public double getProportionOfRegionDeathsToUsDeaths() {
		return proportionOfRegionDeathsToUsDeaths;
	}

	public void setProportionOfRegionDeathsToUsDeaths(double proportionOfRegionDeathsToUsDeaths) {
		this.proportionOfRegionDeathsToUsDeaths = proportionOfRegionDeathsToUsDeaths;
	}

	public double getProportionOfRegionPopToUsPop() {
		return proportionOfRegionPopToUsPop;
	}

	public void setProportionOfRegionPopToUsPop(double proportionOfRegionPopToUsPop) {
		this.proportionOfRegionPopToUsPop = proportionOfRegionPopToUsPop;
	}
}
