package com.royware.corona.dashboard.enums;

import java.util.List;

import com.royware.corona.dashboard.interfaces.CanonicalCases;
import com.royware.corona.dashboard.interfaces.ChartListDataService;
import com.royware.corona.dashboard.model.RegionData;
import com.royware.corona.dashboard.model.UnitedStatesCases;
import com.royware.corona.dashboard.model.WorldCases;

public enum Regions {
	USA {
		@Override
		public RegionData getRegionData() {
			return new RegionData(this, 328200000, RegionLevels.COUNTRY, "United States");
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<UnitedStatesCases> getRegionDataList(ChartListDataService dataService, String cacheKey) {
			return dataService.getAllUsData("COVID_TRACKING");
		}
	},
	ITA {
		public RegionData getRegionData() {
			return new RegionData(this, 60360000, RegionLevels.COUNTRY, "Italy");
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<WorldCases> getRegionDataList(ChartListDataService dataService, String region) {
			return dataService.getSingleNonUsCountryData(this.name());
		}
	},
	DEU {
		public RegionData getRegionData() {
			return new RegionData(this, 83020000, RegionLevels.COUNTRY, "Germany");
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<WorldCases> getRegionDataList(ChartListDataService dataService, String region) {
			return dataService.getSingleNonUsCountryData(this.name());
		}
	},
	AUS {
		public RegionData getRegionData() {
			return new RegionData(this, 24990000, RegionLevels.COUNTRY, "Australia");
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<WorldCases> getRegionDataList(ChartListDataService dataService, String region) {
			return dataService.getSingleNonUsCountryData(this.name());
		}
	},
	CAN {
		public RegionData getRegionData() {
			return new RegionData(this, 37590000, RegionLevels.COUNTRY, "Canada");
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<WorldCases> getRegionDataList(ChartListDataService dataService, String region) {
			return dataService.getSingleNonUsCountryData(this.name());
		}
	},
	CHN {
		public RegionData getRegionData() {
			return new RegionData(this, 1393000000, RegionLevels.COUNTRY, "China");
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<WorldCases> getRegionDataList(ChartListDataService dataService, String region) {
			return dataService.getSingleNonUsCountryData(this.name());
		}
	},
	JPN {
		public RegionData getRegionData() {
			return new RegionData(this, 126500000, RegionLevels.COUNTRY, "Japan");
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<WorldCases> getRegionDataList(ChartListDataService dataService, String region) {
			return dataService.getSingleNonUsCountryData(this.name());
		}
	},
	MEX {
		public RegionData getRegionData() {
			return new RegionData(this, 126200000, RegionLevels.COUNTRY, "Mexico");
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<WorldCases> getRegionDataList(ChartListDataService dataService, String region) {
			return dataService.getSingleNonUsCountryData(this.name());
		}
	},
	KOR {
		public RegionData getRegionData() {
			return new RegionData(this, 51640000, RegionLevels.COUNTRY, "South Korea");
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<WorldCases> getRegionDataList(ChartListDataService dataService, String region) {
			return dataService.getSingleNonUsCountryData(this.name());
		}
	},
	GBR {
		public RegionData getRegionData() {
			return new RegionData(this, 66650000, RegionLevels.COUNTRY, "Great Britain");
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<WorldCases> getRegionDataList(ChartListDataService dataService, String region) {
			return dataService.getSingleNonUsCountryData(this.name());
		}
	},
	USA_NO_NY {
		public RegionData getRegionData() {
			return new RegionData(this,
					USA.getRegionData().getPopulation() - NY.getRegionData().getPopulation(),
					RegionLevels.COUNTRY, "United States without New York State");
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<UnitedStatesCases> getRegionDataList(ChartListDataService dataService, String region) {
			return dataService.getAllUsDataExcludingState(NY.name());
		}
	},
	AL {
		public RegionData getRegionData() {
			return new RegionData(this, 4903000, RegionLevels.STATE, "Alabama");
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<UnitedStatesCases> getRegionDataList(ChartListDataService dataService, String region) {
			return dataService.getSingleUsStateData(this.name());
		}
	},
	AK {
		public RegionData getRegionData() {
			return new RegionData(this, 731545, RegionLevels.STATE, "Alaska");
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<UnitedStatesCases> getRegionDataList(ChartListDataService dataService, String region) {
			return dataService.getSingleUsStateData(this.name());
		}
	},
	AZ {
		public RegionData getRegionData() {
			return new RegionData(this, 7279000, RegionLevels.STATE, "Arizona");
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<UnitedStatesCases> getRegionDataList(ChartListDataService dataService, String region) {
			return dataService.getSingleUsStateData(this.name());
		}
	},
	AR {
		public RegionData getRegionData() {
			return new RegionData(this, 3018000, RegionLevels.STATE, "Arkansas");
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<UnitedStatesCases> getRegionDataList(ChartListDataService dataService, String region) {
			return dataService.getSingleUsStateData(this.name());
		}
	},
	CA {
		public RegionData getRegionData() {
			return new RegionData(this, 39510000, RegionLevels.STATE, "California");
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<UnitedStatesCases> getRegionDataList(ChartListDataService dataService, String region) {
			return dataService.getSingleUsStateData(this.name());
		}
	},
	CO {
		public RegionData getRegionData() {
			return new RegionData(this, 5759000, RegionLevels.STATE, "Colorado");
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<UnitedStatesCases> getRegionDataList(ChartListDataService dataService, String region) {
			return dataService.getSingleUsStateData(this.name());
		}
	},
	CT {
		public RegionData getRegionData() {
			return new RegionData(this, 3565000, RegionLevels.STATE, "Connecticut");
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<UnitedStatesCases> getRegionDataList(ChartListDataService dataService, String region) {
			return dataService.getSingleUsStateData(this.name());
		}
	},
	DE {
		public RegionData getRegionData() {
			return new RegionData(this, 973764, RegionLevels.STATE, "Delaware");
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<UnitedStatesCases> getRegionDataList(ChartListDataService dataService, String region) {
			return dataService.getSingleUsStateData(this.name());
		}
	},
	FL {
		public RegionData getRegionData() {
			return new RegionData(this, 21480000, RegionLevels.STATE, "Florida");
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<UnitedStatesCases> getRegionDataList(ChartListDataService dataService, String region) {
			return dataService.getSingleUsStateData(this.name());
		}
	},
	GA {
		public RegionData getRegionData() {
			return new RegionData(this, 10620000, RegionLevels.STATE, "Georgia");
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<UnitedStatesCases> getRegionDataList(ChartListDataService dataService, String region) {
			return dataService.getSingleUsStateData(this.name());
		}
	},
	HI {
		public RegionData getRegionData() {
			return new RegionData(this, 1416000, RegionLevels.STATE, "Hawaii");
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<UnitedStatesCases> getRegionDataList(ChartListDataService dataService, String region) {
			return dataService.getSingleUsStateData(this.name());
		}
	},
	ID {
		public RegionData getRegionData() {
			return new RegionData(this, 1787000, RegionLevels.STATE, "Idaho");
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<UnitedStatesCases> getRegionDataList(ChartListDataService dataService, String region) {
			return dataService.getSingleUsStateData(this.name());
		}
	},
	IL {
		public RegionData getRegionData() {
			return new RegionData(this, 12670000, RegionLevels.STATE, "Illinois");
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<UnitedStatesCases> getRegionDataList(ChartListDataService dataService, String region) {
			return dataService.getSingleUsStateData(this.name());
		}
	},
	IN {
		public RegionData getRegionData() {
			return new RegionData(this, 6732000, RegionLevels.STATE, "Indiana");
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<UnitedStatesCases> getRegionDataList(ChartListDataService dataService, String region) {
			return dataService.getSingleUsStateData(this.name());
		}
	},
	IA {
		public RegionData getRegionData() {
			return new RegionData(this, 3155000, RegionLevels.STATE, "Iowa");
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<UnitedStatesCases> getRegionDataList(ChartListDataService dataService, String region) {
			return dataService.getSingleUsStateData(this.name());
		}
	},
	KS {
		public RegionData getRegionData() {
			return new RegionData(this, 2913000, RegionLevels.STATE, "Kansas");
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<UnitedStatesCases> getRegionDataList(ChartListDataService dataService, String region) {
			return dataService.getSingleUsStateData(this.name());
		}
	},
	KY {
		public RegionData getRegionData() {
			return new RegionData(this, 4468000, RegionLevels.STATE, "Kentucky");
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<UnitedStatesCases> getRegionDataList(ChartListDataService dataService, String region) {
			return dataService.getSingleUsStateData(this.name());
		}
	},
	LA {
		public RegionData getRegionData() {
			return new RegionData(this, 3990000, RegionLevels.STATE, "Louisiana");
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<UnitedStatesCases> getRegionDataList(ChartListDataService dataService, String region) {
			return dataService.getSingleUsStateData(this.name());
		}
	},
	ME {
		public RegionData getRegionData() {
			return new RegionData(this, 1344000, RegionLevels.STATE, "Maine");
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<UnitedStatesCases> getRegionDataList(ChartListDataService dataService, String region) {
			return dataService.getSingleUsStateData(this.name());
		}
	},
	MD {
		public RegionData getRegionData() {
			return new RegionData(this, 6046000, RegionLevels.STATE, "Maryland");
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<UnitedStatesCases> getRegionDataList(ChartListDataService dataService, String region) {
			return dataService.getSingleUsStateData(this.name());
		}
	},
	MA {
		public RegionData getRegionData() {
			return new RegionData(this, 6893000, RegionLevels.STATE, "Massachusetts");
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<UnitedStatesCases> getRegionDataList(ChartListDataService dataService, String region) {
			return dataService.getSingleUsStateData(this.name());
		}
	},
	MI {
		public RegionData getRegionData() {
			return new RegionData(this, 9987000, RegionLevels.STATE, "Michigan");
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<UnitedStatesCases> getRegionDataList(ChartListDataService dataService, String region) {
			return dataService.getSingleUsStateData(this.name());
		}
	},
	MN {
		public RegionData getRegionData() {
			return new RegionData(this, 5640000, RegionLevels.STATE, "Minnesota");
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<UnitedStatesCases> getRegionDataList(ChartListDataService dataService, String region) {
			return dataService.getSingleUsStateData(this.name());
		}
	},
	MS {
		public RegionData getRegionData() {
			return new RegionData(this, 2976000, RegionLevels.STATE, "Mississippi");
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<UnitedStatesCases> getRegionDataList(ChartListDataService dataService, String region) {
			return dataService.getSingleUsStateData(this.name());
		}
	},
	MO {
		public RegionData getRegionData() {
			return new RegionData(this, 6137000, RegionLevels.STATE, "Missouri");
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<UnitedStatesCases> getRegionDataList(ChartListDataService dataService, String region) {
			return dataService.getSingleUsStateData(this.name());
		}
	},
	MT {
		public RegionData getRegionData() {
			return new RegionData(this, 1069000, RegionLevels.STATE, "Montana");
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<UnitedStatesCases> getRegionDataList(ChartListDataService dataService, String region) {
			return dataService.getSingleUsStateData(this.name());
		}
	},
	NE {
		public RegionData getRegionData() {
			return new RegionData(this, 1934000, RegionLevels.STATE, "Nebraska");
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<UnitedStatesCases> getRegionDataList(ChartListDataService dataService, String region) {
			return dataService.getSingleUsStateData(this.name());
		}
	},
	NV {
		public RegionData getRegionData() {
			return new RegionData(this, 3080000, RegionLevels.STATE, "Nevada");
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<UnitedStatesCases> getRegionDataList(ChartListDataService dataService, String region) {
			return dataService.getSingleUsStateData(this.name());
		}
	},
	NH {
		public RegionData getRegionData() {
			return new RegionData(this, 1360000, RegionLevels.STATE, "New Hampshire");
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<UnitedStatesCases> getRegionDataList(ChartListDataService dataService, String region) {
			return dataService.getSingleUsStateData(this.name());
		}
	},
	NJ {
		public RegionData getRegionData() {
			return new RegionData(this, 8882000, RegionLevels.STATE, "New Jersey");
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<UnitedStatesCases> getRegionDataList(ChartListDataService dataService, String region) {
			return dataService.getSingleUsStateData(this.name());
		}
	},
	NM {
		public RegionData getRegionData() {
			return new RegionData(this, 2097000, RegionLevels.STATE, "New Mexico");
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<UnitedStatesCases> getRegionDataList(ChartListDataService dataService, String region) {
			return dataService.getSingleUsStateData(this.name());
		}
	},
	NY {
		public RegionData getRegionData() {
			return new RegionData(this, 19450000, RegionLevels.STATE, "New York");
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<UnitedStatesCases> getRegionDataList(ChartListDataService dataService, String region) {
			return dataService.getSingleUsStateData(this.name());
		}
	},
	NC {
		public RegionData getRegionData() {
			return new RegionData(this, 10490000, RegionLevels.STATE, "North Carolina");
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<UnitedStatesCases> getRegionDataList(ChartListDataService dataService, String region) {
			return dataService.getSingleUsStateData(this.name());
		}
	},
	ND {
		public RegionData getRegionData() {
			return new RegionData(this, 762062, RegionLevels.STATE, "North Dakota");
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<UnitedStatesCases> getRegionDataList(ChartListDataService dataService, String region) {
			return dataService.getSingleUsStateData(this.name());
		}
	},
	OH {
		public RegionData getRegionData() {
			return new RegionData(this, 11690000, RegionLevels.STATE, "Ohio");
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<UnitedStatesCases> getRegionDataList(ChartListDataService dataService, String region) {
			return dataService.getSingleUsStateData(this.name());
		}
	},
	OK {
		public RegionData getRegionData() {
			return new RegionData(this, 3957000, RegionLevels.STATE, "Oklahoma");
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<UnitedStatesCases> getRegionDataList(ChartListDataService dataService, String region) {
			return dataService.getSingleUsStateData(this.name());
		}
	},
	OR {
		public RegionData getRegionData() {
			return new RegionData(this, 4218000, RegionLevels.STATE, "Oregon");
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<UnitedStatesCases> getRegionDataList(ChartListDataService dataService, String region) {
			return dataService.getSingleUsStateData(this.name());
		}
	},
	PA {
		public RegionData getRegionData() {
			return new RegionData(this, 12800000, RegionLevels.STATE, "Pennsylvania");
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<UnitedStatesCases> getRegionDataList(ChartListDataService dataService, String region) {
			return dataService.getSingleUsStateData(this.name());
		}
	},
	RI {
		public RegionData getRegionData() {
			return new RegionData(this, 1059000, RegionLevels.STATE, "Rhode Island");
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<UnitedStatesCases> getRegionDataList(ChartListDataService dataService, String region) {
			return dataService.getSingleUsStateData(this.name());
		}
	},
	SC {
		public RegionData getRegionData() {
			return new RegionData(this, 5149000, RegionLevels.STATE, "South Carolina");
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<UnitedStatesCases> getRegionDataList(ChartListDataService dataService, String region) {
			return dataService.getSingleUsStateData(this.name());
		}
	},
	SD {
		public RegionData getRegionData() {
			return new RegionData(this, 884659, RegionLevels.STATE, "South Dakota");
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<UnitedStatesCases> getRegionDataList(ChartListDataService dataService, String region) {
			return dataService.getSingleUsStateData(this.name());
		}
	},
	TN {
		public RegionData getRegionData() {
			return new RegionData(this, 6829000, RegionLevels.STATE, "Tennessee");
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<UnitedStatesCases> getRegionDataList(ChartListDataService dataService, String region) {
			return dataService.getSingleUsStateData(this.name());
		}
	},
	TX {
		public RegionData getRegionData() {
			return new RegionData(this, 29000000, RegionLevels.STATE, "Texas");
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<UnitedStatesCases> getRegionDataList(ChartListDataService dataService, String region) {
			return dataService.getSingleUsStateData(this.name());
		}
	},
	UT {
		public RegionData getRegionData() {
			return new RegionData(this, 3206000, RegionLevels.STATE, "Utah");
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<UnitedStatesCases> getRegionDataList(ChartListDataService dataService, String region) {
			return dataService.getSingleUsStateData(this.name());
		}
	},
	VT {
		public RegionData getRegionData() {
			return new RegionData(this, 623989, RegionLevels.STATE, "Vermont");
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<UnitedStatesCases> getRegionDataList(ChartListDataService dataService, String region) {
			return dataService.getSingleUsStateData(this.name());
		}
	},
	VA {
		public RegionData getRegionData() {
			return new RegionData(this, 8536000, RegionLevels.STATE, "Virginia");
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<UnitedStatesCases> getRegionDataList(ChartListDataService dataService, String region) {
			return dataService.getSingleUsStateData(this.name());
		}
	},
	WA {
		public RegionData getRegionData() {
			return new RegionData(this, 7615000, RegionLevels.STATE, "Washington");
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<UnitedStatesCases> getRegionDataList(ChartListDataService dataService, String region) {
			return dataService.getSingleUsStateData(this.name());
		}
	},
	WV {
		public RegionData getRegionData() {
			return new RegionData(this, 1792000, RegionLevels.STATE, "West Virginia");
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<UnitedStatesCases> getRegionDataList(ChartListDataService dataService, String region) {
			return dataService.getSingleUsStateData(this.name());
		}
	},
	WI {
		public RegionData getRegionData() {
			return new RegionData(this, 5822000, RegionLevels.STATE, "Wisconsin");
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<UnitedStatesCases> getRegionDataList(ChartListDataService dataService, String region) {
			return dataService.getSingleUsStateData(this.name());
		}
	},
	WY {
		public RegionData getRegionData() {
			return new RegionData(this, 578759, RegionLevels.STATE, "Wyoming");
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<UnitedStatesCases> getRegionDataList(ChartListDataService dataService, String region) {
			return dataService.getSingleUsStateData(this.name());
		}
	},
	DC {
		public RegionData getRegionData() {
			return new RegionData(this, 702455, RegionLevels.STATE, "District of Columbia");
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<UnitedStatesCases> getRegionDataList(ChartListDataService dataService, String region) {
			return dataService.getSingleUsStateData(this.name());
		}
	};
	
	public abstract RegionData getRegionData();
	public abstract <T extends CanonicalCases> List<T> getRegionDataList(ChartListDataService dataService, String region);
}
