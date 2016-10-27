package com.jevalab.azure.persistence;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.datanucleus.api.jpa.annotations.Extension;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.jevalab.azure.AbstractTestResult;

@Entity
public class CbtTest extends AbstractTestResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Key key;
	
	@Extension(vendorName = "datanucleus", key = "gae.unindexed", value = "true")
	private String totalNoQuestions,noCorrect,noWrong,noUnanswered,speed,confidence,accuracy,overallperformance;
	
	

	public CbtTest(String subjectName) {
		key = KeyFactory.createKey(CbtTest.class.getSimpleName(), subjectName.toUpperCase());
		this.testName = subjectName;
	}
	
	

	public Key getKey() {
		return key;
	}



	public void setKey(Key key) {
		this.key = key;
	}



	public String getTestDate() {
		return testDate;
	}

	public void setTestDate(String testDate) {
		this.testDate = testDate;
	}

	public String getTotalNoQuestions() {
		return totalNoQuestions;
	}

	public void setTotalNoQuestions(String totalNoQuestions) {
		this.totalNoQuestions = totalNoQuestions;
	}

	public String getNoCorrect() {
		return noCorrect;
	}

	public void setNoCorrect(String noCorrect) {
		this.noCorrect = noCorrect;
	}

	public String getNoWrong() {
		return noWrong;
	}

	public void setNoWrong(String noWrong) {
		this.noWrong = noWrong;
	}

	public String getNoUnanswered() {
		return noUnanswered;
	}

	public void setNoUnanswered(String noUnanswered) {
		this.noUnanswered = noUnanswered;
	}

	public String getSpeed() {
		return speed;
	}

	public void setSpeed(String speed) {
		this.speed = speed;
	}

	public String getConfidence() {
		return confidence;
	}

	public void setConfidence(String confidence) {
		this.confidence = confidence;
	}

	public String getAccuracy() {
		return accuracy;
	}

	public void setAccuracy(String accuracy) {
		this.accuracy = accuracy;
	}

	public String getOverallperformance() {
		return overallperformance;
	}

	public void setOverallperformance(String overallperformance) {
		this.overallperformance = overallperformance;
	}

	@Override
	public String getTestName() {
	
		return testName;
	}

	

	@Override
	public void setTestName(String testName) {
		this.testName = testName;
		
	}

	

	@Override
	public void setShortDetail(String shortDetail) {
		this.shortDetail = shortDetail;
		
	}

	@Override
	public String getShortDetail() {
		
		return shortDetail;
	}
	
	
	
	
}
