package com.jevalab.helper.classes;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class TalentReport implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 710231930275600128L;
	
	Set<TalentInfo> headTalents;
	Set<TalentInfo> bodyTalents;
	Set<TalentInfo> handTalents;
	int vsTalentCount, sTalentCount, vsHeadCount,sHeadCount,vsHandCount,sHandCount, vsBodyCount, sBodyCount ;
	
	
	
	public int getVsHeadCount() {
		return vsHeadCount;
	}
	public void setVsHeadCount(int vsHeadCount) {
		this.vsHeadCount = vsHeadCount;
	}
	public int getsHeadCount() {
		return sHeadCount;
	}
	public void setsHeadCount(int sHeadCount) {
		this.sHeadCount = sHeadCount;
	}
	public int getVsHandCount() {
		return vsHandCount;
	}
	public void setVsHandCount(int vsHandCount) {
		this.vsHandCount = vsHandCount;
	}
	public int getsHandCount() {
		return sHandCount;
	}
	public void setsHandCount(int sHandCount) {
		this.sHandCount = sHandCount;
	}
	public int getVsBodyCount() {
		return vsBodyCount;
	}
	public void setVsBodyCount(int vsBodyCount) {
		this.vsBodyCount = vsBodyCount;
	}
	public int getsBodyCount() {
		return sBodyCount;
	}
	public void setsBodyCount(int sBodyCount) {
		this.sBodyCount = sBodyCount;
	}
	public int getVsTalentCount() {
		return vsTalentCount;
	}
	public void setVsTalentCount(int vsTalentCount) {
		this.vsTalentCount = vsTalentCount;
	}
	public int getsTalentCount() {
		return sTalentCount;
	}
	public void setsTalentCount(int sTalentCount) {
		this.sTalentCount = sTalentCount;
	}
	public Set<TalentInfo> getHeadTalents() {
		return headTalents;
	}
	public void setHeadTalents(Set<TalentInfo> headTalents) {
		this.headTalents = headTalents;
	}
	public Set<TalentInfo> getBodyTalents() {
		return bodyTalents;
	}
	public void setBodyTalents(Set<TalentInfo> bodyTalents) {
		this.bodyTalents = bodyTalents;
	}
	public Set<TalentInfo> getHandTalents() {
		return handTalents;
	}
	public void setHandTalents(Set<TalentInfo> handTalents) {
		this.handTalents = handTalents;
	}
	
	@Override
	public String toString() {
		return "TalentReport [headTalents=" + headTalents + ", bodyTalents="
				+ bodyTalents + ", handTalents=" + handTalents
				+ ", vsTalentCount=" + vsTalentCount + ", sTalentCount="
				+ sTalentCount + ", vsHeadCount=" + vsHeadCount
				+ ", sHeadCount=" + sHeadCount + ", vsHandCount=" + vsHandCount
				+ ", sHandCount=" + sHandCount + ", vsBodyCount=" + vsBodyCount
				+ ", sBodyCount=" + sBodyCount + "]";
	}
	public TalentReport(Set<TalentInfo> headTalents,
			Set<TalentInfo> bodyTalents, Set<TalentInfo> handTalents) {
		super();
		this.headTalents = headTalents;
		this.bodyTalents = bodyTalents;
		this.handTalents = handTalents;
		vsTalentCount = getsTalentCount(headTalents,bodyTalents,handTalents,"VS");
		sTalentCount = getsTalentCount(headTalents,bodyTalents,handTalents,"S");
		vsHeadCount = getsTalentCount(headTalents,"VS");
		sHeadCount = getsTalentCount(headTalents,"S");
		vsHandCount = getsTalentCount(handTalents,"VS");
		sHandCount = getsTalentCount(handTalents,"S");
		vsBodyCount = getsTalentCount(bodyTalents,"VS");
		sBodyCount = getsTalentCount(bodyTalents,"S");
	}
	private int getsTalentCount(Set<TalentInfo> talent, String val) {

		return getsTalentCount(talent,new HashSet<TalentInfo>(),new HashSet<TalentInfo>(),val);
	}
	private int getsTalentCount(Set<TalentInfo> headTalents,
			Set<TalentInfo> bodyTalents, Set<TalentInfo> handTalents,
			String val) {
		int count = 0;
		Set<TalentInfo> talents = new HashSet<>();
		talents.addAll(headTalents);
		talents.addAll(bodyTalents);
		talents.addAll(handTalents);
		for(TalentInfo ti: talents) {
			if(ti.getRating().equalsIgnoreCase(val)) {
				count += 1;
			}
		}
		
		return count;
	}
	
	
}
