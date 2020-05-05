package com.royware.corona.dashboard.services.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.royware.corona.dashboard.enums.DataUrls;
import com.royware.corona.dashboard.interfaces.ExternalDataService;
import com.royware.corona.dashboard.model.UnitedStatesCases;

public class UsDataServiceImpl implements ExternalDataService {
	@Autowired
	private RestTemplate restTemplate;
	
	private static final Logger log = LoggerFactory.getLogger(UsDataServiceImpl.class);

	/**
	 * Gets all US data and returns it as an array of objects
	 * @return JSON array of UnitedStatesData objects
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<UnitedStatesCases> makeDataListFromExternalSource(String cacheKey) {
		UnitedStatesCases[] usDataArray = null;
		int tries = 0;
		do {
			try {
				String url = DataUrls.US_DATA_URL.getName();
				log.info("***** ABOUT TO HIT ENDPOINT FOR UNITED STATES DATA AT URL " + url);
				usDataArray = restTemplate.getForObject(url, UnitedStatesCases[].class);
				log.info("***** GOT THROUGH PARSING UNITED STATES DATA *****");
			} catch(RestClientException e) {
				log.info("*** ERROR CONNECTING TO U.S. DATA SOURCE: RETRYING: TRY #" + (tries+1) + " ***");
				e.printStackTrace();
				usDataArray = null;
				tries++;
			} catch(Exception e) {
				log.info("The restTemplate threw an exception unexpectedly:");
				e.printStackTrace();
				tries++;
			}
		} while(tries <= 3 && usDataArray == null);
		
		List<UnitedStatesCases> usDataList = new ArrayList<>(Arrays.asList(usDataArray));
		Collections.reverse(usDataList);
		usDataList.removeIf(unitedStatesCase -> (unitedStatesCase.getDateInteger() < US_CUTOFF_DATE));
		
		log.info("***** FINISHED HITTING ENDPOINT FOR UNITED STATES DATA *****");
		return usDataList;
	}
}