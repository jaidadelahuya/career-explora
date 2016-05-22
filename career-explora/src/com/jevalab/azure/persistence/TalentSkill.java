package com.jevalab.azure.persistence;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

@Entity
public class TalentSkill {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Key key;
	
	private String talentName,SkillName;
	
	public TalentSkill(String talentName, String skillName) {
		
		key = KeyFactory.createKey(TalentSkill.class.getSimpleName(),talentName+skillName);
		this.talentName = talentName;
		SkillName = skillName;
	}

	

	public Key getKey() {
		return key;
	}



	public void setKey(Key key) {
		this.key = key;
	}



	public String getTalentName() {
		return talentName;
	}

	public void setTalentName(String talentName) {
		this.talentName = talentName;
	}

	public String getSkillName() {
		return SkillName;
	}

	public void setSkillName(String skillName) {
		SkillName = skillName;
	}
	
	
}
