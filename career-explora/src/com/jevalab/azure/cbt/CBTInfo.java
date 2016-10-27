/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.jevalab.azure.cbt;

import java.io.Serializable;

public class CBTInfo implements Serializable {
	private static final long serialVersionUID = -7409840509340989145L;
	private String subject;
	private String time;
	private String questions;
	private String passMark;

	public String getSubject() {
		return this.subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getTime() {
		return this.time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getQuestions() {
		return this.questions;
	}

	public void setQuestions(String questions) {
		this.questions = questions;
	}

	public String getPassMark() {
		return this.passMark;
	}

	public void setPassMark(String passMark) {
		this.passMark = passMark;
	}

	public String toString() {
		return "CBTInfo [subject=" + this.subject + ", time=" + this.time
				+ ", questions=" + this.questions + ", passMark="
				+ this.passMark + "]";
	}
}