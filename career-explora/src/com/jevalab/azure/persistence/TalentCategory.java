package com.jevalab.azure.persistence;

import java.util.List;
import java.util.Set;

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
public class TalentCategory {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Key key;
	
	@Extension(vendorName = "datanucleus", key = "gae.unindexed", value = "true")
	private String category;
	
	@Basic
	@OneToMany(cascade = CascadeType.ALL)
	@Extension(vendorName = "datanucleus", key = "gae.unindexed", value = "true")
	private Set<Talent> talent;

	
	public TalentCategory(String category, Set<Talent> talent) {
		Key key = KeyFactory.createKey(TalentCategory.class.getSimpleName(), category);
		this.key = key;
		this.category = category;
		this.talent = talent;
	}
	
	

	public Key getKey() {
		return key;
	}



	public void setKey(Key key) {
		this.key = key;
	}



	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Set<Talent> getTalent() {
		return talent;
	}

	public void setTalent(Set<Talent> talent) {
		this.talent = talent;
	}



	@Override
	public String toString() {
		return "TalentCategory [key=" + key + ", category=" + category
				+ ", talent=" + talent + "]";
	}
	
	
}
