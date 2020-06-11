package com.royware.corona.dashboard.config;

import java.util.Arrays;
import java.util.concurrent.Executor;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.web.client.RestTemplate;

import com.royware.corona.dashboard.interfaces.data.ExternalDataService;
import com.royware.corona.dashboard.services.data.WorldDataServiceImpl;

@Configuration
@PropertySource({"classpath:application-${ENVIRONMENT}.properties"})
@EnableScheduling
@EnableAsync
@ComponentScan(basePackages = "com.royware.corona.dashboard")
public class ApplicationConfig {
	@Bean("worldDataService")
	@Qualifier("world")
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
	public ExternalDataService worldDataService() {
		return new WorldDataServiceImpl();
	}
	
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
    public TaskScheduler taskScheduler() {
        return new ConcurrentTaskScheduler();
    }    
	
	@Bean
    public Executor taskExecutor() {
        return new SimpleAsyncTaskExecutor();
    }	
}
