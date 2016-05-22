package com.jevalab.azure;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.jevalab.azure.persistence.CbtRecord;
import com.jevalab.azure.persistence.Question;

public class JsonQuestion  {

	private CbtRecord rec;
	private Collection<Question> questions;
	private List<JsonEnglishCategory> englishQuestions;

	public CbtRecord getRec() {
		return rec;
	}

	public void setRec(CbtRecord rec) {
		this.rec = rec;
	}

	public Collection<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(Collection<Question> questions) {
		this.questions = questions;
	}
	

	

	public List<JsonEnglishCategory> getEnglishQuestions() {
		return englishQuestions;
	}

	public void setEnglishQuestions(List<JsonEnglishCategory> englishQuestions) {
		this.englishQuestions = englishQuestions;
	}

	public JsonQuestion(CbtRecord rec, Collection<Question> questions) {
		super();
		this.rec = rec;
		this.questions = questions;
	}

	public JsonQuestion(CbtRecord rec,
			List<JsonEnglishCategory> englishQuestions) {
		super();
		this.rec = rec;
		this.englishQuestions = englishQuestions;
	}
	
}
