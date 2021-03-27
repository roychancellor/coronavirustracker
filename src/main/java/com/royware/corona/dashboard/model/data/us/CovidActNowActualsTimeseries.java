package com.royware.corona.dashboard.model.data.us;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_EMPTY)
public class CovidActNowActualsTimeseries {
	@JsonProperty("actualsTimeseries") private CovidActNowActual[] actualsTimeSeries;	
}
