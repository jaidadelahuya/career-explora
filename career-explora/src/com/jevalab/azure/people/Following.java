package com.jevalab.azure.people;

import java.io.Serializable;

import com.google.appengine.api.datastore.Key;

public class Following implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2964892599646441249L;
	
	private Key owner, following, id;

	@Override
	public String toString() {
		return "Following [owner=" + owner + ", following=" + following + "]";
	}

	
	public Key getId() {
		return id;
	}


	public void setId(Key id) {
		this.id = id;
	}


	public Key getOwner() {
		return owner;
	}

	public void setOwner(Key owner) {
		this.owner = owner;
	}

	public Key getFollowing() {
		return following;
	}

	public void setFollowing(Key following) {
		this.following = following;
	}
	
	

}
