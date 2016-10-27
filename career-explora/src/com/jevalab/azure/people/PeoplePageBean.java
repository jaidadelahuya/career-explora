package com.jevalab.azure.people;

import java.io.Serializable;
import java.util.List;

public class PeoplePageBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4850532635441322063L;
	
	private String category,cursor;
	private int marker;
	private List<Person> people;
	@Override
	public String toString() {
		return "PeoplePageBean [category=" + category + ", cursor=" + cursor
				+ ", marker=" + marker + ", people=" + people + "]";
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getCursor() {
		return cursor;
	}
	public void setCursor(String cursor) {
		this.cursor = cursor;
	}
	public int getMarker() {
		return marker;
	}
	public void setMarker(int marker) {
		this.marker = marker;
	}
	public List<Person> getPeople() {
		return people;
	}
	public void setPeople(List<Person> people) {
		this.people = people;
	}
	
	

}
