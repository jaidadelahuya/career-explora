/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.jevalab.azure.cbt;

import com.jevalab.azure.persistence.Question;
import java.util.List;

public class JsonEnglishCategory {
	String instruction;
	List<Question> questions;

	public String getInstruction() {
		return this.instruction;
	}

	public void setInstruction(String instruction) {
		this.instruction = instruction;
	}

	public List<Question> getQuestions() {
		return this.questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

	public JsonEnglishCategory(String instruction, List<Question> questions) {
		this.instruction = instruction;
		this.questions = questions;
	}
}