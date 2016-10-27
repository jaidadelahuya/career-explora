package com.jevalab.azure.persistence;

import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;



import org.datanucleus.api.jpa.annotations.Extension;

import com.google.appengine.api.datastore.Key;

@Entity
public class CareerPathway {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Key key;
	
	@Extension(vendorName = "datanucleus", key = "gae.unindexed", value = "true")
	private String name;
	
	@Basic
	@Extension(vendorName = "datanucleus", key = "gae.unindexed", value = "true")
	private List<String> careers;

	public CareerPathway(Key key, String name, List<String> careers) {
		super();
		this.key = key;
		this.name = name;
		this.careers = careers;
	}

	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getCareers() {
		return careers;
	}

	public void setCareers(List<String> careers) {
		this.careers = careers;
	}
	
	
}
