/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.jevalab.azure.careerplus;

import javax.persistence.Transient;

public class CareerClusterQuestion {
	private String clusterName;
	private String description;
	private String question;

	@Transient
	private int userChoice;

	public CareerClusterQuestion(String clusterName, String description,
			String question) {
		this.clusterName = clusterName;
		this.description = description;
		this.question = question;
	}

	public String getClusterName() {
		return this.clusterName;
	}

	public void setClusterName(String clusterName) {
		this.clusterName = clusterName;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getQuestion() {
		return this.question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public int getUserChoice() {
		return this.userChoice;
	}

	public void setUserChoice(int userChoice) {
		this.userChoice = userChoice;
	}
}