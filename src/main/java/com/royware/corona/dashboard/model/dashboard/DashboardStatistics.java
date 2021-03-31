package com.royware.corona.dashboard.model.dashboard;

import org.springframework.stereotype.Service;

@Service
public class DashboardStatistics {
	private int casesTotal;
	private int casesToday;
	private double casesPerCapita;
	private double casesPercentOfPop;
	private double casesMovingSumPrimary;
	private double casesMovingSumSecondary;
	private double rateOfCasesToday;
	private double accelOfCasesToday;
	
	private int deathsTotal;
	private int deathsToday;
	private double deathsPerCapita;
	private double deathsPercentOfPop;
	private double deathsMovingSumPrimary;
	private double deathsMovingSumSecondary;
	private double rateOfDeathsToday;
	private double rateOfCasesPerCasesToday;
	private double rateOfDeathsPerDeathsToday;
	private double accelOfDeathsToday;
	
	private int totalTestsConducted;
	private int totalTestsConductedLastN;
	
	private int totalVaccCompleted;
	private int vaccToday;
	private double vaccPerCapita;
	private double vaccPercentOfPop;
	private double vaccMovingSumPrimary;
	private double vaccMovingSumSecondary;
	
	private double proportionOfPopulationTested;
	private double proportionOfPopulationVaccinated;
	private double proportionOfPositiveTests;
	private double proportionOfPositiveTestsMovingAverage;
	private double proportionOfDeathsFromPositives;
	private double proportionOfDeathsFromTested;
	private double proportionOfDeathsOfExtrapolatedCases;
	
	private double totalUsCases;
	private double totalUsDeaths;
	private double totalUsVacc;
	
	private double proportionOfRegionCasesToUsCases;
	private double proportionOfRegionDeathsToUsDeaths;
	private double proportionOfRegionVaccToUsVacc;
	
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

	public double getCasesPerCapita() {
		return casesPerCapita;
	}

	public void setCasesPerCapita(double casesPerCapita) {
		this.casesPerCapita = casesPerCapita;
	}

	public double getCasesPercentOfPop() {
		return casesPercentOfPop;
	}

	public void setCasesPercentOfPop(double casesPercentOfPop) {
		this.casesPercentOfPop = casesPercentOfPop;
	}

	public double getCasesMovingSumPrimary() {
		return casesMovingSumPrimary;
	}

	public void setCasesMovingSumPrimary(double casesMovingSumPrimary) {
		this.casesMovingSumPrimary = casesMovingSumPrimary;
	}

	public double getCasesMovingSumSecondary() {
		return casesMovingSumSecondary;
	}

	public void setCasesMovingSumSecondary(double casesMovingSumSecondary) {
		this.casesMovingSumSecondary = casesMovingSumSecondary;
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

	public double getDeathsPerCapita() {
		return deathsPerCapita;
	}

	public void setDeathsPerCapita(double deathsPerCapita) {
		this.deathsPerCapita = deathsPerCapita;
	}

	public double getDeathsPercentOfPop() {
		return deathsPercentOfPop;
	}

	public void setDeathsPercentOfPop(double deathsPercentOfPop) {
		this.deathsPercentOfPop = deathsPercentOfPop;
	}

	public double getDeathsMovingSumPrimary() {
		return deathsMovingSumPrimary;
	}

	public void setDeathsMovingSumPrimary(double deathsMovingSumPrimary) {
		this.deathsMovingSumPrimary = deathsMovingSumPrimary;
	}

	public double getDeathsMovingSumSecondary() {
		return deathsMovingSumSecondary;
	}

	public void setDeathsMovingSumSecondary(double deathsMovingSumSecondary) {
		this.deathsMovingSumSecondary = deathsMovingSumSecondary;
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

	public int getTotalTestsConductedLastN() {
		return totalTestsConductedLastN;
	}

	public void setTotalTestsConductedLastN(int totalTestsConductedLastN) {
		this.totalTestsConductedLastN = totalTestsConductedLastN;
	}

	public int getTotalVaccCompleted() {
		return totalVaccCompleted;
	}

	public void setTotalVaccCompleted(int totalVaccCompleted) {
		this.totalVaccCompleted = totalVaccCompleted;
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

	public double getProportionOfPositiveTestsMovingAverage() {
		return proportionOfPositiveTestsMovingAverage;
	}

	public void setProportionOfPositiveTestsMovingAverage(double proportionOfPositiveTestsMovingAverage) {
		this.proportionOfPositiveTestsMovingAverage = proportionOfPositiveTestsMovingAverage;
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

	public double getProportionOfRegionVaccToUsVacc() {
		return proportionOfRegionVaccToUsVacc;
	}

	public void setProportionOfRegionVaccToUsVacc(double proportionOfRegionVaccToUsVacc) {
		this.proportionOfRegionVaccToUsVacc = proportionOfRegionVaccToUsVacc;
	}

	public double getProportionOfRegionPopToUsPop() {
		return proportionOfRegionPopToUsPop;
	}

	public void setProportionOfRegionPopToUsPop(double proportionOfRegionPopToUsPop) {
		this.proportionOfRegionPopToUsPop = proportionOfRegionPopToUsPop;
	}

	public double getProportionOfPopulationVaccinated() {
		return proportionOfPopulationVaccinated;
	}

	public void setProportionOfPopulationVaccinated(double proportionOfPopulationVaccinated) {
		this.proportionOfPopulationVaccinated = proportionOfPopulationVaccinated;
	}

	public double getTotalUsVacc() {
		return totalUsVacc;
	}

	public void setTotalUsVacc(double totalUsVacc) {
		this.totalUsVacc = totalUsVacc;
	}

	public int getVaccToday() {
		return vaccToday;
	}

	public void setVaccToday(int vaccToday) {
		this.vaccToday = vaccToday;
	}

	public double getVaccPerCapita() {
		return vaccPerCapita;
	}

	public void setVaccPerCapita(double vaccPerCapita) {
		this.vaccPerCapita = vaccPerCapita;
	}

	public double getVaccPercentOfPop() {
		return vaccPercentOfPop;
	}

	public void setVaccPercentOfPop(double vaccPercentOfPop) {
		this.vaccPercentOfPop = vaccPercentOfPop;
	}

	public double getVaccMovingSumPrimary() {
		return vaccMovingSumPrimary;
	}

	public void setVaccMovingSumPrimary(double vaccMovingSumPrimary) {
		this.vaccMovingSumPrimary = vaccMovingSumPrimary;
	}

	public double getVaccMovingSumSecondary() {
		return vaccMovingSumSecondary;
	}

	public void setVaccMovingSumSecondary(double vaccMovingSumSecondary) {
		this.vaccMovingSumSecondary = vaccMovingSumSecondary;
	}
}
