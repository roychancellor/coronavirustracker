package com.royware.corona.dashboard.services.data.us;

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
import com.royware.corona.dashboard.model.data.us.CaseDeathDataCDC;
import com.royware.corona.dashboard.model.data.us.VaccinationDataCAN;
import com.royware.corona.dashboard.model.data.us.VaccinationTimeSeriesCAN;
import com.royware.corona.dashboard.model.data.us.HospitalDataCDC;
import com.royware.corona.dashboard.model.data.us.UnitedStatesData;

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
				String urlCaseDeath = DataUrls.STATE_DATA_URL_CASES_DEATHS_START.getText()
						+ DataUrls.STATE_DATA_URL_CASES_DEATHS.getText() + DataUrls.STATE_DATA_URL_END.getText()
						+ stateAbbreviation.toUpperCase();
				log.debug("CASE/DEATH DATA AT " + urlCaseDeath + " FOR " + stateAbbreviation);
				stateCaseDeathDataArray = restTemplate.getForObject(urlCaseDeath, CaseDeathDataCDC[].class);
			} catch (RestClientException e) {
				log.error("RestClientException is: " + e.getMessage());
				log.info("ERROR CONNECTING TO CASE/DEATH DATA: RETRYING: TRY #" + (tries+1));
				tries++;
				stateCaseDeathDataArray = null;
			} 
		} while (tries <= 3 && stateCaseDeathDataArray == null);
		
		HospitalDataCDC[] hospitalizationDataArray;
		tries = 0;
		do {
			try {
				String urlHospitalization = DataUrls.STATE_DATA_URL_HOSPITAL_LOAD_START.getText()
						+ DataUrls.STATE_DATA_URL_HOSPITAL_LOAD.getText() + DataUrls.STATE_DATA_URL_END.getText()
						+ stateAbbreviation.toUpperCase();
				log.debug("HOSPITALIZATION DATA AT " + urlHospitalization + " FOR " + stateAbbreviation);
				hospitalizationDataArray = restTemplate.getForObject(urlHospitalization, HospitalDataCDC[].class);
			} catch (RestClientException e) {
				log.error("RestClientException is: " + e.getMessage());
				log.info("ERROR CONNECTING TO HOSPITAL DATA SOURCE: RETRYING: TRY #" + (tries+1));
				tries++;
				hospitalizationDataArray = null;
			} 
		} while (tries <= 3 && hospitalizationDataArray == null);
		
		VaccinationTimeSeriesCAN vaccArray;
		tries = 0;
		do {
			try {
				StringBuilder urlVacc = new StringBuilder();
				urlVacc
					.append(DataUrls.STATE_DATA_URL_VACCINATIONS_START.getText())
					.append(stateAbbreviation.toUpperCase())
					.append(DataUrls.STATE_DATA_URL_VACCINATIONS_FILE.getText())
					.append(DataUrls.STATE_DATA_URL_VACCINATIONS_END.getText());
				log.debug("VACCINATION DATA AT " + urlVacc.toString() + " FOR " + stateAbbreviation);
				vaccArray = restTemplate.getForObject(urlVacc.toString(), VaccinationTimeSeriesCAN.class);
			} catch (RestClientException e) {
				log.error("RestClientException is: " + e.getMessage());
				log.info("ERROR CONNECTING VACCINATION DATA SOURCE: RETRYING: TRY #" + (tries+1));
				tries++;
				vaccArray = null;
			} 
		} while (tries <= 3 && hospitalizationDataArray == null);
		
		if(stateCaseDeathDataArray == null || hospitalizationDataArray == null || vaccArray == null) {
			log.error("The returned stateCaseDeathDataArray, hospitalizationDataArray, or vaccArray is null!!!");
			return new ArrayList<UnitedStatesData>();
		}
		
		List<UnitedStatesData> stateDataList = buildUnitedStatesDataList(stateCaseDeathDataArray, hospitalizationDataArray, vaccArray);
		log.debug("The size of the pre-filtered state data list for " + stateAbbreviation + " is: " + stateDataList.size());
		
		stateDataList.removeIf(unitedStatesCase -> (unitedStatesCase.getDateInteger() < US_CUTOFF_DATE));
		
		log.debug("FINISHED GETTING DATA FOR STATE: " + stateAbbreviation);
		
		return stateDataList;
	}
	
	private List<UnitedStatesData> buildUnitedStatesDataList(
			CaseDeathDataCDC[] stateCaseDeathDataArray,
			HospitalDataCDC[] hospitalizationDataArray,
			VaccinationTimeSeriesCAN vaccArray) {
		
		Map<Integer, CaseDeathDataCDC> casesAndDeaths = new HashMap<Integer, CaseDeathDataCDC>();
		Map<Integer, HospitalDataCDC> hospitalizations = new HashMap<Integer, HospitalDataCDC>();
		Map<Integer, VaccinationDataCAN> vaccinations = new HashMap<Integer, VaccinationDataCAN>();
		
		for(CaseDeathDataCDC cd : stateCaseDeathDataArray) {
			cd.getDatesCDC().setDateFields(cd.getDateTimeString());
			casesAndDeaths.put(cd.getDatesCDC().getDateAsIntegerYYYYMMDD(), cd);
		}
		
		for(HospitalDataCDC h : hospitalizationDataArray) {
			h.getDatesCDC().setDateFields(h.getDateTimeString());
			hospitalizations.put(h.getDatesCDC().getDateAsIntegerYYYYMMDD(), h);
		}
		
		for(VaccinationDataCAN v : vaccArray.getActualsTimeSeries()) {
			v.getDatesCDC().setDateFields(v.getDateYYYY_MM_DD());
			vaccinations.put(v.getDatesCDC().getDateAsIntegerYYYYMMDD(), v);
		}
		
		return transformIntoUnitedStatesData(casesAndDeaths, hospitalizations, vaccinations);
	}

	/**
	 * Transforms data from disparate sources into United States Data objects
	 * @param casesAndDeaths
	 * @param hospitalizations
	 * @param vaccinations
	 * @return List of UnitedStatesData objects
	 */
	private List<UnitedStatesData> transformIntoUnitedStatesData(
			Map<Integer, CaseDeathDataCDC> casesAndDeaths,
			Map<Integer, HospitalDataCDC> hospitalizations,
			Map<Integer, VaccinationDataCAN> vaccinations) {
		
		List<UnitedStatesData> toReturn = new ArrayList<>();
		
		//Make a list of dates from the cases and deaths data sources as the master dates
		Integer[] datesArray = casesAndDeaths.keySet().toArray(new Integer[0]);
		Arrays.parallelSort(datesArray);
		List<Integer> allCaseDates = Arrays.asList(datesArray);
		
		//Transform the data from the maps into the desired object		
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
			if(vaccinations.containsKey(caseDate)) {
				usd.setTotalVaccCompleted(vaccinations.get(caseDate).getVaccComp());
			}
			toReturn.add(usd);
		}
		
		return toReturn;
	}
}
