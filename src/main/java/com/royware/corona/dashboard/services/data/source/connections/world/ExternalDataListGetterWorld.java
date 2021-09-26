package com.royware.corona.dashboard.services.data.source.connections.world;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.royware.corona.dashboard.enums.data.CacheKeys;
import com.royware.corona.dashboard.enums.data.DataTransformConstants;
import com.royware.corona.dashboard.enums.data.DataUrls;
import com.royware.corona.dashboard.interfaces.data.IExternalDataListGetter;
import com.royware.corona.dashboard.interfaces.data.IWorldDataServiceCaller;
import com.royware.corona.dashboard.model.data.world.WorldData;
import com.royware.corona.dashboard.model.data.world.WorldDataOWID;
import com.royware.corona.dashboard.model.data.world.WorldDataSourceEuroCDC;
import com.royware.corona.dashboard.model.data.world.WorldDataSourceOurWorldInData;
import com.royware.corona.dashboard.services.data.cache.CacheManagerProvider;

@Component("singleCountry")
public class ExternalDataListGetterWorld implements IExternalDataListGetter, IWorldDataServiceCaller {
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private Environment env;
		
	private ConcurrentMapCache cacheManager;
	private static final Logger log = LoggerFactory.getLogger(ExternalDataListGetterWorld.class);

	@SuppressWarnings("unused")
	private boolean toCleanNegativeChangesFromTotals = false;
	
	@Override
	public void setCleanNegativeChangesFromTotals(boolean cleanNegativeChangesFromTotals) {
		this.toCleanNegativeChangesFromTotals = cleanNegativeChangesFromTotals;
	}
	
	//Pull data directly from the cache always
	@SuppressWarnings("unchecked")
	@Override
	public List<WorldData> makeDataListFromExternalSource(String regionString) {
		String cacheKey = CacheKeys.CACHE_KEY_WORLD.getName();
		cacheManager = CacheManagerProvider.getManager();
		List<WorldData> allCountriesInWorldData = safeGetDataFromCache(cacheKey);
		if(allCountriesInWorldData == null || allCountriesInWorldData.isEmpty()) {
			log.info("World data not in cache with key {}. Getting the world data from its source.", cacheKey);
			allCountriesInWorldData = getDataFromWorldSource();
		} else {
			log.info("Returning the cached version of the world data.");
		}
		return filterByCountry(allCountriesInWorldData, regionString);
	}
	
	@SuppressWarnings("unchecked")
	private List<WorldData> safeGetDataFromCache(String cacheKey) {
		ValueWrapper springCacheValueWrapper = cacheManager.get(cacheKey);
		if(springCacheValueWrapper == null) {
			return null;
		}
		return (List<WorldData>)springCacheValueWrapper.get();
	}
	
	private List<WorldData> filterByCountry(List<WorldData> allWorldData, String country){
		log.info("Filtering the all country world data list for country: {}", country);
		return allWorldData
			.stream()
			.filter(wc -> {
					if(wc.getRegionString() == null || wc.getDailyNewCases() < 0) {
						log.error("In the .filter, region string is null or daily new cases < 0");
						return false;
					}
					return wc.getRegionString().equalsIgnoreCase(country) &&
						   wc.getDailyNewCases() >= DataTransformConstants.MINIMUM_NUMBER_OF_DAILY_CASES_FOR_INCLUSION.getValue();
				})
			.collect(Collectors.toList());
	}

	//This only gets called when the cache is initialized and when it is evicted/refreshed
	@Override
	public List<WorldData> getDataFromWorldSource() {
		String dataSource = env.getProperty("spring.world.data.source", "EURO_CDC");
		log.info("The spring.world.data.source is: " + dataSource);
		List<WorldData> worldData = new ArrayList<>();
		
		log.info("HITTING ENDPOINT FOR ALL WORLD DATA");
		if(dataSource.toUpperCase().contains("EURO")) {
			worldData = getDataFromEuroCDC();
		} else if(dataSource.toUpperCase().contains("OWID")) {
			worldData = getDataFromOurWorldInData();
		}
		log.info("FINISHED HITTING ENDPOINT FOR ALL WORLD DATA (worldData size: " + worldData.size() +  ")");
		return worldData;
	}
	
	private List<WorldData> getDataFromEuroCDC() {
		WorldDataSourceEuroCDC worldData = null;
		
		int tries = 0;
		do {	
			try {
				worldData = restTemplate.getForObject(DataUrls.WORLD_DATA_URL_EUROCDC.getText(), WorldDataSourceEuroCDC.class);
				log.info("GOT THROUGH PARSING ALL WORLD DATA FROM EURO CDC *****");
			} catch (RestClientException e) {
				log.error("RestClientException is: " + e.getMessage());
				log.info("ERROR CONNECTING TO WORLD DATA SOURCE: RETRYING: TRY #" + (tries+1) + " ***");
				tries++;
				worldData = null;
			}
		} while (tries <= 3 && worldData == null);
		
		if(worldData == null) {
			log.error("The returned worldData list is null!!!");
			return new ArrayList<WorldData>();
		}
		log.info("The size of the raw world data list is: " + worldData.getRecords().length);
		List<WorldData> toReturn = Arrays.asList(worldData.getRecords());
		Collections.reverse(toReturn);
		return toReturn;
	}
	
	private List<WorldData> getDataFromOurWorldInData() {
		Map<String, WorldDataSourceOurWorldInData> worldData;
		ObjectMapper mapper = new ObjectMapper();
		
		int tries = 0;
		do {
			try {
				URL jsonUrl = new URL(DataUrls.WORLD_DATA_URL_OWID.getText());
				TypeReference<LinkedHashMap<String, WorldDataSourceOurWorldInData>> tr =
						new TypeReference<LinkedHashMap<String, WorldDataSourceOurWorldInData>>() {/*do nothing*/};
				worldData = mapper.readValue(jsonUrl, tr);
				log.info("GOT THROUGH PARSING ALL WORLD DATA FROM OUR WORLD IN DATA *****");
			} catch (JsonParseException e) {
				log.info("ERROR PARSING JSON");
				tries++;
				worldData = null;
				e.printStackTrace();
			} catch (JsonMappingException e) {
				log.info("ERROR MAPPING JSON");
				tries++;
				worldData = null;
				e.printStackTrace();
			} catch (IOException e) {
				log.info("ERROR CONNECTING TO WORLD DATA SOURCE: RETRYING: TRY #" + (tries+1));
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
	
	private List<WorldData> worldDataListFromMap(Map<String, WorldDataSourceOurWorldInData> worldDataMap) {
		List<WorldData> worldDataList = new ArrayList<>();
		
		//Iterate through all country codes (map keys) and create a list of WorldData objects
		for(String countryKey : worldDataMap.keySet()) {
			long pop = worldDataMap.get(countryKey).getPopulation();
			for(WorldDataOWID owid : worldDataMap.get(countryKey).getData()) {
				WorldData wd = worldDataFromWorldDataSourceOwid(countryKey, owid);
				wd.setRegionString(countryKey);
				wd.setPopulation(pop);
				worldDataList.add(wd);
			}
		}
		
		return worldDataList;
	}
	
	private WorldData worldDataFromWorldDataSourceOwid(String countryKey, WorldDataOWID owid) {
		WorldData wd = new WorldData();
		wd.setTotalDeaths((int)owid.getTotalDeaths());
		wd.setTotalPositiveCases((int)owid.getTotalCases());
		wd.setStringDate(owid.getDateStringYYYY_MM_DD().replace("-",  "/"));
		wd.setYear(Integer.parseInt(owid.getDateStringYYYY_MM_DD().substring(0,4)));
		wd.setMonth(Integer.parseInt(owid.getDateStringYYYY_MM_DD().substring(5,7)));
		wd.setDay(Integer.parseInt(owid.getDateStringYYYY_MM_DD().substring(8)));
		wd.setDateChecked(wd.getYear(), wd.getMonth(), wd.getDay());
		wd.setDailyNewCases((int)owid.getNewCases());
		wd.setDailyNewDeaths((int)owid.getNewDeaths());
		
		return wd;
	}
}
