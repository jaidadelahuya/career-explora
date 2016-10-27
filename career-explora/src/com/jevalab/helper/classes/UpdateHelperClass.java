package com.jevalab.helper.classes;

import java.beans.PropertyChangeEvent;
import java.util.Set;

import com.google.appengine.api.datastore.Key;
import com.jevalab.azure.persistence.AzureUser;
import com.jevalab.azure.persistence.PasswordRecoveryJpaController;
import com.jevalab.exceptions.NonexistentEntityException;
import com.jevalab.exceptions.RollbackFailureException;

public class UpdateHelperClass {

	public static void updateAzureUser(PropertyChangeEvent evt, AzureUser user) {
		String pptName = evt.getPropertyName();
		Object value = evt.getNewValue();
		if (pptName.equalsIgnoreCase(StringConstants.FIRSTNAME)
				&& value != null) {
			user.setFirstName(value.toString());
		} else if (pptName.equalsIgnoreCase(StringConstants.LASTNAME)
				&& value != null) {
			user.setLastName(value.toString());
		} else if (pptName.equalsIgnoreCase(StringConstants.MIDDLENAME)
				&& value != null) {
			user.setMiddleName(value.toString());
		} else if (pptName.equalsIgnoreCase(StringConstants.EMAIL)
				&& value != null) {
			user.setEmail(value.toString());
		}   else if (pptName.equalsIgnoreCase(StringConstants.SCHOOL)
				&& value != null) {
			user.setSchool(value.toString());
		} else if (pptName.equalsIgnoreCase(StringConstants.STATE)
				&& value != null) {
			user.setState(value.toString());
		} else if (pptName.equalsIgnoreCase(StringConstants.COUNTRY)
				&& value != null) {
			user.setCountry(value.toString());
		}  else if (pptName.equalsIgnoreCase(StringConstants.PICTURE) && value != null) {
			user.setPicture(value.toString());
		} else if (pptName.equalsIgnoreCase(StringConstants.COVER) && value != null) {
			user.setCover(value.toString());
		}
	}



	

	public static void updateUserPasswordRecovery(PropertyChangeEvent evt,
			AzureUser azureUser) {
		Object o1 = evt.getOldValue();
		Object o2 = evt.getNewValue();
		
		if(o1 instanceof PasswordRecovery && o2 instanceof PasswordRecovery) {
			PasswordRecovery old = (PasswordRecovery) o1;
			PasswordRecovery nw = (PasswordRecovery) o2;
			
			Set<String> ids = azureUser.getPasswordRecoveryIds();
			
			for(String id : ids) {
				if(id.equals(old.getKey().getName())) {
					ids.remove(id);
					ids.add(nw.getKey().getName());
					PasswordRecoveryJpaController c1 = new PasswordRecoveryJpaController();
					try {
						c1.destroy(old.getKey());
						c1.create(nw);
					} catch (NonexistentEntityException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (RollbackFailureException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			}
		}
		
	}

	public static void updateWelcomePage(PropertyChangeEvent evt,
			WelcomePageBean welcomePageBean) {
		// TODO Auto-generated method stub
		
	}

}
