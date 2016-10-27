package com.jevalab.azure.profile;

import java.io.Serializable;
import java.util.Date;


public class UserProfile implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String clusters, talents, mits, learntSkills,
			skillToLearn;
	private String firstName, lastName, middleName, gender, email,
			school, state, country, picture, cover, lastSeenDate, hobbies, about, grade, interest;
	private Date birthDate;
	private int following, followers, friends, communities, collections,
	percentageComplete;
	private boolean currentUser;

	

	public boolean isCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(boolean currentUser) {
		this.currentUser = currentUser;
	}

	public String getClusters() {
		return clusters;
	}

	public void setClusters(String clusters) {
		this.clusters = clusters;
	}

	public String getTalents() {
		return talents;
	}

	public void setTalents(String talents) {
		this.talents = talents;
	}

	public String getMits() {
		return mits;
	}

	public void setMits(String mits) {
		this.mits = mits;
	}

	public String getLearntSkills() {
		return learntSkills;
	}

	public void setLearntSkills(String learntSkills) {
		this.learntSkills = learntSkills;
	}

	public String getSkillToLearn() {
		return skillToLearn;
	}

	public void setSkillToLearn(String skillToLearn) {
		this.skillToLearn = skillToLearn;
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

	public String getLastSeenDate() {
		return lastSeenDate;
	}

	public void setLastSeenDate(String lastSeenDate) {
		this.lastSeenDate = lastSeenDate;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "UserProfile [clusters=" + clusters + ", talents=" + talents
				+ ", mits=" + mits + ", learntSkills=" + learntSkills
				+ ", skillToLearn=" + skillToLearn + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", middleName=" + middleName
				+ ", gender=" + gender + ", email=" + email + ", school="
				+ school + ", state=" + state + ", country=" + country
				+ ", picture=" + picture + ", cover=" + cover
				+ ", lastSeenDate=" + lastSeenDate + ", hobbies=" + hobbies
				+ ", about=" + about + ", grade=" + grade + ", interest="
				+ interest + ", birthDate=" + birthDate + ", following="
				+ following + ", followers=" + followers + ", friends="
				+ friends + ", communities=" + communities + ", collections="
				+ collections + ", percentageComplete=" + percentageComplete
				+ "]";
	}

	

}
