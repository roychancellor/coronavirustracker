package com.royware.corona.dashboard.enums;

public enum CacheKeys {
	CACHE_KEY_WORLD("EURO_CDC"),
	CACHE_KEY_US("COVID_TRACKING");
	
	public final String name;
	
	private CacheKeys(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return this.name;
	}
}
