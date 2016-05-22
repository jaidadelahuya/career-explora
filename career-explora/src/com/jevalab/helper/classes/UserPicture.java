package com.jevalab.helper.classes;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.datanucleus.api.jpa.annotations.Extension;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;


@Entity
public class UserPicture implements Serializable, Comparable<UserPicture> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Key key;
	
	@Basic
	@Extension(vendorName = "datanucleus", key = "gae.unindexed", value = "true")
	BlobKey blobkey;
	
	@Basic
	@Extension(vendorName = "datanucleus", key = "gae.unindexed", value = "true")
	String url;
	
	@Basic
	@Extension(vendorName = "datanucleus", key = "gae.unindexed", value = "true")
	boolean isCurrentPicture;
	
	@Basic
	@Extension(vendorName = "datanucleus", key = "gae.unindexed", value = "true")
	PictureType type;
	
	
	
	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public BlobKey getBlobkey() {
		return blobkey;
	}

	public void setBlobkey(BlobKey blobkey) {
		this.blobkey = blobkey;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public boolean isCurrentPicture() {
		return isCurrentPicture;
	}

	public void setCurrentPicture(boolean isCurrentPicture) {
		this.isCurrentPicture = isCurrentPicture;
	}

	public PictureType getType() {
		return type;
	}

	public void setType(PictureType type) {
		this.type = type;
	}
	
	public UserPicture(BlobKey blobkey, String url, boolean isCurrentPicture,
			PictureType type) {
		this.key = KeyFactory.createKey(UserPicture.class.getSimpleName(), blobkey.getKeyString());
		this.blobkey = blobkey;
		this.url = url;
		this.isCurrentPicture = isCurrentPicture;
		this.type = type;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((blobkey == null) ? 0 : blobkey.hashCode());
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		UserPicture other = (UserPicture) obj;
		if (blobkey == null) {
			if (other.blobkey != null)
				return false;
		} else if (!blobkey.equals(other.blobkey))
			return false;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		if (type != other.type)
			return false;
		return true;
	}

	@Embeddable
	enum PictureType {DISPLAY,COVER}

	@Override
	public int compareTo(UserPicture o) {
		return this.key.getName().compareTo(o.getKey().getName());
	}
}
