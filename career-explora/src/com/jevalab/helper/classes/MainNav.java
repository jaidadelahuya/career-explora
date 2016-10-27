package com.jevalab.helper.classes;

import java.io.Serializable;

public class MainNav implements Serializable {
	private static final long serialVersionUID = -4234158750929701809L;
	private Boolean home;
	private Boolean notification;
	private Boolean cbt;
	private Boolean careerPlus;
	private Boolean collections;
	private Boolean communities;
	private Boolean people;
	private Boolean books;
	private Boolean news;
	private Boolean videos;
	private Boolean preference;
	private Boolean feedback;
	private Boolean help;
	private Boolean profile;

	private void offFields() {
		this.home = Boolean.valueOf(false);
		this.notification = Boolean.valueOf(false);
		this.cbt = Boolean.valueOf(false);
		this.careerPlus = Boolean.valueOf(false);
		this.collections = Boolean.valueOf(false);
		this.communities = Boolean.valueOf(false);
		this.people = Boolean.valueOf(false);
		this.books = Boolean.valueOf(false);
		this.news = Boolean.valueOf(false);
		this.videos = Boolean.valueOf(false);
		this.preference = Boolean.valueOf(false);
		this.feedback = Boolean.valueOf(false);
		this.help = Boolean.valueOf(false);
		this.profile = Boolean.valueOf(false);
	}

	public void setNone() {
		offFields();
	}
	
	

	public Boolean getProfile() {
		return profile;
	}

	public void setProfile(Boolean profile) {
		offFields();
		this.profile = profile;
	}

	public Boolean getHome() {
		return this.home;
	}

	public void setHome(Boolean home) {
		offFields();
		this.home = home;
	}

	public Boolean getNotification() {
		return this.notification;
	}

	public void setNotification(Boolean notification) {
		offFields();
		this.notification = notification;
	}

	public Boolean getCbt() {
		return this.cbt;
	}

	public void setCbt(Boolean cbt) {
		offFields();
		this.cbt = cbt;
	}

	public Boolean getCareerPlus() {
		return this.careerPlus;
	}

	public void setCareerPlus(Boolean careerPlus) {
		offFields();
		this.careerPlus = careerPlus;
	}

	public Boolean getCollections() {
		return this.collections;
	}

	public void setCollections(Boolean collections) {
		offFields();
		this.collections = collections;
	}

	public Boolean getCommunities() {
		return this.communities;
	}

	public void setCommunities(Boolean communities) {
		offFields();
		this.communities = communities;
	}

	public Boolean getPeople() {
		return this.people;
	}

	public void setPeople(Boolean people) {
		offFields();
		this.people = people;
	}

	public Boolean getBooks() {
		return this.books;
	}

	public void setBooks(Boolean books) {
		offFields();
		this.books = books;
	}

	public Boolean getNews() {
		return this.news;
	}

	public void setNews(Boolean news) {
		offFields();
		this.news = news;
	}

	public Boolean getVideos() {
		return this.videos;
	}

	public void setVideos(Boolean videos) {
		offFields();
		this.videos = videos;
	}

	public Boolean getPreference() {
		return this.preference;
	}

	public void setPreference(Boolean preference) {
		offFields();
		this.preference = preference;
	}

	public Boolean getFeedback() {
		return this.feedback;
	}

	public void setFeedback(Boolean feedback) {
		offFields();
		this.feedback = feedback;
	}

	public Boolean getHelp() {
		return this.help;
	}

	public void setHelp(Boolean help) {
		offFields();
		this.help = help;
	}

	public String toString() {
		return "MainNav [home=" + this.home + ", notification="
				+ this.notification + ", cbt=" + this.cbt + ", careerPlus="
				+ this.careerPlus + ", collections=" + this.collections
				+ ", communities=" + this.communities + ", people="
				+ this.people + ", books=" + this.books + ", news=" + this.news
				+ ", videos=" + this.videos + ", preference=" + this.preference
				+ ", feedback=" + this.feedback + ", help=" + this.help + "]";
	}
}