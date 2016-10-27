package com.jevalab.azure.persistence;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.datanucleus.api.jpa.annotations.Extension;



@Entity
public class PersonalStyle implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4919194394684647091L;

	@Id
	long id = 1l;
	
	@Extension(vendorName = "datanucleus", key = "gae.unindexed", value = "true")
	private List<String> styles;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public List<String> getStyles() {
		return styles;
	}

	public void setStyles(List<String> styles) {
		this.styles = styles;
	}

	public PersonalStyle(List<String> styles) {
		super();
		this.styles = styles;
	}
	
	

}
