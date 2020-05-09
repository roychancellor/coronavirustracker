package com.royware.corona.dashboard.beans;

import java.util.Arrays;
import java.util.concurrent.Executor;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.web.client.RestTemplate;

import com.royware.corona.dashboard.interfaces.ExternalDataService;
import com.royware.corona.dashboard.services.data.MultiStateDataServiceImpl;
import com.royware.corona.dashboard.services.data.SingleCountryDataServiceImpl;
import com.royware.corona.dashboard.services.data.SingleStateDataServiceImpl;
import com.royware.corona.dashboard.services.data.UsDataServiceImpl;
import com.royware.corona.dashboard.services.data.UsExcludingStateDataServiceImpl;
import com.royware.corona.dashboard.services.data.WorldDataServiceImpl;

@Configuration
@EnableScheduling
@EnableAsync
@ComponentScan("com.royware.corona")
public class ApplicationBeans {
	@Bean
	public RestTemplate makeRestTemplate() {
		RestTemplate restTemplate = new RestTemplate();
		MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
		mappingJackson2HttpMessageConverter.setSupportedMediaTypes(
				Arrays.asList(MediaType.APPLICATION_JSON, MediaType.TEXT_HTML, MediaType.TEXT_PLAIN));
		restTemplate.getMessageConverters().add(mappingJackson2HttpMessageConverter);
		return restTemplate;
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
	@Qualifier("multiState")
	public ExternalDataService dataServiceMultiState() {
		return new MultiStateDataServiceImpl();
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
	
    @Bean
    public TaskScheduler taskScheduler() {
        return new ConcurrentTaskScheduler();
    }    
	
	@Bean
    public Executor taskExecutor() {
        return new SimpleAsyncTaskExecutor();
    }	
}
