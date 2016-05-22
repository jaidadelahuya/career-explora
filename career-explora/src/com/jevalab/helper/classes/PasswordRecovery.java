package com.jevalab.helper.classes;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.datanucleus.api.jpa.annotations.Extension;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

@Entity
public class PasswordRecovery implements Serializable, Comparable<PasswordRecovery>{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Key key;
	
	@Extension(vendorName = "datanucleus", key = "gae.unindexed", value = "true")
	private boolean verified,email,mobile,defaultRecovery,username;
	
	
	
	public Key getKey() {
		return key;
	}
	public void setKey(Key key) {
		this.key = key;
	}
	public boolean isVerified() {
		return verified;
	}
	public void setVerified(boolean verified) {
		this.verified = verified;
	}
	public boolean isEmail() {
		return email;
	}
	public void setEmail(boolean email) {
		this.email = email;
	}
	public boolean isMobile() {
		return mobile;
	}
	public void setMobile(boolean mobile) {
		this.mobile = mobile;
	}
	
	
	
	public boolean isUsername() {
		return username;
	}
	public void setUsername(boolean username) {
		this.username = username;
	}
	public boolean isDefaultRecovery() {
		return defaultRecovery;
	}
	public void setDefaultRecovery(boolean defaultRecovery) {
		this.defaultRecovery = defaultRecovery;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		PasswordRecovery other = (PasswordRecovery) obj;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		return true;
	}
	public PasswordRecovery(String identifier, boolean verified, boolean email,
			boolean mobile, boolean defaultRecovery, boolean username) {
		super();
		this.key = KeyFactory.createKey(PasswordRecovery.class.getSimpleName(), identifier);
		this.verified = verified;
		this.email = email;
		this.mobile = mobile;
		this.defaultRecovery = defaultRecovery;
		this.username = username;
	}
	
	
	@Override
	public String toString() {
		return "PasswordRecovery [key=" + key + ", verified=" + verified
				+ ", email=" + email + ", mobile=" + mobile
				+ ", defaultRecovery=" + defaultRecovery + ", username="
				+ username + "]";
	}
	@Override
	public int compareTo(PasswordRecovery o) {
		
		return this.key.getName().compareTo(o.getKey().getName());
	}
	
	
	
}
