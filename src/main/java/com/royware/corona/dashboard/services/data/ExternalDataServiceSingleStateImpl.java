package com.royware.corona.dashboard.services.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.royware.corona.dashboard.enums.data.DataUrls;
import com.royware.corona.dashboard.interfaces.data.ExternalDataService;
import com.royware.corona.dashboard.model.data.CaseDeathDataCDC;
import com.royware.corona.dashboard.model.data.HospitalDataCDC;
import com.royware.corona.dashboard.model.data.UnitedStatesData;

/**
 * Provides service methods for getting dashboard data from external sources
 */
@Component("singleState")
public class ExternalDataServiceSingleStateImpl implements ExternalDataService {
	@Autowired
	private RestTemplate restTemplate;
	
	private static final Logger log = LoggerFactory.getLogger(ExternalDataServiceSingleStateImpl.class);
	
	@SuppressWarnings("unchecked")
	@Override
	public List<UnitedStatesData> makeDataListFromExternalSource(String stateAbbreviation) {
		CaseDeathDataCDC[] stateCaseDeathDataArray;
		int tries = 0;
		do {
			try {
				String urlCaseDeath = DataUrls.STATE_DATA_URL_START.getName()
						+ DataUrls.STATE_DATA_URL_CASES_DEATHS.getName() + DataUrls.STATE_DATA_URL_END.getName()
						+ stateAbbreviation.toUpperCase();
				log.info("***** ABOUT TO HIT ENDPOINT FOR STATE CASE/DEATH DATA AT " + urlCaseDeath + " FOR " + stateAbbreviation);
				stateCaseDeathDataArray = restTemplate.getForObject(urlCaseDeath, CaseDeathDataCDC[].class);
			} catch (RestClientException e) {
				log.error("RestClientException is: " + e.getMessage());
				log.info("*** ERROR CONNECTING TO STATE DATA SOURCE FOR CASE/DEATH DATA: RETRYING: TRY #" + (tries+1) + " ***");
				tries++;
				stateCaseDeathDataArray = null;
			} 
		} while (tries <= 3 && stateCaseDeathDataArray == null);
		
		HospitalDataCDC[] hospitalizationDataArray;
		tries = 0;
		do {
			try {
				String urlHospitalization = DataUrls.STATE_DATA_URL_START.getName()
						+ DataUrls.STATE_DATA_URL_HOSPITAL_LOAD.getName() + DataUrls.STATE_DATA_URL_END.getName()
						+ stateAbbreviation.toUpperCase();
				log.info("***** ABOUT TO HIT ENDPOINT FOR STATE HOSPITALIZATION DATA AT " + urlHospitalization + " FOR "
						+ stateAbbreviation);
				hospitalizationDataArray = restTemplate.getForObject(urlHospitalization, HospitalDataCDC[].class);
			} catch (RestClientException e) {
				log.error("RestClientException is: " + e.getMessage());
				log.info("*** ERROR CONNECTING TO STATE DATA SOURCE FOR HOSPITAL DATA: RETRYING: TRY #" + (tries+1) + " ***");
				tries++;
				hospitalizationDataArray = null;
			} 
		} while (tries <= 3 && hospitalizationDataArray == null);
		
		if(stateCaseDeathDataArray == null || hospitalizationDataArray == null) {
			log.error("The returned stateCaseDeathDataArray or the hospitalizationDataArray (or both) is null!!!");
			return new ArrayList<UnitedStatesData>();
		}
		
		List<UnitedStatesData> stateDataList = buildUnitedStatesDataList(stateCaseDeathDataArray, hospitalizationDataArray);
		log.info("The size of the pre-filtered state data list for " + stateAbbreviation + " is: " + stateDataList.size());
		
		stateDataList.removeIf(unitedStatesCase -> (unitedStatesCase.getDateInteger() < US_CUTOFF_DATE));
		
		log.info("***** FINISHED GETTING DATA FOR STATE: " + stateAbbreviation + " ****");
		
		return stateDataList;
	}
	
	private List<UnitedStatesData> buildUnitedStatesDataList(CaseDeathDataCDC[] stateCaseDeathDataArray, HospitalDataCDC[] hospitalizationDataArray) {
		Map<Integer, CaseDeathDataCDC> casesAndDeaths = new HashMap<Integer, CaseDeathDataCDC>();
		Map<Integer, HospitalDataCDC> hospitalizations = new HashMap<Integer, HospitalDataCDC>();
		List<UnitedStatesData> toReturn = new ArrayList<>();
		
		for(CaseDeathDataCDC cd : stateCaseDeathDataArray) {
			cd.getDatesCDC().setDateFields(cd.getDateTimeString());
			casesAndDeaths.put(cd.getDatesCDC().getDateAsIntegerYYYYMMDD(), cd);
		}
		
		for(HospitalDataCDC h : hospitalizationDataArray) {
			h.getDatesCDC().setDateFields(h.getDateTimeString());
			hospitalizations.put(h.getDatesCDC().getDateAsIntegerYYYYMMDD(), h);
		}
		
		//Transform the data from the maps into the desired object
		Integer[] datesArray = casesAndDeaths.keySet().toArray(new Integer[0]);
		Arrays.parallelSort(datesArray);
		List<Integer> allCaseDates = Arrays.asList(datesArray);
		for(Integer caseDate : allCaseDates) {
			UnitedStatesData usd = new UnitedStatesData();
			usd.setDateChecked(casesAndDeaths.get(caseDate).getDatesCDC().getDateAsLocalDate());
			usd.setDateInteger(caseDate);
			usd.setDateTimeString(casesAndDeaths.get(caseDate).getDatesCDC().getDateAsStringYYYYMMDD());
			usd.setRegionString(casesAndDeaths.get(caseDate).getRegionString());
			usd.setTotalPositiveCases(casesAndDeaths.get(caseDate).getTotalPositiveCases());
			usd.setTotalDeaths(casesAndDeaths.get(caseDate).getTotalDeaths());
			if(hospitalizations.containsKey(caseDate)) {
				usd.setHospitalizedCurrently(hospitalizations.get(caseDate).getTotalBedsCovidCurrently());
			}
			toReturn.add(usd);
		}
		
		return toReturn;
	}
}
