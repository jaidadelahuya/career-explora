package com.jevalab.azure.persistence;

import java.io.Serializable;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;



import org.datanucleus.api.jpa.annotations.Extension;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;


@Entity
public class CareerClusterRecord implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Key key;
	
	@Extension(vendorName = "datanucleus", key = "gae.unindexed", value = "true")
	@OneToMany(cascade = CascadeType.ALL)
	private Map<String , Integer> record;

	public CareerClusterRecord(String userid) {
		key =  KeyFactory.createKey(CareerClusterRecord.class.getSimpleName(), userid);
	}

	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public Map<String, Integer> getRecord() {
		return record;
	}

	public void setRecord(Map<String, Integer> record) {
		this.record = record;
	}
	
	
	
	
}



















