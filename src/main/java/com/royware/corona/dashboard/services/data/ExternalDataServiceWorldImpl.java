package com.royware.corona.dashboard.services.data;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.royware.corona.dashboard.enums.data.DataUrls;
import com.royware.corona.dashboard.interfaces.data.ExternalDataService;
import com.royware.corona.dashboard.interfaces.data.WorldDataServiceCaller;
import com.royware.corona.dashboard.model.data.WorldData;
import com.royware.corona.dashboard.model.data.WorldDataSourceEuroCDC;
import com.royware.corona.dashboard.model.data.WorldDataSourceOurWorldInData;

public class ExternalDataServiceWorldImpl implements ExternalDataService, WorldDataServiceCaller {
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private Environment env;
		
	private ConcurrentMapCache cacheManager;
	private static final Logger log = LoggerFactory.getLogger(ExternalDataServiceWorldImpl.class);

	//Pull data directly from the cache always
	@SuppressWarnings("unchecked")
	@Override
	public List<WorldData> makeDataListFromExternalSource(String cacheKey) {
		cacheManager = CacheManagerProvider.getManager();
		List<WorldData> worldData = (List<WorldData>)cacheManager.get(cacheKey).get();
		if(worldData == null || worldData.isEmpty()) {
			log.info("Getting the world data from its source, then putting it into the cache.");
			worldData = getDataFromWorldSource();
			cacheManager.put(cacheKey, worldData);
		}
		log.info("Returning the cached version of the world data.");
		return worldData;
	}

	//This only gets called when the cache is initialized and when it is evicted/refreshed
	@Override
	public List<WorldData> getDataFromWorldSource() {
		String dataSource = env.getProperty("spring.world.data.source", "EURO_CDC");
		log.info("The spring.world.data.source is: " + dataSource);
		List<WorldData> worldData = new ArrayList<>();
		
		log.info("***** ABOUT TO HIT ENDPOINT FOR ALL WORLD DATA *****");
		if(dataSource.toUpperCase().contains("EURO")) {
			worldData = getDataFromEuroCDC();
		} else if(dataSource.toUpperCase().contains("OWID")) {
			worldData = getDataFromOurWorldInData();
		}
		log.info("***** FINISHED HITTING ENDPOINT FOR ALL WORLD DATA *****");
		return worldData;
	}
	
	private List<WorldData> getDataFromEuroCDC() {
		WorldDataSourceEuroCDC worldData = null;
		
		int tries = 0;
		do {	
			try {
				worldData = restTemplate.getForObject(DataUrls.WORLD_DATA_URL_EUROCDC.getName(), WorldDataSourceEuroCDC.class);
				log.info("***** GOT THROUGH PARSING ALL WORLD DATA FROM EURO CDC *****");
			} catch (RestClientException e) {
				log.error("RestClientException is: " + e.getMessage());
				log.info("*** ERROR CONNECTING TO WORLD DATA SOURCE: RETRYING: TRY #" + (tries+1) + " ***");
				tries++;
				worldData = null;
			}
		} while (tries <= 3 && worldData == null);
		
		if(worldData == null) {
			log.error("The returned worldData list is null!!!");
			return new ArrayList<WorldData>();
		}
		return Arrays.asList(worldData.getRecords());
	}
	
	private List<WorldData> getDataFromOurWorldInData() {
		Map<String, List<WorldDataSourceOurWorldInData>> worldData;
		ObjectMapper mapper = new ObjectMapper();
		
		int tries = 0;
		do {
			try {
				URL jsonUrl = new URL(DataUrls.WORLD_DATA_URL_OWID.getName());
				TypeReference<LinkedHashMap<String, List<WorldDataSourceOurWorldInData>>> tr = new TypeReference<LinkedHashMap<String, List<WorldDataSourceOurWorldInData>>>() {
				};
				worldData = mapper.readValue(jsonUrl, tr);
				log.info("***** GOT THROUGH PARSING ALL WORLD DATA FROM OUR WORLD IN DATA *****");
			} catch (JsonParseException e) {
				log.info("*** ERROR PARSING JSON ***");
				tries++;
				worldData = null;
				e.printStackTrace();
			} catch (JsonMappingException e) {
				log.info("*** ERROR MAPPING JSON ***");
				tries++;
				worldData = null;
				e.printStackTrace();
			} catch (IOException e) {
				log.info("*** ERROR CONNECTING TO WORLD DATA SOURCE: RETRYING: TRY #" + (tries+1) + " ***");
				tries++;
				worldData = null;
				e.printStackTrace();
			}
		} while (tries <= 3 && worldData == null);
		
		if(worldData == null) {
			log.error("The returned worldData list is null!!!");
			return new ArrayList<WorldData>();
		}
		log.info("worldData is of type: " + worldData.getClass().getSimpleName());
		log.info("worldData.get(\"USA\") is of type: " + worldData.get("USA").getClass().getSimpleName());
		log.info("Now going to make worldDataListFromMap...");
		return worldDataListFromMap(worldData);
	}
	
	private List<WorldData> worldDataListFromMap(Map<String, List<WorldDataSourceOurWorldInData>> worldDataMap) {
		List<WorldData> worldDataList = new ArrayList<>();
		
		for(String countryKey : worldDataMap.keySet()) {
			for(WorldDataSourceOurWorldInData wr : worldDataMap.get(countryKey)) {
				worldDataList.add(worldDataFromWorldRecord(wr, countryKey));
			}
		}
		
		return worldDataList;
	}
	
	private WorldData worldDataFromWorldRecord(WorldDataSourceOurWorldInData wr, String countryKey) {
		WorldData wd = new WorldData();
		wd.setTotalDeaths((int)wr.getTotalDeaths());
		wd.setTotalPositiveCases((int)wr.getTotalCases());
		wd.setStringDate(wr.getDateStringYYYY_MM_DD().replace("-",  "/"));
		wd.setYear(Integer.parseInt(wr.getDateStringYYYY_MM_DD().substring(0,4)));
		wd.setMonth(Integer.parseInt(wr.getDateStringYYYY_MM_DD().substring(5,7)));
		wd.setDay(Integer.parseInt(wr.getDateStringYYYY_MM_DD().substring(8)));
		wd.setDateChecked(wd.getYear(), wd.getMonth(), wd.getDay());
		wd.setDailyNewCases((int)wr.getNewCases());
		wd.setDailyNewDeaths((int)wr.getNewDeaths());
		wd.setRegionString(countryKey);
		wd.setPopulation(wr.getPopulation());
		
		return wd;
	}
}