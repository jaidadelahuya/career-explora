package com.jevalab.azure;

import java.util.List;

import com.jevalab.azure.persistence.Question;

public class JsonEnglishCategory {

	String instruction;
	List<Question> questions;
	public String getInstruction() {
		return instruction;
	}
	public void setInstruction(String instruction) {
		this.instruction = instruction;
	}
	public List<Question> getQuestions() {
		return questions;
	}
	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}
	public JsonEnglishCategory(String instruction, List<Question> questions) {
		super();
		this.instruction = instruction;
		this.questions = questions;
	}
	
}
