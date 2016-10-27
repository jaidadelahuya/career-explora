package com.jevalab.azure.persistence;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
public class CbtRecord implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8296979764354266338L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Key key;
	
	@Extension(vendorName = "datanucleus", key = "gae.unindexed", value = "true")
	@OneToMany(cascade = CascadeType.ALL)
	private List<CbtTest> tests;

	public CbtRecord(String id) {
		key = KeyFactory.createKey(CbtRecord.class.getSimpleName(), id);
		tests = new ArrayList<CbtTest>();
		
	}

	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public List<CbtTest> getTests() {
		return tests;
	}

	public void setTests(List<CbtTest> tests) {
		this.tests = tests;
	}
	
	
	
	

}
