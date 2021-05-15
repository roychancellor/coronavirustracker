package com.royware.corona.dashboard.services.chartlistmakers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.royware.corona.dashboard.enums.data.MovingAverageSizes;
import com.royware.corona.dashboard.interfaces.charts.IChartList;
import com.royware.corona.dashboard.interfaces.model.CanonicalCaseDeathData;

@Component
public class TotalDeathsVersusTimeWithExponentialFitChartList implements IChartList {
	private static final Logger log = LoggerFactory.getLogger(TotalDeathsVersusTimeWithExponentialFitChartList.class);
	private Map<Integer, Double> dailyDeaths = new HashMap<>();

	@Override
	public <T extends CanonicalCaseDeathData> List<List<Map<Object, Object>>> makeListFrom(List<T> regionDataList) {
		log.debug("MAKING TOTAL AND DAILY DEATHS VERSUS TIME");
		//Transform the data into ChartJS-ready lists
		Map<Object, Object> xyPair;
		List<Map<Object, Object>> dataList = new ArrayList<>();
		List<List<Map<Object, Object>>> scatterChartDataLists = new ArrayList<>();

		log.debug("Making time history of TOTAL deaths");
		log.debug("Making time history of DAILY deaths");
		int startDayIndex = ChartListMakerUtilities.findFirstDayIndexWithPositiveDeaths(regionDataList);
		xyPair = ChartListMakerUtilities.makeXYPairWithDateStamp(startDayIndex,
				regionDataList.get(startDayIndex).getTotalDeaths(),
				regionDataList.get(startDayIndex).getDateChecked().toString());
		dataList.add(xyPair);
		
		int dayIndex = startDayIndex + 1;
		int totalYesterday = 0;
		int totalToday = 0;
		int dailyChange = 0;
		while(dayIndex < regionDataList.size()) {
			//TOTAL
			xyPair = ChartListMakerUtilities.makeXYPairWithDateStamp(
					dayIndex,
					regionDataList.get(dayIndex).getTotalDeaths(),
					regionDataList.get(dayIndex).getDateChecked().toString());
			dataList.add(xyPair);
			
			//NEW
			totalYesterday = regionDataList.get(dayIndex - 1).getTotalDeaths();
			totalToday = regionDataList.get(dayIndex).getTotalDeaths();
			dailyChange = totalToday - totalYesterday;
			dailyChange = dailyChange > 0 ? dailyChange : 0;
			dailyDeaths.put(dayIndex, dailyChange * 1.0);

			dayIndex++;
		}
		scatterChartDataLists.add(dataList);

		//make the MOVING AVERAGE of daily deaths to smooth out some of the noise
		log.debug("Making moving average of DAILY deaths");
		scatterChartDataLists.add(
			ChartListMakerUtilities.makeMovingAverageList(
				dailyDeaths,
				startDayIndex + MovingAverageSizes.MOVING_AVERAGE_SIZE.getValue(),
				regionDataList.size()
			));
		
		log.debug("DONE MAKING TOTAL AND DAILY DEATHS VERSUS TIME");

		return scatterChartDataLists;
	}

}
