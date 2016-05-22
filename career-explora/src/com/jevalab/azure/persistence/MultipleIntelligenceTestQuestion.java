package com.jevalab.azure.persistence;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author JAIDA DE LAHUYA
 */
@Entity
public class MultipleIntelligenceTestQuestion implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	private String intelligenceType;
    private String shortDescription,longDescription,typicalRoles,relatedActivities,preferredLearningStyle;
    @OneToMany(fetch=FetchType.EAGER)
    private List<String> questions;
    public String getShortDescription() {
		return shortDescription;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	public String getLongDescription() {
		return longDescription;
	}

	public void setLongDescription(String longDescription) {
		this.longDescription = longDescription;
	}

	public String getTypicalRoles() {
		return typicalRoles;
	}

	public void setTypicalRoles(String typicalRoles) {
		this.typicalRoles = typicalRoles;
	}

	public String getRelatedActivities() {
		return relatedActivities;
	}

	public void setRelatedActivities(String relatedActivities) {
		this.relatedActivities = relatedActivities;
	}

	public String getPreferredLearningStyle() {
		return preferredLearningStyle;
	}

	public void setPreferredLearningStyle(String preferredLearningStyle) {
		this.preferredLearningStyle = preferredLearningStyle;
	}

	public List<String> getQuestions() {
		return questions;
	}

	public void setQuestions(List<String> questions) {
		this.questions = questions;
	}

	public String getIntelligenceType() {
        return intelligenceType;
    }

    public void setIntelligenceType(String intelligenceType) {
        this.intelligenceType = intelligenceType;
    }
    
}
