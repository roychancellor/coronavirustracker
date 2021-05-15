package com.royware.corona.dashboard.services.chartlistmakers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.royware.corona.dashboard.enums.data.MovingAverageSizes;
import com.royware.corona.dashboard.interfaces.charts.ChartListMaker;
import com.royware.corona.dashboard.interfaces.model.CanonicalCaseDeathData;
import com.royware.corona.dashboard.services.charts.ChartListMakerUtilities;

@Component
public class DailyHospitalizedTotalWithMovingAverageListMaker implements ChartListMaker {
	private static final Logger log = LoggerFactory.getLogger(DailyHospitalizedTotalWithMovingAverageListMaker.class);
	private Map<Integer, Double> cumulHospitalizations = new HashMap<>();

	@Override
	public <T extends CanonicalCaseDeathData> List<List<Map<Object, Object>>> makeList(List<T> regionDataList) {
		log.debug("MAKING CUMULATIVE AND DAILY NEW HOSPITALIZATIONS (FROM CUMULATIVE) VERSUS TIME");
		//Transform the data into ChartJS-ready lists
		Map<Object, Object> xyPair;
		List<Map<Object, Object>> dataList = new ArrayList<>();
		List<List<Map<Object, Object>>> scatterChartDataLists = new ArrayList<>();

		log.debug("Making time history of CUMULATIVE hospitalizations");
		log.debug("Making time history of DAILY NEW hospitalizations (from Cumulative)");
		int startDayIndex = ChartListMakerUtilities.findFirstDayIndexWithPositiveCumulativeHospitalizations(regionDataList);
		xyPair = ChartListMakerUtilities.makeXYPairWithDateStamp(startDayIndex,
				regionDataList.get(startDayIndex).getHospitalizedCumulative(),
				regionDataList.get(startDayIndex).getDateChecked().toString());
		dataList.add(xyPair);
		
		int totalYesterday = 0;
		int totalToday = 0;
		int dailyChange = 0;
		int dayIndex = startDayIndex + 1;
		while(dayIndex < regionDataList.size()) {
			//TOTAL
			xyPair = ChartListMakerUtilities.makeXYPairWithDateStamp(dayIndex,
					regionDataList.get(dayIndex).getHospitalizedCumulative(),
					regionDataList.get(dayIndex).getDateChecked().toString());
			dataList.add(xyPair);
			
			//NEW
			totalYesterday = regionDataList.get(dayIndex - 1).getHospitalizedCumulative();
			totalToday = regionDataList.get(dayIndex).getHospitalizedCumulative();
			dailyChange = totalToday - totalYesterday;
			cumulHospitalizations.put(dayIndex, dailyChange * 1.0);
			
			dayIndex++;
		}
		scatterChartDataLists.add(dataList);

		//make the MOVING AVERAGE of daily new hospitalizations to smooth out some of the noise
		log.debug("Making moving average of DAILY NEW hospitalizations");
		scatterChartDataLists.add(
				ChartListMakerUtilities.makeMovingAverageList(
					cumulHospitalizations, startDayIndex + MovingAverageSizes.MOVING_AVERAGE_SIZE.getValue(),
					regionDataList.size()
				));
		
		log.debug("DONE MAKING CURRENT AND DAILY NEW HOSPITALIZATIONS (FROM CUMULATIVE) VERSUS TIME");

		return scatterChartDataLists;
	}
}
