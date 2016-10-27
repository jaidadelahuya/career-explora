/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.jevalab.azure.careerplus;

public class CareerClusterResult {
	private String clusterName;
	private String value;

	public String getClusterName() {
		return this.clusterName;
	}

	public void setClusterName(String clusterName) {
		this.clusterName = clusterName;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public CareerClusterResult(String clusterName, String value) {
		this.clusterName = clusterName;
		this.value = value;
	}
}