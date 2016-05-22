package com.jevalab.azure;

import java.util.Date;

public interface TestResult {

	String getTestName();
	String getTestDate();
	String getShortDetail();
	void setTestName(String testName);
	void setTestDate(String testDate);
	void setShortDetail(String shortDetail);
}
