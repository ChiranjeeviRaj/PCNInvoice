package uk.pcn.invoice.common;

public enum ServiceNames {
	YODEL("Yodel"), CITYLINK("city link");

	private String name;

	private ServiceNames(String name) {
		this.name = name;
	}

	public String getValue() {
		return this.name;
	}
}
