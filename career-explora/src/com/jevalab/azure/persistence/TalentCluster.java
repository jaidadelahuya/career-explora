package com.jevalab.azure.persistence;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

@Entity
public class TalentCluster {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Key key;
	
	private String clusterName,talentName;
	
	

	public TalentCluster(String clusterName, String talentName) {
		
		key = KeyFactory.createKey(TalentCluster.class.getSimpleName(), clusterName+talentName);
		this.clusterName = clusterName;
		this.talentName = talentName;
	}
	
	

	public Key getKey() {
		return key;
	}



	public void setKey(Key key) {
		this.key = key;
	}



	public String getClusterName() {
		return clusterName;
	}

	public void setClusterName(String clusterName) {
		this.clusterName = clusterName;
	}

	public String getTalentName() {
		return talentName;
	}

	public void setTalentName(String talentName) {
		this.talentName = talentName;
	}
	
	
}
