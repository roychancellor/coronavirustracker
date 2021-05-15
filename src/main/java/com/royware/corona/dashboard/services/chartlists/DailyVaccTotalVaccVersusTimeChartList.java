package com.royware.corona.dashboard.services.chartlists;

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
import com.royware.corona.dashboard.services.charts.ChartListMakerUtilities;

@Component
public class DailyVaccTotalVaccVersusTimeChartList implements IChartList {
	private static final Logger log = LoggerFactory.getLogger(DailyVaccTotalVaccVersusTimeChartList.class);
	private Map<Integer, Double> dailyVacc = new HashMap<>();

	@Override
	public <T extends CanonicalCaseDeathData> List<List<Map<Object, Object>>> makeListFrom(List<T> regionDataList) {
		log.debug("MAKING TOTAL AND DAILY VACCINATIONS VERSUS TIME");
		//Transform the data into ChartJS-ready lists
		Map<Object, Object> xyPair;
		List<Map<Object, Object>> dataList = new ArrayList<>();
		List<List<Map<Object, Object>>> scatterChartDataLists = new ArrayList<>();
		
		xyPair = ChartListMakerUtilities.makeXYPairWithDateStamp(0,
				regionDataList.get(0).getTotalVaccCompleted(),
				regionDataList.get(0).getDateChecked().toString());
		dataList.add(xyPair);
		
		int dayIndex = 1;
		int totalYesterday = 0;
		int totalToday = 0;
		int dailyChange = 0;
		while(dayIndex < regionDataList.size()) {
			//TOTAL
			xyPair = ChartListMakerUtilities.makeXYPairWithDateStamp(dayIndex,
					regionDataList.get(dayIndex).getTotalVaccCompleted(),
					regionDataList.get(dayIndex).getDateChecked().toString());
			dataList.add(xyPair);
			
			//NEW
			totalYesterday = regionDataList.get(dayIndex - 1).getTotalVaccCompleted();
			totalToday = regionDataList.get(dayIndex).getTotalVaccCompleted();
			dailyChange = totalToday - totalYesterday;
			dailyChange = dailyChange > 0 ? dailyChange : 0;
			dailyVacc.put(dayIndex, dailyChange * 1.0);
			
			dayIndex++;
		}
		scatterChartDataLists.add(dataList);

		//make the MOVING AVERAGE of daily quantity to smooth out some of the noise
		scatterChartDataLists.add(
				ChartListMakerUtilities.makeMovingAverageList(
					dailyVacc,
					MovingAverageSizes.MOVING_AVERAGE_SIZE.getValue(),
					regionDataList.size()
				));
		
		log.debug("DONE MAKING TOTAL AND DAILY VACCINATIONS VERSUS TIME");

		return scatterChartDataLists;
	}
}
