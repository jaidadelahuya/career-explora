package com.jevalab.azure.persistence;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.datanucleus.api.jpa.annotations.Extension;

@Entity(name="question")
public class Question implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	private String subjectName,vendor,year,difficulty,categoryName;
	
	@Extension(vendorName = "datanucleus", key = "gae.unindexed", value = "true")
	private String body,explanation,imageKey,pictureUrl,correctAlternative;
	
	
	@Basic
	@Extension(vendorName = "datanucleus", key = "gae.unindexed", value = "true")
	private List<String> alternatives,topics;
	
	@Transient
	private boolean isCorrect;
	
	@Basic
	@Extension(vendorName = "datanucleus", key = "gae.unindexed", value = "true")
	EnglishPassage passage;
	
	
	
	public EnglishPassage getPassage() {
		return passage;
	}

	public void setPassage(EnglishPassage passage) {
		this.passage = passage;
	}

	public String getSubjectName() {
		return subjectName;
	}
	
	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getExplanation() {
		return explanation;
	}
	public void setExplanation(String explanation) {
		this.explanation = explanation;
	}
	public String getImageKey() {
		return imageKey;
	}
	public void setImageKey(String imageKey) {
		this.imageKey = imageKey;
	}
	public String getVendor() {
		return vendor;
	}
	public void setVendor(String vendor) {
		this.vendor = vendor;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getPictureUrl() {
		return pictureUrl;
	}
	public void setPictureUrl(String extraText) {
		this.pictureUrl = extraText;
	}
	public List<String> getAlternatives() {
		return alternatives;
	}
	public void setAlternatives(List<String> alternatives) {
		this.alternatives = alternatives;
	}
	public List<String> getTopics() {
		return topics;
	}
	public void setTopics(List<String> topics) {
		this.topics = topics;
	}
	public String getCorrectAlternative() {
		return correctAlternative;
	}
	public void setCorrectAlternative(String correctAlternative) {
		this.correctAlternative = correctAlternative;
	}
	public String getDifficulty() {
		return difficulty;
	}
	public void setDifficulty(String difficulty) {
		this.difficulty = difficulty;
	}
	public boolean isCorrect() {
		return isCorrect;
	}
	public void setCorrect(boolean isCorrect) {
		this.isCorrect = isCorrect;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Question [id=").append(id).append(", subjectName=")
				.append(subjectName).append(", vendor=").append(vendor)
				.append(", year=").append(year).append(", difficulty=")
				.append(difficulty).append(", categoryName=")
				.append(categoryName).append(", body=").append(body)
				.append(", explanation=").append(explanation)
				.append(", imageKey=").append(imageKey).append(", pictureUrl=")
				.append(pictureUrl).append(", correctAlternative=")
				.append(correctAlternative).append(", alternatives=")
				.append(alternatives).append(", topics=").append(topics)
				.append(", isCorrect=").append(isCorrect).append(", passage=")
				.append(passage).append("]");
		return builder.toString();
	}
	
	
	
}
