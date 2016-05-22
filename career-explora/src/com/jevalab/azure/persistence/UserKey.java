package com.jevalab.azure.persistence;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class UserKey implements Serializable {

	@Id
	private String key;
	private String validity;

	public String getKey() {
		return key;
	}
	
	

	public String getValidity() {
		return validity;
	}

	public void setValidity(String validity) {
		this.validity = validity;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public UserKey(String key) {
		super();
		this.key = key;
	}

	@Override
	public int hashCode() {
		final int prime = 11;
		int result = 31;
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserKey other = (UserKey) obj;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		return true;
	}
	
	
}
