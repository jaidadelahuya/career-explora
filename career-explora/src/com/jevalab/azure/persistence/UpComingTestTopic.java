package com.jevalab.azure.persistence;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.datanucleus.api.jpa.annotations.Extension;

import com.google.appengine.api.datastore.Key;

@Entity
public class UpComingTestTopic implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Key key;
	@Extension(vendorName = "datanucleus", key = "gae.unindexed", value = "true")
	private String topicName;
	@Extension(vendorName = "datanucleus", key = "gae.unindexed", value = "true")
	private boolean isCovered;
	
	public String getTopicName() {
		return topicName;
	}
	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}
	public boolean isCovered() {
		return isCovered;
	}
	public void setCovered(boolean isCovered) {
		this.isCovered = isCovered;
	}
	public UpComingTestTopic(String topicName, boolean isCovered) {
		super();
		this.topicName = topicName;
		this.isCovered = isCovered;
	}
	@Override
	public String toString() {
		return "UpComingTestTopic [topicName=" + topicName + ", isCovered="
				+ isCovered + "]";
	}
	
	
	
}
