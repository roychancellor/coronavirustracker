package com.royware.corona.dashboard.interfaces.dashboard;

import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

@Service
public interface DashboardConfigService {
	public static String ALL_STATES_AS_CSV = "AL,AK,AZ,AR,CA,CO,CT,DE,FL,GA,HI,ID,IL,IN,IA,KS,KY,LA,ME,MD,MA,MI,MN,MS,MO,MT,NE,NV,NH,NJ,NM,NY,NC,ND,OH,OK,OR,PA,RI,SC,SD,TN,TX,UT,VT,VA,WA,WV,WI,WY";
	
	public boolean populateDashboardModelMap(String region, ModelMap map);
}
