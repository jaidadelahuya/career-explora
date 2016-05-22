package com.jevalab.helper.classes;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.jevalab.azure.persistence.IpnInfoJpaController;
import com.jevalab.exceptions.IpnException;
import com.jevalab.exceptions.PreexistingEntityException;
import com.jevalab.exceptions.RollbackFailureException;

public class IpnInfoService {

	/**
	 * Store Paypal IPN Notification related information for future use
	 * 
	 * @param ipnInfo
	 *            {@link IpnInfo}
	 * @throws IpnException
	 */
	public void log(final IpnInfo ipnInfo) throws IpnException {
		IpnInfoJpaController cont = new IpnInfoJpaController();
		try {
			cont.create(ipnInfo);
		} catch (PreexistingEntityException e) {
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

	/**
	 * Fetch Paypal IPN Notification related information saved earlier
	 * 
	 * @param txnId
	 *            Paypal IPN Notification's Transaction ID
	 * @return {@link IpnInfo}
	 * @throws IpnException
	 */
	public String getIpnInfo(final String txnId) throws IpnException {
		IpnInfo ipnInfo = null;
		IpnInfoJpaController c = new IpnInfoJpaController();
		String id = c.findIpnInfo(txnId);
		return id;
		
	}
}
