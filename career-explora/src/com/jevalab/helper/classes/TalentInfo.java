package com.jevalab.helper.classes;

import java.io.Serializable;

public class TalentInfo implements Serializable, Comparable<TalentInfo> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8679570476533755961L;
	
	String name,value,rating,category;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		TalentInfo other = (TalentInfo) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TalentInfo [name=" + name + ", value=" + value + ", rating="
				+ rating + ", category=" + category + "]";
	}

	public TalentInfo(String name, String value, String category) {
		super();
		this.name = name;
		this.value = value;
		this.rating = calculateRating(value);
		this.category = category;
	}

	private String calculateRating(String value) {
		int i = Integer.parseInt(value);
		if(i >= 8) {
			return "VS";
		} else if(i >= 6){
			return "S";
		} else {
			return null;
		}
	}

	@Override
	public int compareTo(TalentInfo o) {
		int v1 = Integer.parseInt(value);
		int v2 = Integer.parseInt(o.getValue());
		
		if (v1 > v2) {
			return 1;
		} else if(v1 == v2) {
			return 0;
		} else {
			return -1;
		}
	}
	
	
		
	
}
