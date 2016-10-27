/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.jevalab.azure.cbt;

import com.jevalab.azure.persistence.CbtRecord;
import com.jevalab.azure.persistence.Question;
import java.util.Collection;
import java.util.List;

public class JsonQuestion {
	private CbtRecord rec;
	private Collection<Question> questions;
	private List<JsonEnglishCategory> englishQuestions;

	public CbtRecord getRec() {
		return this.rec;
	}

	public void setRec(CbtRecord rec) {
		this.rec = rec;
	}

	public Collection<Question> getQuestions() {
		return this.questions;
	}

	public void setQuestions(Collection<Question> questions) {
		this.questions = questions;
	}

	public List<JsonEnglishCategory> getEnglishQuestions() {
		return this.englishQuestions;
	}

	public void setEnglishQuestions(List<JsonEnglishCategory> englishQuestions) {
		this.englishQuestions = englishQuestions;
	}

	public JsonQuestion(CbtRecord rec, Collection<Question> questions) {
		this.rec = rec;
		this.questions = questions;
	}

	public JsonQuestion(CbtRecord rec,
			List<JsonEnglishCategory> englishQuestions) {
		this.rec = rec;
		this.englishQuestions = englishQuestions;
	}
}