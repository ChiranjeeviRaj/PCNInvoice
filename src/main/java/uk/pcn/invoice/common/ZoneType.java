package uk.pcn.invoice.common;

public enum ZoneType {
	LOCAL_ZONE("LOCAL ZONE"), ZONE1("ZONE 1"), ZONE2("ZONE 2"), 
	ZONE3("ZONE 3"), ZONE4("ZONE 4"), ZONE5("ZONE 5"), ZONE6("ZONE 6");
	private String name;

	private ZoneType(String name) {
		this.name = name;
	}

	public String getValue() {
		return this.name;
	}
	
	public static ZoneType getZoneName(String name){
		for (ZoneType customerName : ZoneType.values()) {
			if(customerName.getValue().equals(name)){
				return customerName;
			}
		}
		return null;
	}
}
