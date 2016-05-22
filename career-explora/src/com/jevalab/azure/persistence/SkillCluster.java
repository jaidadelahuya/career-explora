package com.jevalab.azure.persistence;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

@Entity
public class SkillCluster {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Key key;
	
	private String clusterName,skillName;
	
	public SkillCluster(String clusterName, String skillName) {
		
		key = KeyFactory.createKey(SkillCluster.class.getSimpleName(), clusterName+skillName);
		this.clusterName = clusterName;
		this.skillName = skillName;
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

	public String getSkillName() {
		return skillName;
	}

	public void setSkillName(String skillName) {
		this.skillName = skillName;
	}
	
	

}
