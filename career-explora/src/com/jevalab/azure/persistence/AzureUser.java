package com.jevalab.azure.persistence;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.datanucleus.api.jpa.annotations.Extension;

import com.google.appengine.api.datastore.Key;
import com.jevalab.helper.classes.LoginHelper;
import com.jevalab.helper.classes.RegistrationForm;
import com.jevalab.helper.classes.StringConstants;
import com.jevalab.helper.classes.UpdateHelperClass;
import com.jevalab.helper.classes.UserSettingsModel;
import com.jevalab.helper.classes.WelcomePageBean;

@Entity(name = "user")
public class AzureUser implements Serializable, PropertyChangeListener {
	/**
	 * 
	 */
	@Transient
	private boolean takenTalentTest;

	@Transient
	private PropertyChangeSupport pcs = new PropertyChangeSupport(this);

	private static final long serialVersionUID = 3643732767169302091L;

	@Id
	private String userID;

	@Column(name = "FirstName")
	private String firstName;

	@Column(name = "LastName")
	private String lastName;

	@Column(name = "MiddleName")
	private String middleName;

	@Column(name = "Gender")
	@Extension(vendorName = "datanucleus", key = "gae.unindexed", value = "true")
	private String gender;

	@Column(name = "EMail")
	private String email;

	@Column(name = "State")
	private String state;

	@Column(name = "Country")
	private String country;

	@Column(name = "School")
	private String school;

	@Column(name = "UpdateName")
	@Extension(vendorName = "datanucleus", key = "gae.unindexed", value = "true")
	private boolean updateNameFromIdp;

	@Column(name = "LastTestTaken")
	@Extension(vendorName = "datanucleus", key = "gae.unindexed", value = "true")
	private String lastTestTaken;

	@Column(name = "DateOfBirth")
	@Extension(vendorName = "datanucleus", key = "gae.unindexed", value = "true")
	private String dateOfBirth;

	@Column(name = "LastSeenDate")
	@Extension(vendorName = "datanucleus", key = "gae.unindexed", value = "true")
	private Date lastSeenDate;

	@Column(name = "authorized")
	@Extension(vendorName = "datanucleus", key = "gae.unindexed", value = "true")
	private boolean authorized;

	@Column(name = "FreeAccess")
	@Extension(vendorName = "datanucleus", key = "gae.unindexed", value = "true")
	private boolean freeAccess;

	@Column(name = "UpComingTests")
	@Basic
	@OneToMany(cascade = CascadeType.ALL)
	@Extension(vendorName = "datanucleus", key = "gae.unindexed", value = "true")
	private List<UpComingTest> upComingTests;

	@Column(name = "SubscriptionDate")
	@Extension(vendorName = "datanucleus", key = "gae.unindexed", value = "true")
	private Date subscriptionDate;

	@Column(name = "Validity")
	@Extension(vendorName = "datanucleus", key = "gae.unindexed", value = "true")
	private String validity;

	@Column(name = "Attends")
	@Extension(vendorName = "datanucleus", key = "gae.unindexed", value = "true")
	private String attends;

	@Column(name = "Picture")
	@Extension(vendorName = "datanucleus", key = "gae.unindexed", value = "true")
	private String picture;

	@Column(name = "Cover")
	@Extension(vendorName = "datanucleus", key = "gae.unindexed", value = "true")
	private String cover;

	@Basic
	@Column(name = "Friends")
	@OneToMany(cascade = CascadeType.ALL)
	@Extension(vendorName = "datanucleus", key = "gae.unindexed", value = "true")
	private List<String> friendsId;

	@Basic
	@Column(name = "Username")
	private String username;

	@Basic
	@Column(name = "Mobile")
	private String mobile;

	@Basic
	@Column(name = "Password")
	@Extension(vendorName = "datanucleus", key = "gae.unindexed", value = "true")
	private String password;

	@Basic
	@Column(name = "OldPasswords")
	@Extension(vendorName = "datanucleus", key = "gae.unindexed", value = "true")
	private List<String> oldPasswords;
	
	@Basic
	@Column(name = "Communities")
	@Extension(vendorName = "datanucleus", key = "gae.unindexed", value = "true")
	private List<Key> communities;
	
	@Basic
	@Column(name = "AreaOfInterest")
	@Extension(vendorName = "datanucleus", key = "gae.unindexed", value = "true")
	private List<String> areaOfInterest;

	@Basic
	@Column(name = "LastPasswordChangeDate")
	@Extension(vendorName = "datanucleus", key = "gae.unindexed", value = "true")
	private Date lastPasswordChangeDate;

	@Basic
	@Column(name = "PasswordRecoveryIds")
	@Extension(vendorName = "datanucleus", key = "gae.unindexed", value = "true")
	private Set<String> passwordRecoveryIds;

	@Basic
	@Column(name = "UserPicturesIds")
	@Extension(vendorName = "datanucleus", key = "gae.unindexed", value = "true")
	private Set<String> UserPicturesIds;
	

	@Column(name = "Class")
	private String sClass;

	@Transient
	private boolean newUser;

	@Transient
	private String accessToken;

	@Transient
	private boolean fromAuthorization;
	
	

	public String getsClass() {
		return sClass;
	}

	public void setsClass(String sClass) {
		this.sClass = sClass;
	}

	public List<String> getAreaOfInterest() {
		return areaOfInterest;
	}

	public void setAreaOfInterest(List<String> areaOfInterest) {
		this.areaOfInterest = areaOfInterest;
	}

	public List<Key> getCommunities() {
		return communities;
	}

	public void setCommunities(List<Key> communities) {
		this.communities = communities;
	}

	public boolean isFreeAccess() {
		return freeAccess;
	}

	public void setFreeAccess(boolean freeAccess) {
		this.freeAccess = freeAccess;
	}

	public Set<String> getUserPicturesIds() {
		return UserPicturesIds;
	}

	public void setUserPicturesIds(Set<String> userPicturesIds) {
		UserPicturesIds = userPicturesIds;
	}

	public List<String> getOldPasswords() {
		return oldPasswords;
	}

	public void setOldPasswords(List<String> oldPasswords) {
		this.oldPasswords = oldPasswords;
	}

	public Date getLastPasswordChangeDate() {
		return lastPasswordChangeDate;
	}

	public void setLastPasswordChangeDate(Date lastPasswordChangeDate) {
		this.lastPasswordChangeDate = lastPasswordChangeDate;
	}

	public Set<String> getPasswordRecoveryIds() {
		return passwordRecoveryIds;
	}

	public void setPasswordRecoveryIds(Set<String> passwordRecoveryIds) {
		this.passwordRecoveryIds = passwordRecoveryIds;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		if(username!=null)
		this.username = username.toLowerCase();
	}

	public boolean isFromAuthorization() {
		return fromAuthorization;
	}

	public void setFromAuthorization(boolean fromAuthorization) {
		this.fromAuthorization = fromAuthorization;
	}

	public boolean isTakenTalentTest() {
		return takenTalentTest;
	}

	public void setTakenTalentTest(boolean takenTalentTest) {
		this.takenTalentTest = takenTalentTest;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public String getAttends() {
		return attends;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public boolean isNewUser() {
		return newUser;
	}

	public void setNewUser(boolean newUser) {
		this.newUser = newUser;
	}

	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public List<String> getFriendsId() {
		return friendsId;
	}

	public void setFriendsId(List<String> friends) {
		this.friendsId = friends;
	}

	public String isAttends() {
		return attends;
	}

	public void setAttends(String attends) {
		String oldAttends = this.attends;
		this.attends = attends;
		pcs.firePropertyChange("attends", oldAttends, attends);
	}

	@Transient
	private Date expiryDate;

	@Transient
	private int upComingTestsSize;

	public Date getSubscriptionDate() {
		return subscriptionDate;
	}

	public void setSubscriptionDate(Date subscriptionDate) {
		this.subscriptionDate = subscriptionDate;
	}

	public String getValidity() {
		return validity;
	}

	public void setValidity(String validity) {
		this.validity = validity;
	}

	public Date getExpiryDate() {
		Calendar c = Calendar.getInstance();
		c.setTime(subscriptionDate);
		int v = Integer.parseInt(validity);
		c.add(Calendar.MONTH, v);
		Date eDate = c.getTime();
		return eDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public boolean isAuthorized() {
		return authorized;
	}

	public void setAuthorized(boolean authorized) {
		this.authorized = authorized;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		String oldDateOfBirth = this.dateOfBirth;
		this.dateOfBirth = dateOfBirth;
		pcs.firePropertyChange("dateOfBirth", oldDateOfBirth, dateOfBirth);
	}

	public String getLastTestTaken() {
		return lastTestTaken;
	}

	public void setLastTestTaken(String lastTestTaken) {
		String oldLastTestTaken = this.lastTestTaken;
		this.lastTestTaken = lastTestTaken;
		pcs.firePropertyChange("lastTestTaken", oldLastTestTaken, lastTestTaken);
	}

	public Date getLastSeenDate() {
		return lastSeenDate;
	}

	public void setLastSeenDate(Date lastSeenDate) {
		this.lastSeenDate = lastSeenDate;
	}

	public int getUpComingTestsSize() {
		return upComingTests.size();
	}

	public List<UpComingTest> getUpComingTests() {
		return upComingTests;
	}

	public void setUpComingTests(List<UpComingTest> upComingTests) {
		List<UpComingTest> oldUpComingTests = this.upComingTests;
		this.upComingTests = upComingTests;
		pcs.firePropertyChange("upComingTests", oldUpComingTests, upComingTests);
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		String oldState = this.state;
		if (country != null) {
			this.state = state.toUpperCase();
			pcs.firePropertyChange("state", oldState, state);
		}
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		String oldCountry = this.country;
		if (country != null) {
			this.country = country.toUpperCase();
			pcs.firePropertyChange("country", oldCountry, country);
		}
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		String oldSchool = this.school;
		if (school != null) {
			this.school = school.toUpperCase();
			pcs.firePropertyChange("school", oldSchool, school);
		}
	}

	public boolean isUpdateNameFromIdp() {
		return updateNameFromIdp;
	}

	public void setUpdateNameFromIdp(boolean updateNameFromIdp) {
		boolean oldValue = this.updateNameFromIdp;
		this.updateNameFromIdp = updateNameFromIdp;
		pcs.firePropertyChange("updateFromIdp", oldValue, updateNameFromIdp);
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		String oldEmail = this.email;
		this.email = email.toLowerCase();
		pcs.firePropertyChange("email", oldEmail, email);
	}

	public AzureUser() {
		this.userID = LoginHelper.getNextId();
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		String oldMiddleName = this.middleName;
		if (middleName != null) {
			this.middleName = middleName.toUpperCase();
			pcs.firePropertyChange("middleName", oldMiddleName, middleName);
		}
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		String oldGender = this.gender;
		this.gender = gender;
		pcs.firePropertyChange("gender", oldGender, gender);
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		String oldFirstName = this.firstName;
		if (country != null) {
			this.firstName = firstName.toUpperCase();
			pcs.firePropertyChange("firstName", oldFirstName, firstName);
		}
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		String oldLastName = this.lastName;
		if (country != null) {
			this.lastName = lastName.toUpperCase();
			pcs.firePropertyChange("lastName", oldLastName, lastName);
		}
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((userID == null) ? 0 : userID.hashCode());
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
		AzureUser other = (AzureUser) obj;
		if (userID == null) {
			if (other.userID != null)
				return false;
		} else if (!userID.equals(other.userID))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "AzureUser [takenTalentTest=" + takenTalentTest + ", userID="
				+ userID + ", firstName=" + firstName + ", lastName="
				+ lastName + ", middleName=" + middleName + ", gender="
				+ gender + ", email=" + email + ", state=" + state
				+ ", country=" + country + ", school=" + school
				+ ", updateNameFromIdp=" + updateNameFromIdp
				+ ", lastTestTaken=" + lastTestTaken + ", dateOfBirth="
				+ dateOfBirth + ", lastSeenDate=" + lastSeenDate
				+ ", authorized=" + authorized + ", upComingTests="
				+ upComingTests + ", subscriptionDate=" + subscriptionDate
				+ ", validity=" + validity + ", attends=" + attends
				+ ", picture=" + picture + ", cover=" + cover + ", friendsId="
				+ friendsId + ", username=" + username + ", mobile=" + mobile
				+ ", password=" + password + ", oldPasswords=" + oldPasswords
				+ ", lastPasswordChangeDate=" + lastPasswordChangeDate
				+ ", passwordRecoveryIds=" + passwordRecoveryIds
				+ ", UserPicturesIds=" + UserPicturesIds + ", newUser="
				+ newUser + ", accessToken=" + accessToken
				+ ", fromAuthorization=" + fromAuthorization + ", expiryDate="
				+ expiryDate + ", upComingTestsSize=" + upComingTestsSize + "]";
	}

	public void addPropertyChangeListener(PropertyChangeListener pcl) {
		pcs.addPropertyChangeListener(pcl);
	}

	public void removePropertyChangeListener(PropertyChangeListener pcl) {
		pcs.removePropertyChangeListener(pcl);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		Object o = evt.getSource();
		if (o instanceof WelcomePageBean) {
			UpdateHelperClass.updateAzureUser(evt, this);
		} else if (o instanceof UserSettingsModel) {
			UpdateHelperClass.updateUserPasswordRecovery(evt, this);
		}

	}

	public AzureUser(RegistrationForm rf) {
		this.firstName = rf.getFirstName().toUpperCase();
		this.lastName = rf.getLastName().toUpperCase();
		this.username = rf.getUsername().toLowerCase();
		this.password = rf.getPassword1();
		this.gender = rf.getGender();
		this.userID = LoginHelper.getNextId();
		if (rf.isUseEmail()) {
			this.email = rf.getUsername().toLowerCase();
		} else if (rf.isUseMobile()) {
			this.mobile = rf.getUsername();
		}

		this.upComingTests = new ArrayList<>();
		this.friendsId = new ArrayList<>();
		this.picture = rf.getPicture();
		this.passwordRecoveryIds = new TreeSet<String>();
		this.UserPicturesIds = new TreeSet<String>();
		this.oldPasswords = new ArrayList<>();
		if (rf.getPassword1() != null) {
			oldPasswords.add(rf.getPassword1());
		}
		if (rf.getPasswordRecovery() != null) {
			this.passwordRecoveryIds.add(rf.getPasswordRecovery().getKey()
					.getName());
		}

	}

	public AzureUser(com.google.appengine.api.datastore.Entity e) {
		this.attends = (String) e.getProperty(StringConstants.cAttends);
		this.authorized = (Boolean) e.getProperty(StringConstants.cAuthorized);
		this.country = (String) e.getProperty(StringConstants.cCountry);
		this.cover = (String) e.getProperty(StringConstants.cCover);
		this.dateOfBirth = (String) e.getProperty(StringConstants.cDateOfBirth);
		this.email = (String) e.getProperty(StringConstants.cEMail);
		this.firstName = (String) e.getProperty(StringConstants.cFirstName);
		this.friendsId = (List<String>) e.getProperty(StringConstants.cFriends);
		this.gender = (String) e.getProperty(StringConstants.cGender);
		this.lastName = (String) e.getProperty(StringConstants.cLastName);
		this.lastSeenDate = (Date) e.getProperty(StringConstants.cLastSeenDate);
		this.lastTestTaken = (String) e
				.getProperty(StringConstants.cLastTestTaken);
		this.middleName = (String) e.getProperty(StringConstants.cMiddleName);
		this.mobile = (String) e.getProperty(StringConstants.cMobile);
		this.password = (String) e.getProperty(StringConstants.cPassword);
		this.passwordRecoveryIds = new TreeSet<String>(
				((List<String>) e
						.getProperty(StringConstants.cPasswordRecoveryIds)));
		this.picture = (String) e.getProperty(StringConstants.cPicture);
		this.school = ((String) e.getProperty(StringConstants.cSchool));
		this.state = ((String) e.getProperty(StringConstants.cState));
		this.subscriptionDate = (Date) e
				.getProperty(StringConstants.cSubscriptionDate);
		this.upComingTests = (List<UpComingTest>) e
				.getProperty(StringConstants.cUpComingTests);
		this.updateNameFromIdp = (Boolean) e
				.getProperty(StringConstants.cUpdateName);
		this.userID = e.getKey().getName();
		this.username = (String) e.getProperty(StringConstants.cUsername);
		this.validity = (String) e.getProperty(StringConstants.cValidity);
		this.oldPasswords = (List<String>) e
				.getProperty(StringConstants.cOldPasswords);
		this.lastPasswordChangeDate = (Date) e
				.getProperty(StringConstants.cLastPasswordChangeDate);
		this.UserPicturesIds = (Set<String>) e
				.getProperty(StringConstants.cUserPicturesIds);
		this.setsClass((String) e.getProperty("Class"));
		this.setAreaOfInterest((List<String>) e.getProperty("AreaOfInterest"));
		Object o = e.getProperty(StringConstants.cFreeAccess);
		if (o != null) {
			this.freeAccess = (boolean) e
					.getProperty(StringConstants.cFreeAccess);
		}

	}

}
