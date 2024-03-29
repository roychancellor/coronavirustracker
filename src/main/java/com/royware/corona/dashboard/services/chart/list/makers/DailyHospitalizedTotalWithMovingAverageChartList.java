package com.royware.corona.dashboard.services.chart.list.makers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.royware.corona.dashboard.enums.data.DataTransformConstants;
import com.royware.corona.dashboard.interfaces.chartlist.IChartListMaker;
import com.royware.corona.dashboard.interfaces.model.ICanonicalCaseDeathData;

@Component
public class DailyHospitalizedTotalWithMovingAverageChartList implements IChartListMaker {
	private static final Logger log = LoggerFactory.getLogger(DailyHospitalizedTotalWithMovingAverageChartList.class);
	private Map<Integer, Double> cumulHospitalizations = new HashMap<>();

	@Override
	public <T extends ICanonicalCaseDeathData> List<List<Map<Object, Object>>> makeListFrom(List<T> regionDataList, int pop) {
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
					cumulHospitalizations, startDayIndex + DataTransformConstants.MOVING_AVERAGE_SIZE.getValue(),
					regionDataList.size()
				));
		
		log.debug("DONE MAKING CURRENT AND DAILY NEW HOSPITALIZATIONS (FROM CUMULATIVE) VERSUS TIME");

		return scatterChartDataLists;
	}
}
