package com.jevalab.azure.persistence;

import java.io.Serializable;
import java.util.List;

import com.google.appengine.api.datastore.Key;

public class Unit implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4725424331865724906L;
	
	private Key id;
	private Key community;
	private List<Key> discussions;
	private String name;
	@Override
	public String toString() {
		return "Unit [id=" + id + ", community=" + community + ", discussions="
				+ discussions + ", name=" + name + "]";
	}
	
	
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Key getId() {
		return id;
	}
	public void setId(Key id) {
		this.id = id;
	}
	public Key getCommunity() {
		return community;
	}
	public void setCommunity(Key community) {
		this.community = community;
	}
	public List<Key> getDiscussions() {
		return discussions;
	}
	public void setDiscussions(List<Key> discussions) {
		this.discussions = discussions;
	}
	public Unit() {
		id = GeneralController.ds.allocateIds(Unit.class.getSimpleName(), 1).getStart();
	}
	
	

}
