package com.royware.corona.dashboard;

import java.util.Arrays;

import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.royware.corona.dashboard.interfaces.ChartListDataService;
import com.royware.corona.dashboard.services.ChartListDataServiceImpl;

@Service
public class DashboardControllerBeans {
	@Bean
	public RestTemplate makeRestTemplate() {
		return new RestTemplate();
	}
	
	@Bean
	public ChartListDataService dataService() {
		return new ChartListDataServiceImpl(makeRestTemplate());
	}
	
	@Bean
    public CacheManager cacheManager() {
        // configure and return an implementation of Spring's CacheManager SPI
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        cacheManager.setCaches(Arrays.asList(new ConcurrentMapCache("dataCache")));
        return cacheManager;
    }
	
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
