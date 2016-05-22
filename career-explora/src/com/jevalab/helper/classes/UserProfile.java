package com.jevalab.helper.classes;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserProfile implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<String> values,clusters,talents,mits,learntSkills,skillToLearn,styles;
	private int valuesCounter,clustersCounter,talentsCounter,mitsCounter,learntSkilsCounter,skillsToLearnCounter,stylesCounter;
	private String firstName,lastName,middleName,gender,dateOfBirth,email,school,state,country,picture,cover,attends,lastSeenDate;
	private boolean currentUser;
	
	
	public ModuleInfo getModuleInfo (String listName) {
		ModuleInfo mi = new ModuleInfo();
		if(listName.equalsIgnoreCase("values")) {
			mi.setList(values);
			mi.setCounter(valuesCounter);
			return mi;
		} else if(listName.equalsIgnoreCase("styles")) {
			mi.setList(styles);
			mi.setCounter(stylesCounter);
			return mi;
		} else if(listName.equalsIgnoreCase("SKILLS_TO_LEARN")) {
			mi.setList(skillToLearn);
			mi.setCounter(skillsToLearnCounter);
			return mi;
		} else if(listName.equalsIgnoreCase("SKILLS_LEARNT")) {
			mi.setList(learntSkills);
			mi.setCounter(learntSkilsCounter);
			return mi;
		} else if(listName.equalsIgnoreCase("TALENTS")) {
			mi.setList(talents);
			mi.setCounter(talentsCounter);
			return mi;
		} else if(listName.equalsIgnoreCase("MITS")) {
			mi.setList(mits);
			mi.setCounter(mitsCounter);
			return mi;
		} else if(listName.equalsIgnoreCase("CLUSTERS")) {
			mi.setList(clusters);
			mi.setCounter(clustersCounter);
			return mi;
		} else {
			return mi;
		}
	}
	
	public void setCounter(String listName, int counter) {
		if(listName.equalsIgnoreCase("values")) {
			valuesCounter = counter;
		} else if(listName.equalsIgnoreCase("styles")) {
			stylesCounter=counter;
		} else if(listName.equalsIgnoreCase("SKILLS_TO_LEARN")) {
			skillsToLearnCounter = counter;
		} else if(listName.equalsIgnoreCase("SKILLS_LEARNT")) {
			learntSkilsCounter = counter;
		} else if(listName.equalsIgnoreCase("TALENTS")) {
			talentsCounter=counter;
		} else if(listName.equalsIgnoreCase("MITS")) {
			mitsCounter=counter;
		} else if(listName.equalsIgnoreCase("CLUSTERS")) {
			clustersCounter=counter;
		}
		
	}
	
	public boolean isCurrentUser() {
		return currentUser;
	}



	public void setCurrentUser(boolean currentUser) {
		this.currentUser = currentUser;
	}



	public String getLastSeenDate() {
		return lastSeenDate;
	}
	public void setLastSeenDate(String lastSeenDate) {
		this.lastSeenDate = lastSeenDate;
	}
	public int getValuesCounter() {
		return valuesCounter;
	}
	public void setValuesCounter(int valuesCounter) {
		this.valuesCounter = valuesCounter;
	}
	public int getClustersCounter() {
		return clustersCounter;
	}
	public void setClustersCounter(int clustersCounter) {
		this.clustersCounter = clustersCounter;
	}
	public int getTalentsCounter() {
		return talentsCounter;
	}
	public void setTalentsCounter(int talentsCounter) {
		this.talentsCounter = talentsCounter;
	}
	public int getMitsCounter() {
		return mitsCounter;
	}
	public void setMitsCounter(int mitsCounter) {
		this.mitsCounter = mitsCounter;
	}
	public int getLearntSkilsCounter() {
		return learntSkilsCounter;
	}
	public void setLearntSkilsCounter(int learntSkilsCounter) {
		this.learntSkilsCounter = learntSkilsCounter;
	}
	public int getSkillsToLearnCounter() {
		return skillsToLearnCounter;
	}
	public void setSkillsToLearnCounter(int skillsToLearnCounter) {
		this.skillsToLearnCounter = skillsToLearnCounter;
	}
	public int getStylesCounter() {
		return stylesCounter;
	}
	public void setStylesCounter(int stylesCounter) {
		this.stylesCounter = stylesCounter;
	}
	public List<String> getValues() {
		return values;
	}
	public void setValues(List<String> values) {
		this.values = values;
	}
	public List<String> getClusters() {
		return clusters;
	}
	public void setClusters(List<String> clusters) {
		this.clusters = clusters;
	}
	public List<String> getTalents() {
		return talents;
	}
	public void setTalents(List<String> talents) {
		this.talents = talents;
	}
	public List<String> getMits() {
		return mits;
	}
	public void setMits(List<String> mits) {
		this.mits = mits;
	}
	public List<String> getLearntSkills() {
		return learntSkills;
	}
	public void setLearntSkills(List<String> learntSkills) {
		this.learntSkills = learntSkills;
	}
	public List<String> getSkillToLearn() {
		return skillToLearn;
	}
	public void setSkillToLearn(List<String> skillToLearn) {
		this.skillToLearn = skillToLearn;
	}
	public List<String> getStyles() {
		return styles;
	}
	public void setStyles(List<String> styles) {
		this.styles = styles;
	}
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getMiddleName() {
		return middleName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSchool() {
		return school;
	}
	public void setSchool(String school) {
		this.school = school;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	public String getCover() {
		return cover;
	}
	public void setCover(String cover) {
		this.cover = cover;
	}
	public String getAttends() {
		return attends;
	}
	public void setAttends(String attends) {
		this.attends = attends;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "UserProfile [values=" + values + ", clusters=" + clusters
				+ ", talents=" + talents + ", mits=" + mits + ", learntSkills="
				+ learntSkills + ", skillToLearn=" + skillToLearn + ", styles="
				+ styles + ", valuesCounter=" + valuesCounter
				+ ", clustersCounter=" + clustersCounter + ", talentsCounter="
				+ talentsCounter + ", mitsCounter=" + mitsCounter
				+ ", learntSkilsCounter=" + learntSkilsCounter
				+ ", skillsToLearnCounter=" + skillsToLearnCounter
				+ ", stylesCounter=" + stylesCounter + ", firstName="
				+ firstName + ", lastName=" + lastName + ", middleName="
				+ middleName + ", gender=" + gender + ", dateOfBirth="
				+ dateOfBirth + ", email=" + email + ", school=" + school
				+ ", state=" + state + ", country=" + country + ", picture="
				+ picture + ", cover=" + cover + ", attends=" + attends
				+ ", lastSeenDate=" + lastSeenDate + ", currentUser="
				+ currentUser + "]";
	}
	
	
}
