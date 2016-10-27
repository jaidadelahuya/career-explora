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
import com.google.appengine.api.datastore.Text;

@Entity
public class EnglishPassage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	Key key;

	@Basic
	@Extension(vendorName = "datanucleus", key = "gae.unindexed", value = "true")
	private Text passage;
	
	@Basic
	@Extension(vendorName="datanucleus",key="gae.unindexed", value="true")
	private List<PassageQuestion> passageQuestion;

	

	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public Text getPassage() {
		return passage;
	}

	public void setPassage(Text passage) {
		this.passage = passage;
	}

	public List<PassageQuestion> getPassageQuestion() {
		return passageQuestion;
	}

	public void setPassageQuestion(List<PassageQuestion> passageQuestion) {
		this.passageQuestion = passageQuestion;
	}
	
	
}
