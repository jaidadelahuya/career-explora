package com.jevalab.azure.persistence;



import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;



import org.datanucleus.api.jpa.annotations.Extension;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

@Entity
public class CValue {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Key key;
	
	@Basic
	private String valueName;
	
	@Basic
	@Extension(vendorName = "datanucleus", key = "gae.unindexed", value = "true")
	private String description;
	
	
	@Transient
	private String nameDescription;
	
	@Basic
	private boolean selected;
	
	

	public String getNameDescription() {
		return nameDescription;
	}

	public void setNameDescription(String nameDescription) {
		this.nameDescription = nameDescription;
	}

	public Key getKey() {
		return key;
	}
	
	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public String getValueName() {
		return valueName;
	}

	public void setValueName(String valueName) {
		this.valueName = valueName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	

	public CValue(String nameDescription) {
	
		valueName = deriveName(nameDescription);
		description = deriveDescription(nameDescription);
		key = KeyFactory.createKey(CValue.class.getSimpleName(), valueName);
	}

	private String deriveDescription(String nameDescription) {
		int si = nameDescription.indexOf('-') + 1;
		String desc = nameDescription.substring(si, nameDescription.indexOf(',')).trim();
		return desc;
	}

	private String deriveName(String nameDescription) {
	
		String name = nameDescription.substring(0, nameDescription.indexOf('-')).trim();
		return name;
	}
	
	
}
