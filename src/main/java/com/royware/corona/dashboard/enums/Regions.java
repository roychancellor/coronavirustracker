package com.royware.corona.dashboard.enums;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.royware.corona.dashboard.interfaces.CanonicalData;
import com.royware.corona.dashboard.interfaces.ExternalDataService;
import com.royware.corona.dashboard.model.RegionData;
import com.royware.corona.dashboard.model.UnitedStatesData;
import com.royware.corona.dashboard.model.WorldData;

@Service
public enum Regions {
	USA {
		@Override
		public RegionData getRegionData() {
			return new RegionData(328200000, RegionLevels.COUNTRY, "United States");
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<UnitedStatesData> getCoronaVirusDataFromExternalSource(ExternalDataService eds) {
			Logger log = LoggerFactory.getLogger(Regions.class);
			log.info("In the Regions enum for " + this.name() + " about to call makeDataListFromExternalSource with " + eds.toString());
			return eds.makeDataListFromExternalSource(CacheKeys.CACHE_KEY_US.toString());
		}
	},
	AUS {
		public RegionData getRegionData() {
			return new RegionData(24990000, RegionLevels.COUNTRY, "Australia");
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
			return new RegionData(37590000, RegionLevels.COUNTRY, "Canada");
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
			return new RegionData(1393000000, RegionLevels.COUNTRY, "China");
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
			return new RegionData(83020000, RegionLevels.COUNTRY, "Germany");
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
			return new RegionData(46940000, RegionLevels.COUNTRY, "Spain");
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
			return new RegionData(66990000, RegionLevels.COUNTRY, "France");
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
			return new RegionData(66650000, RegionLevels.COUNTRY, "Great Britain");
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
			return new RegionData(60360000, RegionLevels.COUNTRY, "Italy");
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
			return new RegionData(126500000, RegionLevels.COUNTRY, "Japan");
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
			return new RegionData(51640000, RegionLevels.COUNTRY, "South Korea");
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
			return new RegionData(126200000, RegionLevels.COUNTRY, "Mexico");
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
			return new RegionData(5638000, RegionLevels.COUNTRY, "Norway");
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
			return new RegionData(3194000, RegionLevels.COUNTRY, "Puerto Rico");
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
			return new RegionData(5639000, RegionLevels.COUNTRY, "Singapore");
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
			return new RegionData(10230000, RegionLevels.COUNTRY, "Sweden");
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
					RegionLevels.COUNTRY, "U.S. without New York State");
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
			return new RegionData(4903000, RegionLevels.STATE, "Alabama");
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
			return new RegionData(731545, RegionLevels.STATE, "Alaska");
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
			return new RegionData(7279000, RegionLevels.STATE, "Arizona");
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
			return new RegionData(3018000, RegionLevels.STATE, "Arkansas");
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
			return new RegionData(39510000, RegionLevels.STATE, "California");
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
			return new RegionData(5759000, RegionLevels.STATE, "Colorado");
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
			return new RegionData(3565000, RegionLevels.STATE, "Connecticut");
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
			return new RegionData(973764, RegionLevels.STATE, "Delaware");
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
			return new RegionData(21480000, RegionLevels.STATE, "Florida");
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
			return new RegionData(10620000, RegionLevels.STATE, "Georgia");
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
			return new RegionData(1416000, RegionLevels.STATE, "Hawaii");
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
			return new RegionData(1787000, RegionLevels.STATE, "Idaho");
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
			return new RegionData(12670000, RegionLevels.STATE, "Illinois");
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
			return new RegionData(6732000, RegionLevels.STATE, "Indiana");
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
			return new RegionData(3155000, RegionLevels.STATE, "Iowa");
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
			return new RegionData(2913000, RegionLevels.STATE, "Kansas");
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
			return new RegionData(4468000, RegionLevels.STATE, "Kentucky");
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
			return new RegionData(3990000, RegionLevels.STATE, "Louisiana");
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
			return new RegionData(1344000, RegionLevels.STATE, "Maine");
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
			return new RegionData(6046000, RegionLevels.STATE, "Maryland");
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
			return new RegionData(6893000, RegionLevels.STATE, "Massachusetts");
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
			return new RegionData(9987000, RegionLevels.STATE, "Michigan");
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
			return new RegionData(5640000, RegionLevels.STATE, "Minnesota");
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
			return new RegionData(2976000, RegionLevels.STATE, "Mississippi");
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
			return new RegionData(6137000, RegionLevels.STATE, "Missouri");
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
			return new RegionData(1069000, RegionLevels.STATE, "Montana");
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
			return new RegionData(1934000, RegionLevels.STATE, "Nebraska");
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
			return new RegionData(3080000, RegionLevels.STATE, "Nevada");
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
			return new RegionData(1360000, RegionLevels.STATE, "New Hampshire");
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
			return new RegionData(8882000, RegionLevels.STATE, "New Jersey");
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
			return new RegionData(2097000, RegionLevels.STATE, "New Mexico");
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
			return new RegionData(19450000, RegionLevels.STATE, "New York");
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
			return new RegionData(10490000, RegionLevels.STATE, "North Carolina");
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
			return new RegionData(762062, RegionLevels.STATE, "North Dakota");
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
			return new RegionData(11690000, RegionLevels.STATE, "Ohio");
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
			return new RegionData(3957000, RegionLevels.STATE, "Oklahoma");
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
			return new RegionData(4218000, RegionLevels.STATE, "Oregon");
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
			return new RegionData(12800000, RegionLevels.STATE, "Pennsylvania");
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
			return new RegionData(1059000, RegionLevels.STATE, "Rhode Island");
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
			return new RegionData(5149000, RegionLevels.STATE, "South Carolina");
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
			return new RegionData(884659, RegionLevels.STATE, "South Dakota");
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
			return new RegionData(6829000, RegionLevels.STATE, "Tennessee");
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
			return new RegionData(29000000, RegionLevels.STATE, "Texas");
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
			return new RegionData(3206000, RegionLevels.STATE, "Utah");
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
			return new RegionData(623989, RegionLevels.STATE, "Vermont");
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
			return new RegionData(8536000, RegionLevels.STATE, "Virginia");
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
			return new RegionData(7615000, RegionLevels.STATE, "Washington");
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
			return new RegionData(1792000, RegionLevels.STATE, "West Virginia");
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
			return new RegionData(5822000, RegionLevels.STATE, "Wisconsin");
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
			return new RegionData(578759, RegionLevels.STATE, "Wyoming");
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
			return new RegionData(702455, RegionLevels.STATE, "District of Columbia");
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
