package com.jevalab.azure.persistence;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;



import org.datanucleus.api.jpa.annotations.Extension;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Text;


@Entity
public class Skill implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Key key;

	@Basic
	@Extension(vendorName = "datanucleus", key = "gae.unindexed", value = "true")
	private String name;
	
	@Basic
	@Extension(vendorName = "datanucleus", key = "gae.unindexed", value = "true")
	private Text description;
	
	

	public Skill(String name, Text description) {
		
		this(name);
		this.description = description;
	}
	
	

	public Skill(String name) {
		key = KeyFactory.createKey(Skill.class.getSimpleName(), name);
		this.name = name;
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

	public Text getDescription() {
		return description;
	}

	public void setDescription(Text description) {
		this.description = description;
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
		if(obj instanceof Skill && ((Skill)obj).getName() == this.getName()) {
			return true;
		} else {
			return false;
		}
	}
	
	

}
