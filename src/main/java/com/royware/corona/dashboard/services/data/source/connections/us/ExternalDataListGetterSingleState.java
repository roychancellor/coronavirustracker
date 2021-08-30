package com.royware.corona.dashboard.services.data.source.connections.us;

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
import com.royware.corona.dashboard.interfaces.data.IExternalDataListGetter;
import com.royware.corona.dashboard.model.data.us.CaseDeathVaccData_CovidActNow;
import com.royware.corona.dashboard.model.data.us.CaseDeathVaccTimeSeries_CovActNow;
import com.royware.corona.dashboard.model.data.us.HospitalDataCDC;
import com.royware.corona.dashboard.model.data.us.UnitedStatesData;

/**
 * Provides service methods for getting dashboard data from external sources
 */
@Component("singleState")
public class ExternalDataListGetterSingleState implements IExternalDataListGetter {
	@Autowired
	private RestTemplate restTemplate;
	
	private static final Logger log = LoggerFactory.getLogger(ExternalDataListGetterSingleState.class);
	
	private boolean toCleanNegativeChangesFromTotals;
	
	@Override
	public void setCleanNegativeChangesFromTotals(boolean cleanNegativeChangesFromTotals) {
		this.toCleanNegativeChangesFromTotals = cleanNegativeChangesFromTotals;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UnitedStatesData> makeDataListFromExternalSource(String stateAbbreviation) {
		
		HospitalDataCDC[] hospitalizationDataArray;
		int tries = 0;
		do {
			try {
				String urlHospitalization = DataUrls.STATE_DATA_URL_HOSPITAL_LOAD_START.getText()
						+ DataUrls.STATE_DATA_URL_HOSPITAL_LOAD_CDC.getText() + DataUrls.STATE_DATA_URL_END.getText()
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
		
		CaseDeathVaccTimeSeries_CovActNow caseDeathVaccArray;
		tries = 0;
		do {
			try {
				StringBuilder urlCaseDeathVacc = new StringBuilder();
				urlCaseDeathVacc
					.append(DataUrls.STATE_DATA_URL_CASE_DEATH_VACC_START.getText())
					.append(stateAbbreviation.toUpperCase())
					.append(DataUrls.STATE_DATA_URL_CASE_DEATH_VACC_FILE.getText())
					.append(DataUrls.STATE_DATA_URL_CASE_DEATH_VACC_END.getText());
				log.debug("CASE/DEATH/VACCINATION DATA AT " + urlCaseDeathVacc.toString() + " FOR " + stateAbbreviation);
				caseDeathVaccArray = restTemplate.getForObject(urlCaseDeathVacc.toString(), CaseDeathVaccTimeSeries_CovActNow.class);
			} catch (RestClientException e) {
				log.error("RestClientException is: " + e.getMessage());
				log.info("ERROR CONNECTING CASE/DEATH/VACCINATION DATA SOURCE: RETRYING: TRY #" + (tries+1));
				tries++;
				caseDeathVaccArray = null;
			} 
		} while (tries <= 3 && caseDeathVaccArray == null);
		
		if(hospitalizationDataArray == null || caseDeathVaccArray == null) {
			log.error("One of the returned arrays is null!!!");
			return new ArrayList<UnitedStatesData>();
		}
		
		List<UnitedStatesData> stateDataList = buildUnitedStatesDataList(stateAbbreviation, hospitalizationDataArray, caseDeathVaccArray);
		log.debug("The size of the pre-filtered state data list for " + stateAbbreviation + " is: " + stateDataList.size());
		
		stateDataList.removeIf(unitedStatesCase -> (unitedStatesCase.getDateInteger() < US_CUTOFF_DATE));
		
		log.debug("FINISHED GETTING DATA FOR STATE: " + stateAbbreviation);
		
		return stateDataList;
	}
	
	private List<UnitedStatesData> buildUnitedStatesDataList(
			String stateAbbrev,
			HospitalDataCDC[] hospitalizationDataArray,
			CaseDeathVaccTimeSeries_CovActNow caseDeathVaccArray) {
		
		Map<Integer, CaseDeathVaccData_CovidActNow> caseDeathVacc = new HashMap<Integer, CaseDeathVaccData_CovidActNow>();
		Map<Integer, HospitalDataCDC> hospitalizations = new HashMap<Integer, HospitalDataCDC>();
		
		for(CaseDeathVaccData_CovidActNow cdv : caseDeathVaccArray.getActualsTimeSeries()) {
			cdv.getDatesCDC().setDateFields(cdv.getDateYYYY_MM_DD());
			caseDeathVacc.put(cdv.getDatesCDC().getDateAsIntegerYYYYMMDD(), cdv);
		}
		
		for(HospitalDataCDC h : hospitalizationDataArray) {
			h.getDatesCDC().setDateFields(h.getDateTimeString());
			hospitalizations.put(h.getDatesCDC().getDateAsIntegerYYYYMMDD(), h);
		}
		
		return transformIntoUnitedStatesData(stateAbbrev, hospitalizations, caseDeathVacc);
	}

	/**
	 * Transforms data from disparate sources into United States Data objects
	 * @param casesAndDeaths
	 * @param hospitalizations
	 * @param caseDeathVacc
	 * @return List of UnitedStatesData objects
	 */
	private List<UnitedStatesData> transformIntoUnitedStatesData(
			String stateAbbrev,
			Map<Integer, HospitalDataCDC> hospitalizations,
			Map<Integer, CaseDeathVaccData_CovidActNow> caseDeathVacc) {
		
		List<UnitedStatesData> toReturn = new ArrayList<>();
		
		//Make a list of dates from the cases and deaths data sources as the master dates
		Integer[] datesArray = caseDeathVacc.keySet().toArray(new Integer[0]);
		Arrays.parallelSort(datesArray);
		List<Integer> allCaseDates = Arrays.asList(datesArray);
		
		//Transform the data from the maps into the desired object		
		UnitedStatesData usd = makeUSDataObject(stateAbbrev, hospitalizations, caseDeathVacc, allCaseDates.get(0));
		int totalCasesYesterday = usd.getTotalPositiveCases();
		int totalDeathsYesterday = usd.getTotalDeaths();
		int totalVaccYesterday = usd.getTotalVaccCompleted();
		
		int anchorCases = 0;
		int anchorDeaths = 0;
		int anchorVacc = 0;
		boolean compareToAnchorCases = false;
		boolean compareToAnchorDeaths = false;
		boolean compareToAnchorVacc = false;
				
		for(Integer caseDate : allCaseDates) {
			if(caseDeathVacc.get(caseDate).getTotalCases() < 100) {
				continue;
			}
			usd = makeUSDataObject(stateAbbrev, hospitalizations, caseDeathVacc, caseDate);
			
			// CLEAN NEGATIVE CHANGES IN TOTALS FOR CASES, DEATHS, VACC, AND HOSP
			// This works by setting the anchor value to the last good value, then continuing
			// through the loop until the daily change in total value becomes positive.
			if(toCleanNegativeChangesFromTotals) {
				int totalCasesToday = usd.getTotalPositiveCases();
				int totalDeathsToday = usd.getTotalDeaths();
				int totalVaccToday = usd.getTotalVaccCompleted();
				int changeInCases = totalCasesToday - (compareToAnchorCases ? anchorCases : totalCasesYesterday);
				int changeInDeaths = totalDeathsToday - (compareToAnchorDeaths ? anchorDeaths : totalDeathsYesterday);
				int changeInVacc = totalVaccToday - (compareToAnchorVacc ? anchorVacc : totalVaccYesterday);
				
				// Only add to the list if all three changes are positive
				if(changeInCases < 0) {
					anchorCases = compareToAnchorCases ? anchorCases : totalCasesYesterday;
					compareToAnchorCases = true;
					continue;
				}
				else {
					compareToAnchorCases = false;
					totalCasesYesterday = totalCasesToday;
				}
				
				if(changeInDeaths < 0) {
					anchorDeaths = compareToAnchorDeaths ? anchorDeaths : totalDeathsYesterday;
					compareToAnchorDeaths = true;
					continue;
				}
				else {
					compareToAnchorDeaths = false;
					totalDeathsYesterday = totalDeathsToday;
				}
				
				if(changeInVacc < 0) {
					anchorVacc = compareToAnchorVacc ? anchorVacc : totalVaccYesterday;
					compareToAnchorVacc = true;
					continue;
				}
				else {
					compareToAnchorVacc = false;
					totalVaccYesterday = totalVaccToday;
				}
			}			
			
			
			toReturn.add(usd);
		}
		
		return toReturn;
	}

	private UnitedStatesData makeUSDataObject(
			String stateAbbrev,
			Map<Integer, HospitalDataCDC> hospitalizations,
			Map<Integer, CaseDeathVaccData_CovidActNow> caseDeathVacc,
			Integer caseDate) {
		UnitedStatesData usd = new UnitedStatesData();
		usd.setDateChecked(caseDeathVacc.get(caseDate).getDatesCDC().getDateAsLocalDate());
		usd.setDateInteger(caseDate);
		usd.setDateTimeString(caseDeathVacc.get(caseDate).getDatesCDC().getDateAsStringYYYYMMDD());
		usd.setRegionString(stateAbbrev);
		usd.setTotalPositiveCases(caseDeathVacc.get(caseDate).getTotalCases());
		usd.setTotalDeaths(caseDeathVacc.get(caseDate).getTotalDeaths());
		if(caseDeathVacc.get(caseDate).getVaccComp() >= 100) {
			usd.setTotalVaccCompleted(caseDeathVacc.get(caseDate).getVaccComp());
		}
		if(hospitalizations.containsKey(caseDate)) {
			usd.setHospitalizedCurrently(hospitalizations.get(caseDate).getTotalBedsCovidCurrently());
		}
		return usd;
	}

	
}
