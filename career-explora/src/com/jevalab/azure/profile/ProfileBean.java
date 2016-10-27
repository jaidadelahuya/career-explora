package com.jevalab.azure.profile;

import java.io.Serializable;
import java.util.Date;

public class ProfileBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1150607765968464421L;

	private String fName, lName, school, grade, backgroundImage, profileImage,
			interest, gender, email, hobbies, about, mit, careerclusters,
			multipleIntelligence, skillsToLearn, skillsLearnt;
	private Date birthDate;
	private int following, followers, friends, communities, collections,
			percentageComplete;
	public String getfName() {
		return fName;
	}
	public void setfName(String fName) {
		this.fName = fName;
	}
	public String getlName() {
		return lName;
	}
	public void setlName(String lName) {
		this.lName = lName;
	}
	public String getSchool() {
		return school;
	}
	public void setSchool(String school) {
		this.school = school;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public String getBackgroundImage() {
		return backgroundImage;
	}
	public void setBackgroundImage(String backgroundImage) {
		this.backgroundImage = backgroundImage;
	}
	public String getProfileImage() {
		return profileImage;
	}
	public void setProfileImage(String profileImage) {
		this.profileImage = profileImage;
	}
	public String getInterest() {
		return interest;
	}
	public void setInterest(String interest) {
		this.interest = interest;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getHobbies() {
		return hobbies;
	}
	public void setHobbies(String hobbies) {
		this.hobbies = hobbies;
	}
	public String getAbout() {
		return about;
	}
	public void setAbout(String about) {
		this.about = about;
	}
	public String getMit() {
		return mit;
	}
	public void setMit(String mit) {
		this.mit = mit;
	}
	public String getCareerclusters() {
		return careerclusters;
	}
	public void setCareerclusters(String careerclusters) {
		this.careerclusters = careerclusters;
	}
	public String getMultipleIntelligence() {
		return multipleIntelligence;
	}
	public void setMultipleIntelligence(String multipleIntelligence) {
		this.multipleIntelligence = multipleIntelligence;
	}
	public String getSkillsToLearn() {
		return skillsToLearn;
	}
	public void setSkillsToLearn(String skillsToLearn) {
		this.skillsToLearn = skillsToLearn;
	}
	public String getSkillsLearnt() {
		return skillsLearnt;
	}
	public void setSkillsLearnt(String skillsLearnt) {
		this.skillsLearnt = skillsLearnt;
	}
	public Date getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	public int getFollowing() {
		return following;
	}
	public void setFollowing(int following) {
		this.following = following;
	}
	public int getFollowers() {
		return followers;
	}
	public void setFollowers(int followers) {
		this.followers = followers;
	}
	public int getFriends() {
		return friends;
	}
	public void setFriends(int friends) {
		this.friends = friends;
	}
	public int getCommunities() {
		return communities;
	}
	public void setCommunities(int communities) {
		this.communities = communities;
	}
	public int getCollections() {
		return collections;
	}
	public void setCollections(int collections) {
		this.collections = collections;
	}
	public int getPercentageComplete() {
		return percentageComplete;
	}
	public void setPercentageComplete(int percentageComplete) {
		this.percentageComplete = percentageComplete;
	}
	
	

}
