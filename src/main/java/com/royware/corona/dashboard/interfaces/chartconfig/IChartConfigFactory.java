package com.royware.corona.dashboard.interfaces.chartconfig;

import org.springframework.stereotype.Service;
import com.royware.corona.dashboard.enums.charts.ChartTypes;

@Service
public interface IChartConfigFactory {
	public IChartConfigMaker create(ChartTypes chartType);
}
