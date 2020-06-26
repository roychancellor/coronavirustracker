package com.royware.corona.dashboard.config;

import java.util.Arrays;
import java.util.concurrent.Executor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.web.client.RestTemplate;

import com.royware.corona.dashboard.interfaces.data.ExternalDataService;
import com.royware.corona.dashboard.services.data.ExternalDataServiceWorldImpl;

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
		return new ExternalDataServiceWorldImpl();
	}
	
	@Bean
	public RestTemplate makeRestTemplate() {
		RestTemplate restTemplate = new RestTemplate(getClientHttpRequestFactory());
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
	
	//Override timeouts in request factory
	@Autowired
	private Environment env;
	
	private HttpComponentsClientHttpRequestFactory getClientHttpRequestFactory() 
	{	    
		HttpComponentsClientHttpRequestFactory clientHttpRequestFactory
	                      = new HttpComponentsClientHttpRequestFactory();
	    //Connect timeout
	    clientHttpRequestFactory.setConnectTimeout(Integer.parseInt(env.getProperty("spring.external.connect.timeout")));
	     
	    //Read timeout
	    clientHttpRequestFactory.setReadTimeout(Integer.parseInt(env.getProperty("spring.external.read.timeout")));
	    return clientHttpRequestFactory;
	}
}
