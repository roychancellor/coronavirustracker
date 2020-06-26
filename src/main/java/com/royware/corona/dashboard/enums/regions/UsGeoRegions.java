package com.royware.corona.dashboard.enums.regions;

import java.util.HashMap;
import java.util.Map;

public enum UsGeoRegions {
	FAR_WEST("Far West"),
	SOUTHWEST("Southwest"),
	ROCKY_MOUNTAIN("Rocky Mountain"),
	PLAINS("Plains"),
	GREAT_LAKES("Great Lakes"),
	SOUTHEAST("Southeast"),
	MIDEAST("Mideast"),
	NEW_ENGLAND("New England");
	
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
	
	private UsGeoRegions(String label) {
		this.label = label;
	}
	
	public String getLabel() {
		return this.label;
	}
	
	public static UsGeoRegions valueOfLabel(String label) {
		for(UsGeoRegions e : values()) {
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
