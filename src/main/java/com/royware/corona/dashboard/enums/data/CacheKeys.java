package com.royware.corona.dashboard.enums.data;

public enum CacheKeys {
	CACHE_KEY_WORLD("EURO_CDC"),
	CACHE_KEY_US("US_CDC");
	
	public final String name;
	
	private CacheKeys(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
}
