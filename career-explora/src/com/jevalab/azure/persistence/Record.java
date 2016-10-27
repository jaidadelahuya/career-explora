package com.jevalab.azure.persistence;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;



import org.datanucleus.api.jpa.annotations.Extension;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Text;
import com.jevalab.helper.classes.StringConstants;


@Entity
public class Record implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Key key;
	
	private String userId,testName,testDate,subjectName,vendor;

	
	@Basic
	@Extension(vendorName = "datanucleus", key = "gae.unindexed", value = "true")
	private Text careerValues,styles,mitTypes,cbtData;
	
	@Basic
	@Extension(vendorName = "datanucleus", key = "gae.unindexed", value = "true")
	private Map<String,String> talents,careerClusters,skills; 

	
	public Map<String, String> getSkills() {
		return skills;
	}

	public void setSkills(Map<String, String> skills) {
		this.skills = skills;
	}

	public String getVendor() {
		return vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public Text getCbtData() {
		return cbtData;
	}

	public void setCbtData(Text cbtData) {
		this.cbtData = cbtData;
	}

	public Map<String, String> getTalents() {
		return talents;
	}

	public void setTalents(Map<String, String> talents) {
		this.talents = talents;
	}

	public Text getMitTypes() {
		return mitTypes;
	}

	public void setMitTypes(Text mitTypes) {
		this.mitTypes = mitTypes;
	}

	public Map<String, String> getCareerClusters() {
		return careerClusters;
	}

	public void setCareerClusters(Map<String, String> careerClusters) {
		this.careerClusters = careerClusters;
	}

	public Text getCareerValues() {
		return careerValues;
	}

	public void setCareerValues(Text careerValues) {
		this.careerValues = careerValues;
	}

	public String getTestName() {
		return testName;
	}

	public void setTestName(String testName) {
		this.testName = testName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	

	public Text getStyles() {
		return styles;
	}

	public void setStyles(Text styles) {
		this.styles = styles;
	}

	public String getTestDate() {
		return testDate;
	}

	public void setTestDate(String testDate) {
		this.testDate = testDate;
	}
	
	

	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}
	
	public Record(String userId, String testName) {
		super();
		this.userId = userId;
		this.testName = testName;
		
		key = KeyFactory.createKey(Record.class.getSimpleName(), userId+testName);
	}

	public Record(String userId, String testName, String testDate) {
		super();
		this.userId = userId;
		this.testName = testName;
		this.testDate = testDate;
		
		key = KeyFactory.createKey(Record.class.getSimpleName(), userId+testName);
	}
	
	public Record(String userId, String testName , String testDate, Text values) {
		super();
		this.userId = userId;
		this.testName = testName;
		this.testDate = testDate;
		
		if(testName.equalsIgnoreCase(StringConstants.PERSONAL_STYLE)) {
			this.setStyles(values);
		} else if (testName.equalsIgnoreCase(StringConstants.MULTIPLE_INTELLIGENCE_TEST)) {
			this.setMitTypes(values);
		} else if(testName.equalsIgnoreCase(StringConstants.CAREER_VALUES)) {
			this.setCareerValues(values);
		}
		key = KeyFactory.createKey(Record.class.getSimpleName(), userId+testName);
		
	}

	public Record(String userId, String testName, String testDate, String subjectName) {
		super();
		this.userId = userId;
		this.testName = testName + " "+subjectName;
		this.testDate = testDate;
		this.subjectName = subjectName;
		
		key = KeyFactory.createKey(Record.class.getSimpleName(), userId+"-"+testName+"-"+subjectName+"-"+testDate);
	}

	public Record(String userId, String testName, String testDate,
			Map<String,String> map) {
		
		key = KeyFactory.createKey(Record.class.getSimpleName(), userId+testName);
		
		RecordJpaController cont = new RecordJpaController();
		Record record = cont.findRecord(key);
		
		if(testName.equals(StringConstants.TALENT_HUNT)) {
			if (record == null) {
				this.userId = userId;
				this.testName = testName;
				this.testDate = testDate;
				this.talents = map;
			} else {
				this.userId = record.getUserId();
				this.testDate = record.getTestDate();
				this.testName = record.getTestName();
				record.getTalents().putAll(map);
				this.talents = record.getTalents();
			}
		} else if (testName.equals(StringConstants.CAREER_CLUSTERS)) {
			if (record == null) {
				this.userId = userId;
				this.testName = testName;
				this.testDate = testDate;
				this.careerClusters = map;
			} else {
				this.userId = record.getUserId();
				this.testDate = record.getTestDate();
				this.testName = record.getTestName();
				record.getCareerClusters().putAll(map);
				this.careerClusters = record.getCareerClusters();
			}
		}
		
	}

	
	
	
	
}
