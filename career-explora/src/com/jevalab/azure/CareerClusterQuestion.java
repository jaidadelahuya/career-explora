package com.jevalab.azure;

import javax.persistence.Transient;

public class CareerClusterQuestion {
	private String clusterName;
	private String description;
	private String question;
	@Transient
	private int userChoice;
	public CareerClusterQuestion(String clusterName, String description,
			String question) {
		super();
		this.clusterName = clusterName;
		this.description = description;
		this.question = question;
	}
	public String getClusterName() {
		return clusterName;
	}
	public void setClusterName(String clusterName) {
		this.clusterName = clusterName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public int getUserChoice() {
		return userChoice;
	}
	public void setUserChoice(int userChoice) {
		this.userChoice = userChoice;
	}
	
	
}
