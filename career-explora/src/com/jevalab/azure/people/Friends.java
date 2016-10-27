package com.jevalab.azure.people;

import java.io.Serializable;
import java.util.List;

import com.google.appengine.api.datastore.Key;

public class Friends implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5159426651121995540L;
	
	private Key  id;
	private List<Key> friends;

	public List<Key> getFriends() {
		return friends;
	}

	public void setFriends(List<Key> friends) {
		this.friends = friends;
	}

	public Key getId() {
		return id;
	}

	public void setId(Key id) {
		this.id = id;
	}
	
	
}
