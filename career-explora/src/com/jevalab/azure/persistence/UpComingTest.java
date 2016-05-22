package com.jevalab.azure.persistence;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.datanucleus.api.jpa.annotations.Extension;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;


@Entity(name = "UPCOMINGTEST")
public class UpComingTest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Key key;

	private String testName;
	@Extension(vendorName = "datanucleus", key = "gae.unindexed", value = "true")
	private String testDate;
	@Extension(vendorName = "datanucleus", key = "gae.unindexed", value = "true")
	private String preparationDeadLine;
	
	@Basic
	@OneToMany(cascade = CascadeType.ALL)
	@Extension(vendorName = "datanucleus",
	key = "gae.unindexed",
	value = "true")
	private List<UpComingTestTopic> topics;
	
	

	public UpComingTest(String testName, String testDate,
			String preparationDeadLine,
			List<UpComingTestTopic> topics) {
		super();
		this.key = KeyFactory.createKey(UpComingTest.class.getSimpleName(), testName+testDate);
		this.testName = testName;
		this.testDate = testDate;
		this.preparationDeadLine = preparationDeadLine;
		this.topics = topics;
		
	}
	
	

	public UpComingTest() {
		super();
	}



	public Key getKey() {
		return key;
	}



	public void setKey(Key key) {
		this.key = key;
	}



	public List<UpComingTestTopic> getTopics() {
		return topics;
	}

	public void setTopics(List<UpComingTestTopic> topics) {
		this.topics = topics;
	}

	public String getPreparationDeadLine() {
		return preparationDeadLine;
	}

	public void setPreparationDeadLine(String preparationDeadLine) {
		this.preparationDeadLine = preparationDeadLine;
	}

	public String getTestName() {
		return testName;
	}

	public void setTestName(String testName) {
		this.testName = testName;
	}

	public String getTestDate() {
		return testDate;
	}

	public void setTestDate(String testDate) {
		this.testDate = testDate;
	}

	@Override
	public String toString() {
		return "UpComingTest [testName=" + testName + ", testDate=" + testDate
				+ ", preparationDeadLine=" + preparationDeadLine + ", topics="
				+ topics + "]";
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((testDate == null) ? 0 : testDate.hashCode());
		result = prime * result
				+ ((testName == null) ? 0 : testName.hashCode());
		return result;
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UpComingTest other = (UpComingTest) obj;
		if (testDate == null) {
			if (other.testDate != null)
				return false;
		} else if (!testDate.equals(other.testDate))
			return false;
		if (testName == null) {
			if (other.testName != null)
				return false;
		} else if (!testName.equals(other.testName))
			return false;
		return true;
	}
	
	


	
}
