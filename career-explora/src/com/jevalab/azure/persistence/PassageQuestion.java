package com.jevalab.azure.persistence;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;



import org.datanucleus.api.jpa.annotations.Extension;

import com.google.appengine.api.datastore.Key;

@Entity
public class PassageQuestion implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	Key key;
	
	@Extension(vendorName = "datanucleus", key = "gae.unindexed", value = "true")
	private String question;
	
	@Extension(vendorName="datanucleus", key="gae.unindexed", value="true")
	@Basic
	private List<String> alts;

	

	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public List<String> getAlts() {
		return alts;
	}

	public void setAlts(List<String> alts) {
		this.alts = alts;
	}
	
	
}
