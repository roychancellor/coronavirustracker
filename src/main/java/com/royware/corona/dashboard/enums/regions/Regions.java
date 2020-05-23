package com.royware.corona.dashboard.enums.regions;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.royware.corona.dashboard.enums.data.CacheKeys;
import com.royware.corona.dashboard.interfaces.data.ExternalDataService;
import com.royware.corona.dashboard.interfaces.model.CanonicalData;
import com.royware.corona.dashboard.model.data.RegionData;
import com.royware.corona.dashboard.model.data.UnitedStatesData;
import com.royware.corona.dashboard.model.data.WorldData;

@Service
public enum Regions {
	USA {
		@Override
		public RegionData getRegionData() {
			return new RegionData(328200000, RegionTypes.COUNTRY, "United States");
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<UnitedStatesData> getCoronaVirusDataFromExternalSource(ExternalDataService eds) {
			Logger log = LoggerFactory.getLogger(Regions.class);
			log.info("In the Regions enum for " + this.name() + " about to call makeDataListFromExternalSource with " + eds.toString());
			return eds.makeDataListFromExternalSource(CacheKeys.CACHE_KEY_US.getName());
		}
	},
	AUS {
		public RegionData getRegionData() {
			return new RegionData(24990000, RegionTypes.COUNTRY, "Australia");
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<WorldData> getCoronaVirusDataFromExternalSource(ExternalDataService eds) {
			Logger log = LoggerFactory.getLogger(Regions.class);
			log.info("In the Regions enum for " + this.name() + " about to call makeDataListFromExternalSource with " + eds.toString());
			return eds.makeDataListFromExternalSource(this.name());
		}
	},
	CAN {
		public RegionData getRegionData() {
			return new RegionData(37590000, RegionTypes.COUNTRY, "Canada");
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<WorldData> getCoronaVirusDataFromExternalSource(ExternalDataService eds) {
			Logger log = LoggerFactory.getLogger(Regions.class);
			log.info("In the Regions enum for " + this.name() + " about to call makeDataListFromExternalSource with " + eds.toString());
			return eds.makeDataListFromExternalSource(this.name());
		}
	},
	CHN {
		public RegionData getRegionData() {
			return new RegionData(1393000000, RegionTypes.COUNTRY, "China");
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<WorldData> getCoronaVirusDataFromExternalSource(ExternalDataService eds) {
			Logger log = LoggerFactory.getLogger(Regions.class);
			log.info("In the Regions enum for " + this.name() + " about to call makeDataListFromExternalSource with " + eds.toString());
			return eds.makeDataListFromExternalSource(this.name());
		}
	},
	DEU {
		public RegionData getRegionData() {
			return new RegionData(83020000, RegionTypes.COUNTRY, "Germany");
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<WorldData> getCoronaVirusDataFromExternalSource(ExternalDataService eds) {
			Logger log = LoggerFactory.getLogger(Regions.class);
			log.info("In the Regions enum for " + this.name() + " about to call makeDataListFromExternalSource with " + eds.toString());
			return eds.makeDataListFromExternalSource(this.name());
		}
	},
	ESP {
		public RegionData getRegionData() {
			return new RegionData(46940000, RegionTypes.COUNTRY, "Spain");
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<WorldData> getCoronaVirusDataFromExternalSource(ExternalDataService eds) {
			Logger log = LoggerFactory.getLogger(Regions.class);
			log.info("In the Regions enum for " + this.name() + " about to call makeDataListFromExternalSource with " + eds.toString());
			return eds.makeDataListFromExternalSource(this.name());
		}
	},
	FRA {
		public RegionData getRegionData() {
			return new RegionData(66990000, RegionTypes.COUNTRY, "France");
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<WorldData> getCoronaVirusDataFromExternalSource(ExternalDataService eds) {
			Logger log = LoggerFactory.getLogger(Regions.class);
			log.info("In the Regions enum for " + this.name() + " about to call makeDataListFromExternalSource with " + eds.toString());
			return eds.makeDataListFromExternalSource(this.name());
		}
	},
	GBR {
		public RegionData getRegionData() {
			return new RegionData(66650000, RegionTypes.COUNTRY, "Great Britain");
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<WorldData> getCoronaVirusDataFromExternalSource(ExternalDataService eds) {
			Logger log = LoggerFactory.getLogger(Regions.class);
			log.info("In the Regions enum for " + this.name() + " about to call makeDataListFromExternalSource with " + eds.toString());
			return eds.makeDataListFromExternalSource(this.name());
		}
	},
	ITA {
		public RegionData getRegionData() {
			return new RegionData(60360000, RegionTypes.COUNTRY, "Italy");
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<WorldData> getCoronaVirusDataFromExternalSource(ExternalDataService eds) {
			Logger log = LoggerFactory.getLogger(Regions.class);
			log.info("In the Regions enum for " + this.name() + " about to call makeDataListFromExternalSource with " + eds.toString());
			return eds.makeDataListFromExternalSource(this.name());
		}
	},
	JPN {
		public RegionData getRegionData() {
			return new RegionData(126500000, RegionTypes.COUNTRY, "Japan");
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<WorldData> getCoronaVirusDataFromExternalSource(ExternalDataService eds) {
			Logger log = LoggerFactory.getLogger(Regions.class);
			log.info("In the Regions enum for " + this.name() + " about to call makeDataListFromExternalSource with " + eds.toString());
			return eds.makeDataListFromExternalSource(this.name());
		}
	},
	KOR {
		public RegionData getRegionData() {
			return new RegionData(51640000, RegionTypes.COUNTRY, "South Korea");
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<WorldData> getCoronaVirusDataFromExternalSource(ExternalDataService eds) {
			Logger log = LoggerFactory.getLogger(Regions.class);
			log.info("In the Regions enum for " + this.name() + " about to call makeDataListFromExternalSource with " + eds.toString());
			return eds.makeDataListFromExternalSource(this.name());
		}
	},
	MEX {
		public RegionData getRegionData() {
			return new RegionData(126200000, RegionTypes.COUNTRY, "Mexico");
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<WorldData> getCoronaVirusDataFromExternalSource(ExternalDataService eds) {
			Logger log = LoggerFactory.getLogger(Regions.class);
			log.info("In the Regions enum for " + this.name() + " about to call makeDataListFromExternalSource with " + eds.toString());
			return eds.makeDataListFromExternalSource(this.name());
		}
	},
	NOR {
		public RegionData getRegionData() {
			return new RegionData(5638000, RegionTypes.COUNTRY, "Norway");
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<WorldData> getCoronaVirusDataFromExternalSource(ExternalDataService eds) {
			Logger log = LoggerFactory.getLogger(Regions.class);
			log.info("In the Regions enum for " + this.name() + " about to call makeDataListFromExternalSource with " + eds.toString());
			return eds.makeDataListFromExternalSource(this.name());
		}
	},
	PRI {
		public RegionData getRegionData() {
			return new RegionData(3194000, RegionTypes.COUNTRY, "Puerto Rico");
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<WorldData> getCoronaVirusDataFromExternalSource(ExternalDataService eds) {
			Logger log = LoggerFactory.getLogger(Regions.class);
			log.info("In the Regions enum for " + this.name() + " about to call makeDataListFromExternalSource with " + eds.toString());
			return eds.makeDataListFromExternalSource(this.name());
		}
	},
	SGP {
		public RegionData getRegionData() {
			return new RegionData(5639000, RegionTypes.COUNTRY, "Singapore");
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<WorldData> getCoronaVirusDataFromExternalSource(ExternalDataService eds) {
			Logger log = LoggerFactory.getLogger(Regions.class);
			log.info("In the Regions enum for " + this.name() + " about to call makeDataListFromExternalSource with " + eds.toString());
			return eds.makeDataListFromExternalSource(this.name());
		}
	},
	SWE {
		public RegionData getRegionData() {
			return new RegionData(10230000, RegionTypes.COUNTRY, "Sweden");
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<WorldData> getCoronaVirusDataFromExternalSource(ExternalDataService eds) {
			Logger log = LoggerFactory.getLogger(Regions.class);
			log.info("In the Regions enum for " + this.name() + " about to call makeDataListFromExternalSource with " + eds.toString());
			return eds.makeDataListFromExternalSource(this.name());
		}
	},
	USA_NO_NY {
		public RegionData getRegionData() {
			return new RegionData(USA.getRegionData().getPopulation() - NY.getRegionData().getPopulation(),
					RegionTypes.COUNTRY, "U.S. without New York State");
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<UnitedStatesData> getCoronaVirusDataFromExternalSource(ExternalDataService eds) {
			Logger log = LoggerFactory.getLogger(Regions.class);
			log.info("In the Regions enum for " + this.name() + " about to call makeDataListFromExternalSource with " + eds.toString());
			return eds.makeDataListFromExternalSource("NY");
		}
	},
	AL {
		public RegionData getRegionData() {
			return new RegionData(4903000, RegionTypes.STATE, "Alabama");
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<UnitedStatesData> getCoronaVirusDataFromExternalSource(ExternalDataService eds) {
			Logger log = LoggerFactory.getLogger(Regions.class);
			log.info("In the Regions enum for " + this.name() + " about to call makeDataListFromExternalSource with " + eds.toString());
			return eds.makeDataListFromExternalSource(this.name());
		}
	},
	AK {
		public RegionData getRegionData() {
			return new RegionData(731545, RegionTypes.STATE, "Alaska");
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<UnitedStatesData> getCoronaVirusDataFromExternalSource(ExternalDataService eds) {
			Logger log = LoggerFactory.getLogger(Regions.class);
			log.info("In the Regions enum for " + this.name() + " about to call makeDataListFromExternalSource with " + eds.toString());
			return eds.makeDataListFromExternalSource(this.name());
		}
	},
	AZ {
		public RegionData getRegionData() {
			return new RegionData(7279000, RegionTypes.STATE, "Arizona");
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<UnitedStatesData> getCoronaVirusDataFromExternalSource(ExternalDataService eds) {
			Logger log = LoggerFactory.getLogger(Regions.class);
			log.info("In the Regions enum for " + this.name() + " about to call makeDataListFromExternalSource with " + eds.toString());
			return eds.makeDataListFromExternalSource(this.name());
		}
	},
	AR {
		public RegionData getRegionData() {
			return new RegionData(3018000, RegionTypes.STATE, "Arkansas");
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<UnitedStatesData> getCoronaVirusDataFromExternalSource(ExternalDataService eds) {
			Logger log = LoggerFactory.getLogger(Regions.class);
			log.info("In the Regions enum for " + this.name() + " about to call makeDataListFromExternalSource with " + eds.toString());
			return eds.makeDataListFromExternalSource(this.name());
		}
	},
	CA {
		public RegionData getRegionData() {
			return new RegionData(39510000, RegionTypes.STATE, "California");
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<UnitedStatesData> getCoronaVirusDataFromExternalSource(ExternalDataService eds) {
			Logger log = LoggerFactory.getLogger(Regions.class);
			log.info("In the Regions enum for " + this.name() + " about to call makeDataListFromExternalSource with " + eds.toString());
			return eds.makeDataListFromExternalSource(this.name());
		}
	},
	CO {
		public RegionData getRegionData() {
			return new RegionData(5759000, RegionTypes.STATE, "Colorado");
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<UnitedStatesData> getCoronaVirusDataFromExternalSource(ExternalDataService eds) {
			Logger log = LoggerFactory.getLogger(Regions.class);
			log.info("In the Regions enum for " + this.name() + " about to call makeDataListFromExternalSource with " + eds.toString());
			return eds.makeDataListFromExternalSource(this.name());
		}
	},
	CT {
		public RegionData getRegionData() {
			return new RegionData(3565000, RegionTypes.STATE, "Connecticut");
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<UnitedStatesData> getCoronaVirusDataFromExternalSource(ExternalDataService eds) {
			Logger log = LoggerFactory.getLogger(Regions.class);
			log.info("In the Regions enum for " + this.name() + " about to call makeDataListFromExternalSource with " + eds.toString());
			return eds.makeDataListFromExternalSource(this.name());
		}
	},
	DE {
		public RegionData getRegionData() {
			return new RegionData(973764, RegionTypes.STATE, "Delaware");
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<UnitedStatesData> getCoronaVirusDataFromExternalSource(ExternalDataService eds) {
			Logger log = LoggerFactory.getLogger(Regions.class);
			log.info("In the Regions enum for " + this.name() + " about to call makeDataListFromExternalSource with " + eds.toString());
			return eds.makeDataListFromExternalSource(this.name());
		}
	},
	FL {
		public RegionData getRegionData() {
			return new RegionData(21480000, RegionTypes.STATE, "Florida");
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<UnitedStatesData> getCoronaVirusDataFromExternalSource(ExternalDataService eds) {
			Logger log = LoggerFactory.getLogger(Regions.class);
			log.info("In the Regions enum for " + this.name() + " about to call makeDataListFromExternalSource with " + eds.toString());
			return eds.makeDataListFromExternalSource(this.name());
		}
	},
	GA {
		public RegionData getRegionData() {
			return new RegionData(10620000, RegionTypes.STATE, "Georgia");
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<UnitedStatesData> getCoronaVirusDataFromExternalSource(ExternalDataService eds) {
			Logger log = LoggerFactory.getLogger(Regions.class);
			log.info("In the Regions enum for " + this.name() + " about to call makeDataListFromExternalSource with " + eds.toString());
			return eds.makeDataListFromExternalSource(this.name());
		}
	},
	HI {
		public RegionData getRegionData() {
			return new RegionData(1416000, RegionTypes.STATE, "Hawaii");
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<UnitedStatesData> getCoronaVirusDataFromExternalSource(ExternalDataService eds) {
			Logger log = LoggerFactory.getLogger(Regions.class);
			log.info("In the Regions enum for " + this.name() + " about to call makeDataListFromExternalSource with " + eds.toString());
			return eds.makeDataListFromExternalSource(this.name());
		}
	},
	ID {
		public RegionData getRegionData() {
			return new RegionData(1787000, RegionTypes.STATE, "Idaho");
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<UnitedStatesData> getCoronaVirusDataFromExternalSource(ExternalDataService eds) {
			Logger log = LoggerFactory.getLogger(Regions.class);
			log.info("In the Regions enum for " + this.name() + " about to call makeDataListFromExternalSource with " + eds.toString());
			return eds.makeDataListFromExternalSource(this.name());
		}
	},
	IL {
		public RegionData getRegionData() {
			return new RegionData(12670000, RegionTypes.STATE, "Illinois");
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<UnitedStatesData> getCoronaVirusDataFromExternalSource(ExternalDataService eds) {
			Logger log = LoggerFactory.getLogger(Regions.class);
			log.info("In the Regions enum for " + this.name() + " about to call makeDataListFromExternalSource with " + eds.toString());
			return eds.makeDataListFromExternalSource(this.name());
		}
	},
	IN {
		public RegionData getRegionData() {
			return new RegionData(6732000, RegionTypes.STATE, "Indiana");
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<UnitedStatesData> getCoronaVirusDataFromExternalSource(ExternalDataService eds) {
			Logger log = LoggerFactory.getLogger(Regions.class);
			log.info("In the Regions enum for " + this.name() + " about to call makeDataListFromExternalSource with " + eds.toString());
			return eds.makeDataListFromExternalSource(this.name());
		}
	},
	IA {
		public RegionData getRegionData() {
			return new RegionData(3155000, RegionTypes.STATE, "Iowa");
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<UnitedStatesData> getCoronaVirusDataFromExternalSource(ExternalDataService eds) {
			Logger log = LoggerFactory.getLogger(Regions.class);
			log.info("In the Regions enum for " + this.name() + " about to call makeDataListFromExternalSource with " + eds.toString());
			return eds.makeDataListFromExternalSource(this.name());
		}
	},
	KS {
		public RegionData getRegionData() {
			return new RegionData(2913000, RegionTypes.STATE, "Kansas");
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<UnitedStatesData> getCoronaVirusDataFromExternalSource(ExternalDataService eds) {
			Logger log = LoggerFactory.getLogger(Regions.class);
			log.info("In the Regions enum for " + this.name() + " about to call makeDataListFromExternalSource with " + eds.toString());
			return eds.makeDataListFromExternalSource(this.name());
		}
	},
	KY {
		public RegionData getRegionData() {
			return new RegionData(4468000, RegionTypes.STATE, "Kentucky");
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<UnitedStatesData> getCoronaVirusDataFromExternalSource(ExternalDataService eds) {
			Logger log = LoggerFactory.getLogger(Regions.class);
			log.info("In the Regions enum for " + this.name() + " about to call makeDataListFromExternalSource with " + eds.toString());
			return eds.makeDataListFromExternalSource(this.name());
		}
	},
	LA {
		public RegionData getRegionData() {
			return new RegionData(3990000, RegionTypes.STATE, "Louisiana");
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<UnitedStatesData> getCoronaVirusDataFromExternalSource(ExternalDataService eds) {
			Logger log = LoggerFactory.getLogger(Regions.class);
			log.info("In the Regions enum for " + this.name() + " about to call makeDataListFromExternalSource with " + eds.toString());
			return eds.makeDataListFromExternalSource(this.name());
		}
	},
	ME {
		public RegionData getRegionData() {
			return new RegionData(1344000, RegionTypes.STATE, "Maine");
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<UnitedStatesData> getCoronaVirusDataFromExternalSource(ExternalDataService eds) {
			Logger log = LoggerFactory.getLogger(Regions.class);
			log.info("In the Regions enum for " + this.name() + " about to call makeDataListFromExternalSource with " + eds.toString());
			return eds.makeDataListFromExternalSource(this.name());
		}
	},
	MD {
		public RegionData getRegionData() {
			return new RegionData(6046000, RegionTypes.STATE, "Maryland");
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<UnitedStatesData> getCoronaVirusDataFromExternalSource(ExternalDataService eds) {
			Logger log = LoggerFactory.getLogger(Regions.class);
			log.info("In the Regions enum for " + this.name() + " about to call makeDataListFromExternalSource with " + eds.toString());
			return eds.makeDataListFromExternalSource(this.name());
		}
	},
	MA {
		public RegionData getRegionData() {
			return new RegionData(6893000, RegionTypes.STATE, "Massachusetts");
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<UnitedStatesData> getCoronaVirusDataFromExternalSource(ExternalDataService eds) {
			Logger log = LoggerFactory.getLogger(Regions.class);
			log.info("In the Regions enum for " + this.name() + " about to call makeDataListFromExternalSource with " + eds.toString());
			return eds.makeDataListFromExternalSource(this.name());
		}
	},
	MI {
		public RegionData getRegionData() {
			return new RegionData(9987000, RegionTypes.STATE, "Michigan");
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<UnitedStatesData> getCoronaVirusDataFromExternalSource(ExternalDataService eds) {
			Logger log = LoggerFactory.getLogger(Regions.class);
			log.info("In the Regions enum for " + this.name() + " about to call makeDataListFromExternalSource with " + eds.toString());
			return eds.makeDataListFromExternalSource(this.name());
		}
	},
	MN {
		public RegionData getRegionData() {
			return new RegionData(5640000, RegionTypes.STATE, "Minnesota");
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<UnitedStatesData> getCoronaVirusDataFromExternalSource(ExternalDataService eds) {
			Logger log = LoggerFactory.getLogger(Regions.class);
			log.info("In the Regions enum for " + this.name() + " about to call makeDataListFromExternalSource with " + eds.toString());
			return eds.makeDataListFromExternalSource(this.name());
		}
	},
	MS {
		public RegionData getRegionData() {
			return new RegionData(2976000, RegionTypes.STATE, "Mississippi");
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<UnitedStatesData> getCoronaVirusDataFromExternalSource(ExternalDataService eds) {
			Logger log = LoggerFactory.getLogger(Regions.class);
			log.info("In the Regions enum for " + this.name() + " about to call makeDataListFromExternalSource with " + eds.toString());
			return eds.makeDataListFromExternalSource(this.name());
		}
	},
	MO {
		public RegionData getRegionData() {
			return new RegionData(6137000, RegionTypes.STATE, "Missouri");
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<UnitedStatesData> getCoronaVirusDataFromExternalSource(ExternalDataService eds) {
			Logger log = LoggerFactory.getLogger(Regions.class);
			log.info("In the Regions enum for " + this.name() + " about to call makeDataListFromExternalSource with " + eds.toString());
			return eds.makeDataListFromExternalSource(this.name());
		}
	},
	MT {
		public RegionData getRegionData() {
			return new RegionData(1069000, RegionTypes.STATE, "Montana");
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<UnitedStatesData> getCoronaVirusDataFromExternalSource(ExternalDataService eds) {
			Logger log = LoggerFactory.getLogger(Regions.class);
			log.info("In the Regions enum for " + this.name() + " about to call makeDataListFromExternalSource with " + eds.toString());
			return eds.makeDataListFromExternalSource(this.name());
		}
	},
	NE {
		public RegionData getRegionData() {
			return new RegionData(1934000, RegionTypes.STATE, "Nebraska");
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<UnitedStatesData> getCoronaVirusDataFromExternalSource(ExternalDataService eds) {
			Logger log = LoggerFactory.getLogger(Regions.class);
			log.info("In the Regions enum for " + this.name() + " about to call makeDataListFromExternalSource with " + eds.toString());
			return eds.makeDataListFromExternalSource(this.name());
		}
	},
	NV {
		public RegionData getRegionData() {
			return new RegionData(3080000, RegionTypes.STATE, "Nevada");
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<UnitedStatesData> getCoronaVirusDataFromExternalSource(ExternalDataService eds) {
			Logger log = LoggerFactory.getLogger(Regions.class);
			log.info("In the Regions enum for " + this.name() + " about to call makeDataListFromExternalSource with " + eds.toString());
			return eds.makeDataListFromExternalSource(this.name());
		}
	},
	NH {
		public RegionData getRegionData() {
			return new RegionData(1360000, RegionTypes.STATE, "New Hampshire");
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<UnitedStatesData> getCoronaVirusDataFromExternalSource(ExternalDataService eds) {
			Logger log = LoggerFactory.getLogger(Regions.class);
			log.info("In the Regions enum for " + this.name() + " about to call makeDataListFromExternalSource with " + eds.toString());
			return eds.makeDataListFromExternalSource(this.name());
		}
	},
	NJ {
		public RegionData getRegionData() {
			return new RegionData(8882000, RegionTypes.STATE, "New Jersey");
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<UnitedStatesData> getCoronaVirusDataFromExternalSource(ExternalDataService eds) {
			Logger log = LoggerFactory.getLogger(Regions.class);
			log.info("In the Regions enum for " + this.name() + " about to call makeDataListFromExternalSource with " + eds.toString());
			return eds.makeDataListFromExternalSource(this.name());
		}
	},
	NM {
		public RegionData getRegionData() {
			return new RegionData(2097000, RegionTypes.STATE, "New Mexico");
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<UnitedStatesData> getCoronaVirusDataFromExternalSource(ExternalDataService eds) {
			Logger log = LoggerFactory.getLogger(Regions.class);
			log.info("In the Regions enum for " + this.name() + " about to call makeDataListFromExternalSource with " + eds.toString());
			return eds.makeDataListFromExternalSource(this.name());
		}
	},
	NY {
		public RegionData getRegionData() {
			return new RegionData(19450000, RegionTypes.STATE, "New York");
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<UnitedStatesData> getCoronaVirusDataFromExternalSource(ExternalDataService eds) {
			Logger log = LoggerFactory.getLogger(Regions.class);
			log.info("In the Regions enum for " + this.name() + " about to call makeDataListFromExternalSource with " + eds.toString());
			return eds.makeDataListFromExternalSource(this.name());
		}
	},
	NC {
		public RegionData getRegionData() {
			return new RegionData(10490000, RegionTypes.STATE, "North Carolina");
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<UnitedStatesData> getCoronaVirusDataFromExternalSource(ExternalDataService eds) {
			Logger log = LoggerFactory.getLogger(Regions.class);
			log.info("In the Regions enum for " + this.name() + " about to call makeDataListFromExternalSource with " + eds.toString());
			return eds.makeDataListFromExternalSource(this.name());
		}
	},
	ND {
		public RegionData getRegionData() {
			return new RegionData(762062, RegionTypes.STATE, "North Dakota");
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<UnitedStatesData> getCoronaVirusDataFromExternalSource(ExternalDataService eds) {
			Logger log = LoggerFactory.getLogger(Regions.class);
			log.info("In the Regions enum for " + this.name() + " about to call makeDataListFromExternalSource with " + eds.toString());
			return eds.makeDataListFromExternalSource(this.name());
		}
	},
	OH {
		public RegionData getRegionData() {
			return new RegionData(11690000, RegionTypes.STATE, "Ohio");
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<UnitedStatesData> getCoronaVirusDataFromExternalSource(ExternalDataService eds) {
			Logger log = LoggerFactory.getLogger(Regions.class);
			log.info("In the Regions enum for " + this.name() + " about to call makeDataListFromExternalSource with " + eds.toString());
			return eds.makeDataListFromExternalSource(this.name());
		}
	},
	OK {
		public RegionData getRegionData() {
			return new RegionData(3957000, RegionTypes.STATE, "Oklahoma");
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<UnitedStatesData> getCoronaVirusDataFromExternalSource(ExternalDataService eds) {
			Logger log = LoggerFactory.getLogger(Regions.class);
			log.info("In the Regions enum for " + this.name() + " about to call makeDataListFromExternalSource with " + eds.toString());
			return eds.makeDataListFromExternalSource(this.name());
		}
	},
	OR {
		public RegionData getRegionData() {
			return new RegionData(4218000, RegionTypes.STATE, "Oregon");
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<UnitedStatesData> getCoronaVirusDataFromExternalSource(ExternalDataService eds) {
			Logger log = LoggerFactory.getLogger(Regions.class);
			log.info("In the Regions enum for " + this.name() + " about to call makeDataListFromExternalSource with " + eds.toString());
			return eds.makeDataListFromExternalSource(this.name());
		}
	},
	PA {
		public RegionData getRegionData() {
			return new RegionData(12800000, RegionTypes.STATE, "Pennsylvania");
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<UnitedStatesData> getCoronaVirusDataFromExternalSource(ExternalDataService eds) {
			Logger log = LoggerFactory.getLogger(Regions.class);
			log.info("In the Regions enum for " + this.name() + " about to call makeDataListFromExternalSource with " + eds.toString());
			return eds.makeDataListFromExternalSource(this.name());
		}
	},
	RI {
		public RegionData getRegionData() {
			return new RegionData(1059000, RegionTypes.STATE, "Rhode Island");
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<UnitedStatesData> getCoronaVirusDataFromExternalSource(ExternalDataService eds) {
			Logger log = LoggerFactory.getLogger(Regions.class);
			log.info("In the Regions enum for " + this.name() + " about to call makeDataListFromExternalSource with " + eds.toString());
			return eds.makeDataListFromExternalSource(this.name());
		}
	},
	SC {
		public RegionData getRegionData() {
			return new RegionData(5149000, RegionTypes.STATE, "South Carolina");
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<UnitedStatesData> getCoronaVirusDataFromExternalSource(ExternalDataService eds) {
			Logger log = LoggerFactory.getLogger(Regions.class);
			log.info("In the Regions enum for " + this.name() + " about to call makeDataListFromExternalSource with " + eds.toString());
			return eds.makeDataListFromExternalSource(this.name());
		}
	},
	SD {
		public RegionData getRegionData() {
			return new RegionData(884659, RegionTypes.STATE, "South Dakota");
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<UnitedStatesData> getCoronaVirusDataFromExternalSource(ExternalDataService eds) {
			Logger log = LoggerFactory.getLogger(Regions.class);
			log.info("In the Regions enum for " + this.name() + " about to call makeDataListFromExternalSource with " + eds.toString());
			return eds.makeDataListFromExternalSource(this.name());
		}
	},
	TN {
		public RegionData getRegionData() {
			return new RegionData(6829000, RegionTypes.STATE, "Tennessee");
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<UnitedStatesData> getCoronaVirusDataFromExternalSource(ExternalDataService eds) {
			Logger log = LoggerFactory.getLogger(Regions.class);
			log.info("In the Regions enum for " + this.name() + " about to call makeDataListFromExternalSource with " + eds.toString());
			return eds.makeDataListFromExternalSource(this.name());
		}
	},
	TX {
		public RegionData getRegionData() {
			return new RegionData(29000000, RegionTypes.STATE, "Texas");
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<UnitedStatesData> getCoronaVirusDataFromExternalSource(ExternalDataService eds) {
			Logger log = LoggerFactory.getLogger(Regions.class);
			log.info("In the Regions enum for " + this.name() + " about to call makeDataListFromExternalSource with " + eds.toString());
			return eds.makeDataListFromExternalSource(this.name());
		}
	},
	UT {
		public RegionData getRegionData() {
			return new RegionData(3206000, RegionTypes.STATE, "Utah");
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<UnitedStatesData> getCoronaVirusDataFromExternalSource(ExternalDataService eds) {
			Logger log = LoggerFactory.getLogger(Regions.class);
			log.info("In the Regions enum for " + this.name() + " about to call makeDataListFromExternalSource with " + eds.toString());
			return eds.makeDataListFromExternalSource(this.name());
		}
	},
	VT {
		public RegionData getRegionData() {
			return new RegionData(623989, RegionTypes.STATE, "Vermont");
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<UnitedStatesData> getCoronaVirusDataFromExternalSource(ExternalDataService eds) {
			Logger log = LoggerFactory.getLogger(Regions.class);
			log.info("In the Regions enum for " + this.name() + " about to call makeDataListFromExternalSource with " + eds.toString());
			return eds.makeDataListFromExternalSource(this.name());
		}
	},
	VA {
		public RegionData getRegionData() {
			return new RegionData(8536000, RegionTypes.STATE, "Virginia");
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<UnitedStatesData> getCoronaVirusDataFromExternalSource(ExternalDataService eds) {
			Logger log = LoggerFactory.getLogger(Regions.class);
			log.info("In the Regions enum for " + this.name() + " about to call makeDataListFromExternalSource with " + eds.toString());
			return eds.makeDataListFromExternalSource(this.name());
		}
	},
	WA {
		public RegionData getRegionData() {
			return new RegionData(7615000, RegionTypes.STATE, "Washington");
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<UnitedStatesData> getCoronaVirusDataFromExternalSource(ExternalDataService eds) {
			Logger log = LoggerFactory.getLogger(Regions.class);
			log.info("In the Regions enum for " + this.name() + " about to call makeDataListFromExternalSource with " + eds.toString());
			return eds.makeDataListFromExternalSource(this.name());
		}
	},
	WV {
		public RegionData getRegionData() {
			return new RegionData(1792000, RegionTypes.STATE, "West Virginia");
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<UnitedStatesData> getCoronaVirusDataFromExternalSource(ExternalDataService eds) {
			Logger log = LoggerFactory.getLogger(Regions.class);
			log.info("In the Regions enum for " + this.name() + " about to call makeDataListFromExternalSource with " + eds.toString());
			return eds.makeDataListFromExternalSource(this.name());
		}
	},
	WI {
		public RegionData getRegionData() {
			return new RegionData(5822000, RegionTypes.STATE, "Wisconsin");
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<UnitedStatesData> getCoronaVirusDataFromExternalSource(ExternalDataService eds) {
			Logger log = LoggerFactory.getLogger(Regions.class);
			log.info("In the Regions enum for " + this.name() + " about to call makeDataListFromExternalSource with " + eds.toString());
			return eds.makeDataListFromExternalSource(this.name());
		}
	},
	WY {
		public RegionData getRegionData() {
			return new RegionData(578759, RegionTypes.STATE, "Wyoming");
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<UnitedStatesData> getCoronaVirusDataFromExternalSource(ExternalDataService eds) {
			Logger log = LoggerFactory.getLogger(Regions.class);
			log.info("In the Regions enum for " + this.name() + " about to call makeDataListFromExternalSource with " + eds.toString());
			return eds.makeDataListFromExternalSource(this.name());
		}
	},
	DC {
		public RegionData getRegionData() {
			return new RegionData(702455, RegionTypes.STATE, "District of Columbia");
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<UnitedStatesData> getCoronaVirusDataFromExternalSource(ExternalDataService eds) {
			Logger log = LoggerFactory.getLogger(Regions.class);
			log.info("In the Regions enum for " + this.name() + " about to call makeDataListFromExternalSource with " + eds.toString());
			return eds.makeDataListFromExternalSource(this.name());
		}
	};
	
	public abstract RegionData getRegionData();
	public abstract <T extends CanonicalData> List<T> getCoronaVirusDataFromExternalSource(ExternalDataService dataService);
}
