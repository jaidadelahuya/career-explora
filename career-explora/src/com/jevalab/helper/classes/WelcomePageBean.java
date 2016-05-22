package com.jevalab.helper.classes;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.Date;

public class WelcomePageBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String lastTest, validity, lastTestDate, id;

	private Date subscriptionDate, lastSeenDate, expiryDate;

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {

		this.id = id;
	}

	public String getLastTest() {
		return lastTest;
	}

	public void setLastTest(String lastTest) {
		this.lastTest = lastTest;
	}

	public String getLastTestDate() {
		return lastTestDate;
	}

	public void setLastTestDate(String lastTestDate) {
		this.lastTestDate = lastTestDate;
	}

	public String getValidity() {
		return validity;
	}

	public void setValidity(String validity) {
		this.validity = validity;
	}

	public Date getSubscriptionDate() {
		return subscriptionDate;
	}

	public void setSubscriptionDate(Date subscriptionDate) {
		this.subscriptionDate = subscriptionDate;
	}

	public Date getLastSeenDate() {
		return lastSeenDate;
	}

	public void setLastSeenDate(Date lastSeenDate) {
		this.lastSeenDate = lastSeenDate;
	}

	@Override
	public String toString() {
		return "WelcomePageBean [lastTest=" + lastTest + ", validity="
				+ validity + ", lastTestDate=" + lastTestDate + ", id=" + id
				+ ", subscriptionDate=" + subscriptionDate + ", lastSeenDate="
				+ lastSeenDate + ", expiryDate=" + expiryDate + "]";
	}
	
	

}
