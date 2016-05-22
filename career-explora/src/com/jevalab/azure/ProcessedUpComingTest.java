package com.jevalab.azure;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Days;

import com.google.appengine.api.datastore.KeyFactory;
import com.jevalab.azure.persistence.UpComingTest;
import com.jevalab.helper.classes.LoginHelper;

public class ProcessedUpComingTest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String testName;
	private String testDate, prepDeadline;
	private boolean expired;
	private List<ProcessTestTopic> topics;
	private int daysLeft;
	private String color;
	private int topicsLeft;
	private String id;

	public ProcessedUpComingTest(String testName, String testDate,
			String prepDeadline, List<ProcessTestTopic> topics) {
		super();
		this.testName = testName;
		this.testDate = testDate;
		this.prepDeadline = prepDeadline;
		Calendar cTestDate = LoginHelper.getDateObject(testDate);
		this.expired = isExpired(cTestDate);
		this.topics = topics;
		daysLeft = calculateDaysLeft(cTestDate.getTime());
		if (prepDeadline != null) {
			Calendar cPrepDate = LoginHelper.getDateObject(prepDeadline);
			color = calculateColor(cPrepDate.getTime());
		}

		if (topics != null) {
			topicsLeft = calculateTopicsLeft(topics);
		}

	}

	public ProcessedUpComingTest(UpComingTest uct) {
		this(uct.getTestName(), uct.getTestDate(),
				uct.getPreparationDeadLine(), LoginHelper.asProcessedTopics(uct
						.getTopics()));
		this.id = KeyFactory.keyToString(uct.getKey());
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	private boolean isExpired(Calendar dateObject) {
		Calendar today = new GregorianCalendar();
		if (dateObject.compareTo(today) < 0) {
			return true;
		} else {
			return false;
		}
	}

	private int calculateTopicsLeft(List<ProcessTestTopic> topics2) {
		int counter = 0;
		if (topics2 != null) {
			for (ProcessTestTopic ptt : topics2) {
				if (ptt.isCovered()) {
					counter++;
				}
			}
		}
		return counter;
	}

	private String calculateColor(Date prepDeadline2) {
		Date today = new Date();
		if (prepDeadline2.before(today)) {
			return "red";
		} else {
			return "green";
		}
	}

	private int calculateDaysLeft(Date testDate) {

		DateTime d1 = new DateTime(testDate.getTime());
		DateTime d2 = new DateTime();

		Days days = Days.daysBetween(d2, d1);

		return days.getDays();
	}

	public int getTopicsLeft() {
		return topicsLeft;
	}

	public void setTopicsLeft(int topicsLeft) {
		this.topicsLeft = topicsLeft;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String daysLeftColor) {
		this.color = daysLeftColor;
	}

	public int getDaysLeft() {
		return daysLeft;
	}

	public void setDaysLeft(int daysLeft) {
		this.daysLeft = daysLeft;
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

	public String getPrepDeadline() {
		return prepDeadline;
	}

	public void setPrepDeadline(String prepDeadline) {
		this.prepDeadline = prepDeadline;
	}

	public boolean isExpired() {
		return expired;
	}

	public void setExpired(boolean expired) {
		this.expired = expired;
	}

	public List<ProcessTestTopic> getTopics() {
		return topics;
	}

	public void setTopics(List<ProcessTestTopic> topics) {
		this.topics = topics;
	}

	@Override
	public String toString() {
		return "ProcessedUpComingTest [testName=" + testName + ", testDate="
				+ testDate + ", prepDeadline=" + prepDeadline + ", expired="
				+ expired + ", topics=" + topics + "]";
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
		ProcessedUpComingTest other = (ProcessedUpComingTest) obj;
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
