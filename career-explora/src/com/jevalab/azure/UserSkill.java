package com.jevalab.azure;

public class UserSkill {

	private String name, description,talentName;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


	public String getTalentName() {
		return talentName;
	}

	public void setTalentName(String talentName) {
		this.talentName = talentName;
	}

	public UserSkill(String name, String description, String talentName) {
		super();
		this.name = name;
		this.description = description;
		this.talentName = talentName;
	}
	
}
