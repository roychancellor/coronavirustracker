package com.royware.corona.dashboard.services.dashboard;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.royware.corona.dashboard.enums.data.MovingAverageSizes;
import com.royware.corona.dashboard.enums.regions.RegionsData;
import com.royware.corona.dashboard.interfaces.data.ExternalDataServiceFactory;
import com.royware.corona.dashboard.interfaces.model.CanonicalCaseDeathData;
import com.royware.corona.dashboard.model.dashboard.DashboardStatistics;
import com.royware.corona.dashboard.model.data.us.UnitedStatesData;
import com.royware.corona.dashboard.services.chart.config.makers.ChartConfigMakerUtilities;

public class DashboardStatisticsBuilder_DELETE_ME {
	@Autowired
	private ExternalDataServiceFactory dataFactory;
	
	private static final Logger log = LoggerFactory.getLogger(DashboardStatisticsBuilder_DELETE_ME.class);
	
	public DashboardStatistics makeDashboardStatsForRegion(DashboardStatistics dashStats, int regionPopulation,
			List<List<Map<Object, Object>>> chartDataCasesByTime,
			List<List<Map<Object, Object>>> chartDataCasesMovingSum,
			List<List<Map<Object, Object>>> chartDataDeathsByTime,
			List<List<Map<Object, Object>>> chartDataVaccByTime) {
		log.info("Making all the GENERAL DASHBOARD STATISTICS FOR REGION");
		///////// CASES /////////
		dashStats.setCasesTotal((int) chartDataCasesByTime.get(0).get(chartDataCasesByTime.get(0).size() - 1).get("y"));
		dashStats.setCasesToday((int) chartDataCasesByTime.get(0).get(chartDataCasesByTime.get(0).size() - 1).get("y")
				- (int) chartDataCasesByTime.get(0).get(chartDataCasesByTime.get(0).size() - 2).get("y"));
		dashStats.setCasesMovingSumPrimary((double) chartDataCasesMovingSum.get(0).get(chartDataCasesMovingSum.get(0).size() - 1).get("y"));
		dashStats.setCasesMovingSumSecondary((double) chartDataCasesMovingSum.get(1).get(chartDataCasesMovingSum.get(1).size() - 1).get("y"));
		
		///////// DEATHS /////////
		dashStats.setDeathsTotal((int) chartDataDeathsByTime.get(0).get(chartDataDeathsByTime.get(0).size() - 1).get("y"));
		dashStats.setDeathsToday((int) chartDataDeathsByTime.get(0).get(chartDataDeathsByTime.get(0).size() - 1).get("y")
				- (int) chartDataDeathsByTime.get(0).get(chartDataDeathsByTime.get(0).size() - 2).get("y"));
		dashStats.setDeathsMovingSumPrimary(
				(double) ChartConfigMakerUtilities.computeTotalQuantityLastN(chartDataDeathsByTime.get(0), MovingAverageSizes.CURRENT_POSITIVES_QUEUE_SIZE_PRIMARY.getValue())
				* 1.0 * MovingAverageSizes.PER_CAPITA_BASIS.getValue()
				/ regionPopulation);
		dashStats.setDeathsMovingSumSecondary(
				(double) ChartConfigMakerUtilities.computeTotalQuantityLastN(chartDataDeathsByTime.get(0), MovingAverageSizes.CURRENT_POSITIVES_QUEUE_SIZE_SECONDARY.getValue())
				* 1.0 * MovingAverageSizes.PER_CAPITA_BASIS.getValue()
				/ regionPopulation);
		
		///////// VACCINATIONS /////////
		dashStats.setTotalVaccCompleted((int) chartDataVaccByTime.get(0).get(chartDataVaccByTime.get(0).size() - 1).get("y"));
		dashStats.setVaccToday((int) chartDataVaccByTime.get(0).get(chartDataVaccByTime.get(0).size() - 1).get("y")
				- (int) chartDataVaccByTime.get(0).get(chartDataVaccByTime.get(0).size() - 2).get("y"));
		dashStats.setVaccMovingSumPrimary(
				(double) ChartConfigMakerUtilities.computeTotalQuantityLastN(chartDataVaccByTime.get(0), MovingAverageSizes.CURRENT_POSITIVES_QUEUE_SIZE_PRIMARY.getValue())
				* 1.0 * MovingAverageSizes.PER_CAPITA_BASIS.getValue()
				/ regionPopulation);
		dashStats.setVaccMovingSumSecondary(
				(double) ChartConfigMakerUtilities.computeTotalQuantityLastN(chartDataVaccByTime.get(0), MovingAverageSizes.CURRENT_POSITIVES_QUEUE_SIZE_SECONDARY.getValue())
				* 1.0 * MovingAverageSizes.PER_CAPITA_BASIS.getValue()
				/ regionPopulation);
		
		return dashStats;
	}

	public DashboardStatistics makeDashboardRowByUsTotals(int regionPopulation, DashboardStatistics dashStats) {
		log.info("Making all the DASHBOARD STATISTICS FOR REGION - BY U.S. TOTALS");
		log.debug("Getting U.S. data for populating By U.S. Totals row of dashboard...");
		List<UnitedStatesData> usaData = RegionsData.USA.getCoronaVirusDataFromExternalSource(dataFactory.getExternalDataService(RegionsData.USA.name()));
		int totalUSCases = usaData.get(usaData.size() - 1).getTotalPositiveCases();
		log.debug("Making totalUSCases");
		dashStats.setTotalUsCases(totalUSCases);
		log.debug("Making proportionOfRegionCasesToUsCases");
		dashStats.setProportionOfRegionCasesToUsCases(dashStats.getCasesTotal() * 100.0 / totalUSCases);
		int totalUSDeaths = usaData.get(usaData.size() - 1).getTotalDeaths();
		log.debug("Making totalUSDeaths");
		dashStats.setTotalUsDeaths(totalUSDeaths);
		log.debug("Making proportionOfRegionDeathsToUsCases");
		dashStats.setProportionOfRegionDeathsToUsDeaths(dashStats.getDeathsTotal() * 100.0 / totalUSDeaths);
		log.debug("Making proportionOfRegionPopToUsPop");
		dashStats.setProportionOfRegionPopToUsPop(regionPopulation * 100.0 / RegionsData.USA.getRegionData().getPopulation());
		int totalUSVacc = usaData.get(usaData.size() - 1).getTotalVaccCompleted();
		log.debug("Making totalUSVacc");
		dashStats.setTotalUsVacc(totalUSVacc);
		log.debug("Making proportionOfRegionCasesToUsCases");
		dashStats.setProportionOfRegionVaccToUsVacc(dashStats.getTotalVaccCompleted() * 100.0 / totalUSVacc);
		
		return dashStats;
	}

	public <T extends CanonicalCaseDeathData> DashboardStatistics makeDashboardStatsForUSRegionsByTesting(List<T> dataList, DashboardStatistics dashStats) {
		log.debug("Getting the region population from the Regions enum");
		int usaPop = RegionsData.USA.getRegionData().getPopulation();
//		log.debug("Making total tests conducted");
//		dashStats.setTotalTestsConducted(dataList.get(dataList.size() - 1).getTotalPositiveCases()
//				+ dataList.get(dataList.size() - 1).getTotalNegativeCases());
//		log.debug("Making ProportionOfPositiveTests and ProportionOfPositiveTestsMovingAverage");
//		dashStats.setProportionOfPositiveTests(dataList.get(dataList.size() - 1).getTotalPositiveCases()
//				* 100.0 / dashStats.getTotalTestsConducted());
//		int totalTestsLastN = computeTotalTestsLastN(dataList, MovingAverageSizes.CURRENT_POSITIVES_QUEUE_SIZE_PRIMARY.getValue());
//		dashStats.setTotalTestsConductedLastN(totalTestsLastN);
//		dashStats.setProportionOfPositiveTestsMovingAverage(
//				computeTotalPositivesLastN(dataList, MovingAverageSizes.CURRENT_POSITIVES_QUEUE_SIZE_PRIMARY.getValue())* 100.0 / totalTestsLastN);
//		log.debug("Making ProportionOfPopulationTested");
//		dashStats.setProportionOfPopulationTested(dashStats.getTotalTestsConducted()
//				* 100.0 / usaPop);
		log.debug("Making ProportionOfDeathsFromPositives");
		dashStats.setProportionOfDeathsFromPositives(dataList.get(dataList.size() - 1).getTotalDeaths()
				* 100.0 / dataList.get(dataList.size() - 1).getTotalPositiveCases());
//		log.debug("Making ProportionOfDeathsFromTested");
//		dashStats.setProportionOfDeathsFromTested(dataList.get(dataList.size() - 1).getTotalDeaths()
//				* 100.0 / dashStats.getTotalTestsConducted());
//		log.debug("Making ProportionOfDeathsOfExtrapolatedCases");
//		dashStats.setProportionOfDeathsOfExtrapolatedCases(dataList.get(dataList.size() - 1).getTotalDeaths()
//				* 100.0 / (dashStats.getProportionOfPositiveTests() * usaPop));
		log.debug("Making ProportionOfPopulationVaccinated");
		dashStats.setProportionOfPopulationVaccinated(dashStats.getTotalVaccCompleted()
				* 100.0 / usaPop);
		
		return dashStats;
	}
}
