package com.royware.corona.dashboard.enums;

import java.util.HashMap;
import java.util.Map;

public enum GeographicalRegions {
	FAR_WEST("far_west"),
	SOUTHWEST("southwest"),
	ROCKY_MOUNTAIN("rocky_mountain"),
	PLAINS("plains"),
	GREAT_LAKES("great_lakes"),
	SOUTHEAST("southeast"),
	MIDEAST("mideast"),
	NEW_ENGLAND("new_england");
	
	private final String label;
	
	private static final Map<String, String> REGION_TO_STATES = new HashMap<>();
	
	static {
		REGION_TO_STATES.put(FAR_WEST.getLabel(), "WA,OR,CA,NV");
		REGION_TO_STATES.put(SOUTHWEST.getLabel(), "AZ,NM,OK,TX");
		REGION_TO_STATES.put(ROCKY_MOUNTAIN.getLabel(), "MT,ID,WY,UT,CO");
		REGION_TO_STATES.put(PLAINS.getLabel(), "ND,SD,NE,KS,MN,IA,MO");
		REGION_TO_STATES.put(GREAT_LAKES.getLabel(), "WI,IL,MI,IN,OH");
		REGION_TO_STATES.put(SOUTHEAST.getLabel(), "AR,LA,KY,TN,MS,AL,WV,VA,NC,SC,GA,FL");
		REGION_TO_STATES.put(MIDEAST.getLabel(), "NY,PA,NJ,DE,MD,DC");
		REGION_TO_STATES.put(NEW_ENGLAND.getLabel(), "ME,NH,VT,MA,CT,RI");
	}
	
	private GeographicalRegions(String label) {
		this.label = label;
	}
	
	public String getLabel() {
		return this.label;
	}
	
	public GeographicalRegions valueOfLabel(String label) {
		for(GeographicalRegions e : values()) {
			if(label.equalsIgnoreCase(e.label)) {
				return e;
			}
		}
		return null;
	}
	
	public String getStatesInRegion(String region) {
		if(REGION_TO_STATES.containsKey(region)) {
			return REGION_TO_STATES.get(region);
		}
		return null;
	}
}
