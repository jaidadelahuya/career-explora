package com.jevalab.helper.classes;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UserSettingsModel implements Serializable {

	private boolean hasPassword,hasMobile, hasAltMobile, hasEmail, hasAltEmail, hasFacebookAccount;
	private PasswordRecovery mobile,altMobile,email,altEmail;
	private PropertyChangeSupport pcs = new PropertyChangeSupport(this);
	
	
	public void addPropertyChangeListener(PropertyChangeListener pcl) {
		pcs.addPropertyChangeListener(pcl);
	}
	
	public void removePropertyChangeListener(PropertyChangeListener pcl) {
		pcs.removePropertyChangeListener(pcl);
	}
	
	public boolean isHasPassword() {
		return hasPassword;
	}
	public void setHasPassword(boolean hasPassword) {
		this.hasPassword = hasPassword;
	}
	public boolean isHasMobile() {
		return hasMobile;
	}
	public void setHasMobile(boolean hasMobile) {
		this.hasMobile = hasMobile;
	}
	public boolean isHasAltMobile() {
		return hasAltMobile;
	}
	public void setHasAltMobile(boolean hasAltMobile) {
		this.hasAltMobile = hasAltMobile;
	}
	public boolean isHasEmail() {
		return hasEmail;
	}
	public void setHasEmail(boolean hasEmail) {
		this.hasEmail = hasEmail;
	}
	public boolean isHasAltEmail() {
		return hasAltEmail;
	}
	public void setHasAltEmail(boolean hasAltEmail) {
		this.hasAltEmail = hasAltEmail;
	}
	public boolean isHasFacebookAccount() {
		return hasFacebookAccount;
	}
	public void setHasFacebookAccount(boolean hasFacebookAccount) {
		this.hasFacebookAccount = hasFacebookAccount;
	}
	public PasswordRecovery getMobile() {
		return mobile;
	}
	public void setMobile(PasswordRecovery mobile) {
		PasswordRecovery oldValue = this.mobile;
		this.mobile = mobile;
		pcs.firePropertyChange(StringConstants.MOBILE, oldValue, mobile);
	}
	public PasswordRecovery getAltMobile() {
		return altMobile;
	}
	public void setAltMobile(PasswordRecovery altMobile) {
		PasswordRecovery oldValue = this.altMobile;
		this.altMobile = altMobile;
		pcs.firePropertyChange(StringConstants.ALT_MOBILE, oldValue, altMobile);
	}
	public PasswordRecovery getEmail() {
		return email;
	}
	public void setEmail(PasswordRecovery email) {
		PasswordRecovery oldValue = this.email;
		this.email = email;
		pcs.firePropertyChange(StringConstants.EMAIL, oldValue, email);
	}
	public PasswordRecovery getAltEmail() {
		return altEmail;
	}
	public void setAltEmail(PasswordRecovery altEmail) {
		PasswordRecovery oldValue = this.altEmail;
		this.altEmail = altEmail;
		pcs.firePropertyChange(StringConstants.ALT_EMAIL, oldValue, altEmail);
	}
	
	public List<PasswordRecovery> getPasswordRecoveries() {
		List<PasswordRecovery> list = new ArrayList<>();
		
		list.add(email);
		list.add(mobile);
		list.add(altEmail);
		list.add(altMobile);
		
		return list;
	}
	@Override
	public String toString() {
		return "UserSettingsModel [hasPassword=" + hasPassword + ", hasMobile="
				+ hasMobile + ", hasAltMobile=" + hasAltMobile + ", hasEmail="
				+ hasEmail + ", hasAltEmail=" + hasAltEmail
				+ ", hasFacebookAccount=" + hasFacebookAccount + ", mobile="
				+ mobile + ", altMobile=" + altMobile + ", email=" + email
				+ ", altEmail=" + altEmail + "]";
	}
	public UserSettingsModel(boolean hasFacebookAccount, boolean hasPassword,
			PasswordRecovery mobile, PasswordRecovery altMobile,
			PasswordRecovery email, PasswordRecovery altEmail) {
		super();
		this.hasFacebookAccount = hasFacebookAccount;
		this.mobile = mobile;
		this.altMobile = altMobile;
		this.email = email;
		this.altEmail = altEmail;
		this.hasPassword = hasPassword;
		this.hasMobile = (mobile==null)?false:true;
		this.hasAltMobile = (altMobile==null)?false:true;
		this.hasEmail = (email==null)?false:true;
		this.hasAltEmail = (altEmail==null)?false:true;
		
	}
	public boolean resetPasswordRecovery(PasswordRecovery old,
			PasswordRecovery nw) {
		if(old.equals(mobile)) {
			this.setMobile(nw);
			return true;
		} else if(old.equals(email)) {
			this.setEmail(nw);
			return true;
		} else if(old.equals(altMobile)) {
			this.setAltMobile(nw);
			return true;
		} else if(old.equals(altEmail)) {
			this.setAltEmail(nw);
			return true;
		} else {
			return false;
		}
		
	}
	
	
}
