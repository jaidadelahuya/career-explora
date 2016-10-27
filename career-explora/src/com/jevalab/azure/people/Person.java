package com.jevalab.azure.people;

import java.io.Serializable;

public class Person implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7338489934689838762L;
	
	private String webKey, picture, name, grade, interest;
	private boolean friend, following;

	
	
	

	@Override
	public String toString() {
		return "Person [webKey=" + webKey + ", picture=" + picture + ", name="
				+ name + ", grade=" + grade + ", interest=" + interest
				+ ", friend=" + friend + ", following=" + following + "]";
	}



	public boolean isFriend() {
		return friend;
	}



	public void setFriend(boolean friend) {
		this.friend = friend;
	}



	public boolean isFollowing() {
		return following;
	}



	public void setFollowing(boolean following) {
		this.following = following;
	}



	public String getWebKey() {
		return webKey;
	}

	public void setWebKey(String webKey) {
		this.webKey = webKey;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getInterest() {
		return interest;
	}

	public void setInterest(String interest) {
		this.interest = interest;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((webKey == null) ? 0 : webKey.hashCode());
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
		Person other = (Person) obj;
		if (webKey == null) {
			if (other.webKey != null)
				return false;
		} else if (!webKey.equals(other.webKey))
			return false;
		return true;
	}
	
	

}
