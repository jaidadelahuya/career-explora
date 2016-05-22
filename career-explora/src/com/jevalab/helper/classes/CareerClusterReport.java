package com.jevalab.helper.classes;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CareerClusterReport implements Serializable {

	private Map<String,String> strongClusters,veryStrongClusters;
	private int scCount, vscCount;
	
	

	public int getScCount() {
		return scCount;
	}

	public void setScCount(int scCount) {
		this.scCount = scCount;
	}

	public int getVscCount() {
		return vscCount;
	}

	public void setVscCount(int vscCount) {
		this.vscCount = vscCount;
	}

	public Map<String, String> getStrongClusters() {
		return strongClusters;
	}

	public void setStrongClusters(Map<String, String> strongClusters) {
		this.strongClusters = strongClusters;
	}

	public Map<String, String> getVeryStrongClusters() {
		return veryStrongClusters;
	}

	public void setVeryStrongClusters(Map<String, String> veryStrongClusters) {
		this.veryStrongClusters = veryStrongClusters;
	}

	@Override
	public String toString() {
		return "CareerClusterReport [strongClusters=" + strongClusters
				+ ", veryStrongClusters=" + veryStrongClusters + "]";
	}

	public CareerClusterReport(Map<String,String> clusters) {
		this.strongClusters = getClusters(clusters,12,16);
		this.veryStrongClusters = getClusters(clusters,16,21);
		this.scCount = this.strongClusters.size();
		this.vscCount = this.veryStrongClusters.size();
	}

	private Map<String, String> getClusters(Map<String, String> clusters, int from, int to) {
		Set<String> keys = clusters.keySet();
		Map<String,String> map = new HashMap<>();
		for(String key:keys) {
			int i = Integer.parseInt(clusters.get(key));
			if(i >= from && i < to) {
				map.put(key, clusters.get(key));
			}
		}
		return map;
	}
	
	
	
}
