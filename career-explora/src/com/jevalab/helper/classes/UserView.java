package com.jevalab.helper.classes;

import java.io.Serializable;

public class UserView implements Serializable, DisplayableUser{
	
	private String firstName, middlename, lastName, cover, picture, id;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id =id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddlename() {
		return middlename;
	}

	public void setMiddlename(String middlename) {
		this.middlename = middlename;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	@Override
	public String toString() {
		return "UserView [firstName=" + firstName + ", middlename="
				+ middlename + ", lastName=" + lastName + ", cover=" + cover
				+ ", picture=" + picture + ", id=" + id + "]";
	}

	
	
	

}
