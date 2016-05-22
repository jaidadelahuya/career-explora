package com.jevalab.helper.classes;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.jevalab.azure.persistence.MultipleIntelligenceTestQuestion;

public class MitReport implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3560062698714631331L;
	private Map<MultipleIntelligenceTestQuestion,String> smit, vsmit, fsmit;

	public MitReport(Map<MultipleIntelligenceTestQuestion, String> smit, Map<MultipleIntelligenceTestQuestion, String> vsmit, Map<MultipleIntelligenceTestQuestion, String> fsmit) {
		super();
		this.smit = smit;
		this.vsmit = vsmit;
		this.fsmit = fsmit;
	}



	public Map<MultipleIntelligenceTestQuestion, String> getSmit() {
		return smit;
	}

	public void setSmit(Map<MultipleIntelligenceTestQuestion, String> smit) {
		this.smit = smit;
	}

	public Map<MultipleIntelligenceTestQuestion, String> getVsmit() {
		return vsmit;
	}

	public void setVsmit(Map<MultipleIntelligenceTestQuestion, String> vsmit) {
		this.vsmit = vsmit;
	}

	public Map<MultipleIntelligenceTestQuestion, String> getFsmit() {
		return fsmit;
	}

	public void setFsmit(Map<MultipleIntelligenceTestQuestion, String> fsmit) {
		this.fsmit = fsmit;
	}



	@Override
	public String toString() {
		return "MitReport [smit=" + smit + ", vsmit=" + vsmit + ", fsmit="
				+ fsmit + "]";
	}

	

	
}
