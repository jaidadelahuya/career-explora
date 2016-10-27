package com.jevalab.azure.persistence;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;



import org.datanucleus.api.jpa.annotations.Extension;

import com.google.appengine.api.datastore.Text;

@Entity(name="careerCluster")
public class CareerCluster implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	private String clusterName;
	
	@Extension(vendorName = "datanucleus", key = "gae.unindexed", value = "true")
	private Text description;
	
	@Extension(vendorName = "datanucleus", key = "gae.unindexed", value = "true")
	@OneToMany
	private List<String> questions;
	
	@Extension(vendorName = "datanucleus", key = "gae.unindexed", value = "true")
	@OneToMany(cascade = CascadeType.ALL)
	private List<CareerPathway> pathways;
	
	
	public List<CareerPathway> getPathways() {
		return pathways;
	}
	public void setPathways(List<CareerPathway> pathways) {
		this.pathways = pathways;
	}
	
	
	public String getClusterName() {
		return clusterName;
	}
	public void setClusterName(String clusterName) {
		this.clusterName = clusterName;
	}
	public Text getDescription() {
		return description;
	}
	public void setDescription(Text descriptionText) {
		this.description = descriptionText;
	}
	
	public List<String> getQuestions() {
		return questions;
	}
	public void setQuestions(List<String> questions) {
		this.questions = questions;
	}
	public void setPathway(List<CareerPathway> pathways) {
		this.pathways = pathways;
		
	}
	

}
