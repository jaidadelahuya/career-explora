package com.jevalab.helper.classes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RegistrationForm implements Serializable {

	private String firstName, lastName, username, password1, password2, gender,
			confirmationCode, picture;
	PasswordRecovery passwordRecovery;
	private boolean useMobile, useEmail;

	public PasswordRecovery getPasswordRecovery() {
		return passwordRecovery;
	}

	public void setPasswordRecovery(PasswordRecovery passwordRecovery) {
		this.passwordRecovery = passwordRecovery;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword1() {
		return password1;
	}

	public void setPassword1(String password1) {
		this.password1 = password1;
	}

	public String getPassword2() {
		return password2;
	}

	public void setPassword2(String password2) {
		this.password2 = password2;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getConfirmationCode() {
		return confirmationCode;
	}

	public void setConfirmationCode(String confirmationCode) {
		this.confirmationCode = confirmationCode;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public boolean isUseMobile() {
		return useMobile;
	}

	public void setUseMobile(boolean useMobile) {
		this.useMobile = useMobile;
	}

	public boolean isUseEmail() {
		return useEmail;
	}

	public void setUseEmail(boolean useEmail) {
		this.useEmail = useEmail;
	}

	@Override
	public String toString() {
		return "RegistrationForm [firstName=" + firstName + ", lastName="
				+ lastName + ", username=" + username + ", password1="
				+ password1 + ", password2=" + password2 + ", gender=" + gender
				+ ", confirmationCode=" + confirmationCode + ", picture="
				+ picture + ", passwordRecovery=" + passwordRecovery
				+ ", useMobile=" + useMobile + ", useEmail=" + useEmail + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((gender == null) ? 0 : gender.hashCode());
		result = prime * result
				+ ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result
				+ ((username == null) ? 0 : username.hashCode());
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
		RegistrationForm other = (RegistrationForm) obj;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (gender == null) {
			if (other.gender != null)
				return false;
		} else if (!gender.equals(other.gender))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	public RegistrationForm(String firstName, String lastName, String username,
			String password1, String password2, String gender,
			boolean useMobile, boolean useEmail,
			PasswordRecovery passwordRecovery) {

		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username.toLowerCase();
		this.password1 = password1;
		this.password2 = password2;
		this.gender = gender;
		this.useMobile = useMobile;
		this.useEmail = useEmail;
		this.confirmationCode = Util.generateRandomCode(100000, 900000);
		System.out.println(confirmationCode);
		if ("male".equalsIgnoreCase(gender)) {
			this.picture = "/images/male-unknown-user.jpg";
		} else {
			this.picture = "/images/female-unknown-user.jpg";
		}
		
		this.passwordRecovery = passwordRecovery;
	}

	public RegistrationForm(String username, boolean useMobile, boolean useEmail) {

		this.username = username.toLowerCase();
		this.useMobile = useMobile;
		this.useEmail = useEmail;
		this.confirmationCode = Util.generateRandomCode(100000, 900000);
		System.out.println(confirmationCode);

	}

	public RegistrationForm(String firstName, String lastName, String username,
			String password1, String password2, String gender,
			PasswordRecovery passWordRecovery) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username.toLowerCase();
		this.password1 = password1;
		this.password2 = password2;
		this.gender = gender;
		this.passwordRecovery = passWordRecovery;
		this.confirmationCode = Util.generateRandomCode(100000, 900000);
		if ("male".equalsIgnoreCase(gender)) {
			this.picture = "/images/male-unknown-user.jpg";
		} else {
			this.picture = "/images/female-unknown-user.jpg";
		}
		this.useEmail = false;
		this.useMobile = false;
		System.out.println(confirmationCode);

	}

}
