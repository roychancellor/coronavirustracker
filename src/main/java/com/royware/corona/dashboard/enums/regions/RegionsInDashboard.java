package com.royware.corona.dashboard.enums.regions;

public enum RegionsInDashboard {
	USA(328200000, RegionTypes.COUNTRY, "United States"),
	USA_NO_NY(328200000 - 19450000, RegionTypes.COUNTRY, "U.S. w/o N.Y."),
	AUS(24990000, RegionTypes.COUNTRY, "Australia"),
	CAN(37590000, RegionTypes.COUNTRY, "Canada"),
	CHN(1393000000, RegionTypes.COUNTRY, "China"),
	DEU(83020000, RegionTypes.COUNTRY, "Germany"),
	ESP(46940000, RegionTypes.COUNTRY, "Spain"),
	FRA(66990000, RegionTypes.COUNTRY, "France"),
	GBR(66650000, RegionTypes.COUNTRY, "Great Britain"),
	IND(1390885000, RegionTypes.COUNTRY, "India"),
	ITA(60360000, RegionTypes.COUNTRY, "Italy"),
	JPN(126500000, RegionTypes.COUNTRY, "Japan"),
	KOR(51640000, RegionTypes.COUNTRY, "South Korea"),
	MEX(126200000, RegionTypes.COUNTRY, "Mexico"),
	NOR(5638000, RegionTypes.COUNTRY, "Norway"),
	PRI(3194000, RegionTypes.COUNTRY, "Puerto Rico"),
	SGP(5639000, RegionTypes.COUNTRY, "Singapore"),
	SWE(10230000, RegionTypes.COUNTRY, "Sweden"),
	AL(4903000, RegionTypes.STATE, "Alabama"),
	AK(731545, RegionTypes.STATE, "Alaska"),
	AR(3018000, RegionTypes.STATE, "Arkansas"),
	AZ(7279000, RegionTypes.STATE, "Arizona"),
	CA(39510000, RegionTypes.STATE, "California"),
	CO(5759000, RegionTypes.STATE, "Colorado"),
	CT(3565000, RegionTypes.STATE, "Connecticut"),
	DE(973764, RegionTypes.STATE, "Delaware"),
	FL(21480000, RegionTypes.STATE, "Florida"),
	GA(10620000, RegionTypes.STATE, "Georgia"),
	HI(1416000, RegionTypes.STATE, "Hawaii"),
	IA(3155000, RegionTypes.STATE, "Iowa"),
	ID(1787000, RegionTypes.STATE, "Idaho"),
	IL(12670000, RegionTypes.STATE, "Illinois"),
	IN(6732000, RegionTypes.STATE, "Indiana"),
	KS(2913000, RegionTypes.STATE, "Kansas"),
	KY(4468000, RegionTypes.STATE, "Kentucky"),
	LA(3990000, RegionTypes.STATE, "Louisiana"),
	MA(6893000, RegionTypes.STATE, "Massachusetts"),
	MD(6046000, RegionTypes.STATE, "Maryland"),
	ME(1344000, RegionTypes.STATE, "Maine"),
	MI(9987000, RegionTypes.STATE, "Michigan"),
	MN(5640000, RegionTypes.STATE, "Minnesota"),
	MO(6137000, RegionTypes.STATE, "Missouri"),
	MS(2976000, RegionTypes.STATE, "Mississippi"),
	MT(1069000, RegionTypes.STATE, "Montana"),
	NC(10490000, RegionTypes.STATE, "North Carolina"),
	ND(762062, RegionTypes.STATE, "North Dakota"),
	NE(1934000, RegionTypes.STATE, "Nebraska"),
	NH(1360000, RegionTypes.STATE, "New Hampshire"),
	NJ(8882000, RegionTypes.STATE, "New Jersey"),
	NM(2097000, RegionTypes.STATE, "New Mexico"),
	NV(3080000, RegionTypes.STATE, "Nevada"),
	NY(19450000, RegionTypes.STATE, "New York"),
	OH(11690000, RegionTypes.STATE, "Ohio"),
	OK(3957000, RegionTypes.STATE, "Oklahoma"),
	OR(4218000, RegionTypes.STATE, "Oregon"),
	PA(12800000, RegionTypes.STATE, "Pennsylvania"),
	RI(1059000, RegionTypes.STATE, "Rhode Island"),
	SC(5149000, RegionTypes.STATE, "South Carolina"),
	SD(884659, RegionTypes.STATE, "South Dakota"),
	TN(6829000, RegionTypes.STATE, "Tennessee"),
	TX(29000000, RegionTypes.STATE, "Texas"),
	UT(3206000, RegionTypes.STATE, "Utah"),
	VA(8536000, RegionTypes.STATE, "Virginia"),
	VT(623989, RegionTypes.STATE, "Vermont"),
	WA(7615000, RegionTypes.STATE, "Washington"),
	WV(1792000, RegionTypes.STATE, "West Virginia"),
	WI(5822000, RegionTypes.STATE, "Wisconsin"),
	WY(578759, RegionTypes.STATE, "Wyoming"),
	DC(702455, RegionTypes.STATE, "District of Columbia");
	
	private final int regionPopulation;
	private final RegionTypes regionType;
	private final String label;
	private RegionsInDashboard(int regionPop, RegionTypes regionType, String label) {
		this.regionPopulation = regionPop;
		this.regionType = regionType;
		this.label = label;
	}
	
	public int getPopulation() {
		return this.regionPopulation;
	}
	
	public RegionTypes getRegionType() {
		return this.regionType;
	}
	
	public String getLabel() {
		return this.label;
	}
	
	public static RegionsInDashboard valueOfLabel(String label) {
		for(RegionsInDashboard e : values()) {
			if(label.equalsIgnoreCase(e.label)) {
				return e;
			}
		}
		return null;
	}
	
	public static RegionsInDashboard valueOfEnum(String enumAsString) {
		for(RegionsInDashboard e : values()) {
			if(enumAsString.equalsIgnoreCase(e.name())) {
				return e;
			}
		}
		return null;
	}
}
