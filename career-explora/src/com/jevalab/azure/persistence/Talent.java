package com.jevalab.azure.persistence;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Basic;
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
public class Talent implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Key key;
	
	@Basic
	private String category;

	@Basic
	@Extension(vendorName = "datanucleus", key = "gae.unindexed", value = "true")
	private String name;
	
	@Extension(vendorName = "datanucleus", key = "gae.unindexed", value = "true")
	@OneToMany(cascade = CascadeType.ALL)
	private List<String> questions;
	
	public Talent() {
		super();
	}


	public Talent(String name) {
	
		Key key = KeyFactory.createKey(Talent.class.getSimpleName(), name);
		this.key = key;
		this.name = name;
	}
	
	
	public String getCategory() {
		return category;
	}


	public void setCategory(String category) {
		this.category = category;
	}


	public List<String> getQuestions() {
		return questions;
	}


	public void setQuestions(List<String> questions) {
		this.questions = questions;
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
	

	@Override
	public String toString() {
		return "Talent [key=" + key + ", category=" + category + ", name="
				+ name + "]";
	}


	@Override
	public int hashCode() {
		int v1 = name.indexOf('a');
		int v2 = name.indexOf('e');
		int v3 = name.indexOf('i');
		int v4 = name.indexOf('o');
		int v5 = name.indexOf('u');
		int v6 = name.length();
		
		return v1*v2*v3*v4*v5*v6;
	}


	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Talent && ((Talent)obj).getName() == this.getName()) {
			return true;
		} else {
			return false;
		}
	}
	
	

}
