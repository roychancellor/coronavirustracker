package com.royware.corona.dashboard.services.chartlistmakers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.royware.corona.dashboard.enums.data.MovingAverageSizes;
import com.royware.corona.dashboard.interfaces.charts.IChartListMaker;
import com.royware.corona.dashboard.interfaces.model.CanonicalCaseDeathData;

@Component
public class DailyHospitalizedNowWithMovingAverageChartList implements IChartListMaker {
	private static final Logger log = LoggerFactory.getLogger(DailyHospitalizedNowWithMovingAverageChartList.class);
	private Map<Integer, Double> dailyHospitalizations = new HashMap<>();

	@Override
	public <T extends CanonicalCaseDeathData> List<List<Map<Object, Object>>> makeListFrom(List<T> regionDataList, int pop) {
		log.debug("MAKING CURRENT AND DAILY NEW HOSPITALIZATIONS VERSUS TIME");
		//Transform the data into ChartJS-ready lists
		Map<Object, Object> xyPair;
		List<Map<Object, Object>> dataList = new ArrayList<>();
		List<List<Map<Object, Object>>> scatterChartDataLists = new ArrayList<>();
		//Find the first object having a current hospitalizations > 0 for two consecutive days
		log.debug("Making time history of CURRENT hospitalizations");
		log.debug("Making time history of DAILY NEW hospitalizations");
		
		//First day with positive hospitalizations
		int startDayIndex = ChartListMakerUtilities.findFirstDayIndexWithPositiveCurrentHospitalizations(regionDataList);
		xyPair = ChartListMakerUtilities.makeXYPairWithDateStamp(startDayIndex,
				regionDataList.get(startDayIndex).getHospitalizedCurrently(),
				regionDataList.get(startDayIndex).getDateChecked().toString());
		dataList.add(xyPair);
		
		//All remaining days
		int dayIndex = startDayIndex + 1;
		int totalYesterday = 0;
		int totalToday = 0;
		int dailyChange = 0;
		while(dayIndex < regionDataList.size()) {
			//CURRENT
			xyPair = ChartListMakerUtilities.makeXYPairWithDateStamp(dayIndex,
					regionDataList.get(dayIndex).getHospitalizedCurrently(),
					regionDataList.get(dayIndex).getDateChecked().toString());
			dataList.add(xyPair);
			
			//NEW
			totalYesterday = regionDataList.get(dayIndex - 1).getHospitalizedCurrently();
			totalToday = regionDataList.get(dayIndex).getHospitalizedCurrently();
			dailyChange = totalToday - totalYesterday;
			dailyHospitalizations.put(dayIndex, dailyChange * 1.0);

			dayIndex++;
		}
		scatterChartDataLists.add(dataList);

		//make the MOVING AVERAGE of daily new hospitalizations to smooth out some of the noise
		log.debug("Making moving average of DAILY NEW hospitalizations");
		scatterChartDataLists.add(
				ChartListMakerUtilities.makeMovingAverageList(
					dailyHospitalizations,
					startDayIndex + MovingAverageSizes.MOVING_AVERAGE_SIZE.getValue(),
					regionDataList.size()
				));
		
		log.debug("DONE MAKING CURRENT AND DAILY NEW HOSPITALIZATIONS VERSUS TIME");

		return scatterChartDataLists;
	}
}
