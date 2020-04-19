package com.royware.corona.dashboard.beans;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.royware.corona.dashboard.interfaces.ExternalDataService;
import com.royware.corona.dashboard.services.SingleCountryDataServiceImpl;
import com.royware.corona.dashboard.services.SingleStateDataServiceImpl;
import com.royware.corona.dashboard.services.UsDataServiceImpl;
import com.royware.corona.dashboard.services.UsExcludingStateDataServiceImpl;
import com.royware.corona.dashboard.services.WorldDataServiceImpl;

@Service
public class ApplicationBeans {
	@Bean
	public RestTemplate makeRestTemplate() {
		return new RestTemplate();
	}
	
	@Bean
	@Qualifier("us")
	public ExternalDataService dataServiceUs() {
		return new UsDataServiceImpl();
	}
	
	@Bean
	@Qualifier("world")
	public ExternalDataService dataServiceWorld() {
		return new WorldDataServiceImpl();
	}
	
	@Bean
	@Qualifier("singleState")
	public ExternalDataService dataServiceSingleState() {
		return new SingleStateDataServiceImpl();
	}
	
	@Bean
	@Qualifier("usExcludingState")
	public ExternalDataService dataServiceUsExcludingState() {
		return new UsExcludingStateDataServiceImpl();
	}
	
	@Bean
	@Qualifier("singleCountry")
	public ExternalDataService dataServiceSingleCountry() {
		return new SingleCountryDataServiceImpl();
	}
	
	@Bean
    public CacheManager cacheManager() {
        // configure and return an implementation of Spring's CacheManager SPI
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        cacheManager.setCaches(Arrays.asList(new ConcurrentMapCache("dataCache")));
        return cacheManager;
    }
}
