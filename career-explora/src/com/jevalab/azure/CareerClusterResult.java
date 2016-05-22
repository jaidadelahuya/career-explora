package com.jevalab.azure;

public class CareerClusterResult {

	private String clusterName,value;

	public String getClusterName() {
		return clusterName;
	}

	public void setClusterName(String clusterName) {
		this.clusterName = clusterName;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public CareerClusterResult(String clusterName, String value) {
		super();
		this.clusterName = clusterName;
		this.value = value;
	}
	
	
}
