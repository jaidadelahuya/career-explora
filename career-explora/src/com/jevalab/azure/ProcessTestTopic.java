package com.jevalab.azure;

import java.io.Serializable;

public class ProcessTestTopic implements Serializable {

	private String topicName;
	private boolean covered;
	public String getTopicName() {
		return topicName;
	}
	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}
	public boolean isCovered() {
		return covered;
	}
	public void setCovered(boolean covered) {
		this.covered = covered;
	}
	
	@Override
	public String toString() {
		return "ProcessTestTopic [topicName=" + topicName + ", covered="
				+ covered + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((topicName == null) ? 0 : topicName.hashCode());
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
		ProcessTestTopic other = (ProcessTestTopic) obj;
		if (topicName == null) {
			if (other.topicName != null)
				return false;
		} else if (!topicName.equalsIgnoreCase(other.topicName))
			return false;
		return true;
	}
	
}
