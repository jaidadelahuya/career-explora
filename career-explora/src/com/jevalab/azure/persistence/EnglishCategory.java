package com.jevalab.azure.persistence;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;



import org.datanucleus.api.jpa.annotations.Extension;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

@Entity
public class EnglishCategory implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Key key;
	
	@Extension(vendorName = "datanucleus", key = "gae.unindexed", value = "true")
	private String instruction,categoryName;
	
	public EnglishCategory(String categoryName,String instruction) {
		super();
		key = KeyFactory.createKey(EnglishCategory.class.getSimpleName(), categoryName);
		this.categoryName = categoryName;
		this.instruction = instruction;
	}
	public String getInstruction() {
		return instruction;
	}
	public void setInstruction(String instruction) {
		this.instruction = instruction;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public Key getKey() {
		return key;
	}
	public void setKey(Key key) {
		this.key = key;
	}
	
	
	
}
