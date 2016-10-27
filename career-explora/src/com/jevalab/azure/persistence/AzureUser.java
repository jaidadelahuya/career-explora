package com.jevalab.azure.persistence;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.ArrayList;
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
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Text;
import com.jevalab.helper.classes.LoginHelper;
import com.jevalab.helper.classes.RegistrationForm;
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
	
	private Key key;

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

	@Column(name = "LastTestTaken")
	@Extension(vendorName = "datanucleus", key = "gae.unindexed", value = "true")
	private String lastTestTaken;

	@Column(name = "DateOfBirth")
	@Extension(vendorName = "datanucleus", key = "gae.unindexed", value = "true")
	private Date dateOfBirth;

	@Column(name = "LastSeenDate")
	@Extension(vendorName = "datanucleus", key = "gae.unindexed", value = "true")
	private Date lastSeenDate;

	@Column(name = "authorized")
	@Extension(vendorName = "datanucleus", key = "gae.unindexed", value = "true")
	private boolean authorized;	

	@Column(name = "SubscriptionDate")
	@Extension(vendorName = "datanucleus", key = "gae.unindexed", value = "true")
	private Date subscriptionDate;

	@Column(name = "Validity")
	@Extension(vendorName = "datanucleus", key = "gae.unindexed", value = "true")
	private String validity;

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
	private List<Key> friendsId;

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
	private boolean fromAuthorization;
	
	private Text about, hobbies;
	
	private List<Key> collections, followers, following;
	
	

	public List<Key> getFollowers() {
		return followers;
	}

	public void setFollowers(List<Key> followers) {
		this.followers = followers;
	}

	public List<Key> getFollowing() {
		return following;
	}

	public void setFollowing(List<Key> following) {
		this.following = following;
	}

	public List<Key> getCollections() {
		return collections;
	}

	public void setCollections(List<Key> collections) {
		this.collections = collections;
	}

	public PropertyChangeSupport getPcs() {
		return pcs;
	}

	public void setPcs(PropertyChangeSupport pcs) {
		this.pcs = pcs;
	}

	public Text getHobbies() {
		return hobbies;
	}

	public void setHobbies(Text hobbies) {
		this.hobbies = hobbies;
	}

	

	public Text getAbout() {
		return about;
	}

	public void setAbout(Text about) {
		this.about = about;
	}

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


	public boolean isAuthorized() {
		return authorized;
	}

	public void setAuthorized(boolean authorized) {
		this.authorized = authorized;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		Date oldDateOfBirth = this.dateOfBirth;
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



	public String getState() {
		return state;
	}

	public void setState(String state) {
		String oldState = this.state;
		if (state != null) {
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
	
	

	public Key getKey() {
		return KeyFactory.createKey(AzureUser.class.getSimpleName(), this.userID);
	}

	public void setKey(Key key) {
		this.key = key;
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



	@Override
	public String toString() {
		return "AzureUser [takenTalentTest=" + takenTalentTest + ", pcs=" + pcs
				+ ", userID=" + userID + ", key=" + key + ", firstName="
				+ firstName + ", lastName=" + lastName + ", middleName="
				+ middleName + ", gender=" + gender + ", email=" + email
				+ ", state=" + state + ", country=" + country + ", school="
				+ school + ", lastTestTaken=" + lastTestTaken
				+ ", dateOfBirth=" + dateOfBirth + ", lastSeenDate="
				+ lastSeenDate + ", authorized=" + authorized
				+ ", subscriptionDate=" + subscriptionDate + ", validity="
				+ validity + ", picture=" + picture + ", cover=" + cover
				+ ", friendsId=" + friendsId + ", username=" + username
				+ ", mobile=" + mobile + ", password=" + password
				+ ", oldPasswords=" + oldPasswords + ", communities="
				+ communities + ", areaOfInterest=" + areaOfInterest
				+ ", lastPasswordChangeDate=" + lastPasswordChangeDate
				+ ", passwordRecoveryIds=" + passwordRecoveryIds
				+ ", UserPicturesIds=" + UserPicturesIds + ", sClass=" + sClass
				+ ", newUser=" + newUser + ", fromAuthorization="
				+ fromAuthorization + ", about=" + about + ", hobbies="
				+ hobbies + "]";
	}

	public List<Key> getFriendsId() {
		return friendsId;
	}

	public void setFriendsId(List<Key> friendsId) {
		this.friendsId = friendsId;
	}

}
