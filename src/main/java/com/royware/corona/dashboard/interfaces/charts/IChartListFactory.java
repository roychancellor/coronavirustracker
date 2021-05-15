package com.royware.corona.dashboard.interfaces.charts;

import org.springframework.stereotype.Service;
import com.royware.corona.dashboard.enums.charts.ChartTypes;

@Service
public interface IChartListFactory {
	public IChartListMaker create(ChartTypes chartType);
}
