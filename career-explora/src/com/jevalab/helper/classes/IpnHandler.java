package com.jevalab.helper.classes;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.jevalab.azure.persistence.AzureUser;
import com.jevalab.exceptions.IpnException;

public class IpnHandler {

	private final static Logger logger = Logger.getLogger(IpnHandler.class
			.getName());
	private IpnConfig ipnConfig;
	private IpnInfoService ipnInfoService;

	/**
	 * This method handles the Paypal IPN Notification as follows: 1. Read all
	 * posted request parameters 2. Prepare 'notify-validate' command with
	 * exactly the same parameters 3. Post above command to Paypal IPN URL
	 * {@link IpnConfig#ipnUrl} 4. Read response from Paypal 5. Capture Paypal
	 * IPN information 6. Validate captured Paypal IPN Information 6.1. Check
	 * that paymentStatus=Completed 6.2. Check that txnId has not been
	 * previously processed 6.3. Check that receiverEmail matches with
	 * configured {@link IpnConfig#receiverEmail} 6.4. Check that paymentAmount
	 * matches with configured {@link IpnConfig#paymentAmount} 6.5. Check that
	 * paymentCurrency matches with configured {@link IpnConfig#paymentCurrency}
	 * 7. In case of any failed validation checks, throw {@link IpnException} 8.
	 * If all is well, return {@link IpnInfo} to the caller for further business
	 * logic execution
	 * 
	 * @param request
	 *            {@link HttpServletRequest}
	 * @return {@link IpnInfo}
	 * @throws IpnException
	 */

	public IpnHandler() {
		this.ipnConfig = new IpnConfig();
		this.ipnInfoService = new IpnInfoService();
	}

	public IpnInfo handleIpn(String res, HttpServletRequest request,
			String requestParams) throws IpnException {

		Object o = null;
		AzureUser user = null;
		HttpSession session = request.getSession();
		IpnInfo ipnInfo = null;
		if (session.isNew()) {

		} else {
			o = session.getAttribute(StringConstants.AZURE_USER);
			if (o == null) {

			} else {
				user = (AzureUser) o;
				ipnInfo = new IpnInfo(user.getUserID(),request.getParameter("txn_id"));

				try {

					// 5. Capture Paypal IPN information
					ipnInfo.setLogTime(System.currentTimeMillis());
					ipnInfo.setItemName(request.getParameter("item_name"));
					ipnInfo.setPaymentStatus(request
							.getParameter("payment_status"));
					ipnInfo.setPaymentAmount(request.getParameter("mc_gross"));
					ipnInfo.setPaymentCurrency(request
							.getParameter("mc_currency"));
					ipnInfo.setTxnId(request.getParameter("txn_id"));
					ipnInfo.setReceiverEmail(request
							.getParameter("receiver_email"));
					ipnInfo.setPayerEmail(request.getParameter("payer_email"));
					ipnInfo.setCustom(request.getParameter("custom"));
					ipnInfo.setResponse(res);
					ipnInfo.setRequestParams(requestParams);

					// 6. Validate captured Paypal IPN Information
					if (res.equals("VERIFIED")) {

						// 6.1. Check that paymentStatus=Completed
						if (ipnInfo.getPaymentStatus() == null
								|| !ipnInfo.getPaymentStatus()
										.equalsIgnoreCase("COMPLETED"))
							ipnInfo.setError("payment_status IS NOT COMPLETED {"
									+ ipnInfo.getPaymentStatus() + "}");

						// 6.2. Check that txnId has not been previously
						// processed
						String oldTxnId = this.getIpnInfoService()
								.getIpnInfo(ipnInfo.getTxnId());
						if (oldTxnId != null)
							ipnInfo.setError("txn_id is already processed "
									+ oldTxnId);

						// 6.3. Check that receiverEmail matches with configured
						// {@link
						// IpnConfig#receiverEmail}
						if (!ipnInfo.getReceiverEmail().equalsIgnoreCase(
								this.getIpnConfig().getReceiverEmail()))
							ipnInfo.setError("receiver_email "
									+ ipnInfo.getReceiverEmail()
									+ " does not match with configured ipn email "
									+ this.getIpnConfig().getReceiverEmail());

						// 6.4. Check that paymentAmount matches with configured
						// {@link
						// IpnConfig#paymentAmount}
						if (Double.parseDouble(ipnInfo.getPaymentAmount()) != Double
								.parseDouble(this.getIpnConfig()
										.getPaymentAmount()))
							ipnInfo.setError("payment amount mc_gross "
									+ ipnInfo.getPaymentAmount()
									+ " does not match with configured ipn amount "
									+ this.getIpnConfig().getPaymentAmount());

						// 6.5. Check that paymentCurrency matches with
						// configured
						// {@link IpnConfig#paymentCurrency}
						if (!ipnInfo.getPaymentCurrency().equalsIgnoreCase(
								this.getIpnConfig().getPaymentCurrency()))
							ipnInfo.setError("payment currency mc_currency "
									+ ipnInfo.getPaymentCurrency()
									+ " does not match with configured ipn currency "
									+ this.getIpnConfig().getPaymentCurrency());
					} else {
						ipnInfo.setError("Inavlid response {" + res
								+ "} expecting {VERIFIED}");
					}

						logger.info("ipnInfo = " + ipnInfo);

						this.getIpnInfoService().log(ipnInfo);
					

					// 7. In case of any failed validation checks, throw {@link
					// IpnException}
					if (ipnInfo.getError() != null)
						throw new IpnException(ipnInfo.getError());
				} catch (Exception e) {
					if (e instanceof IpnException)
						throw (IpnException) e;
					logger.log(Level.SEVERE, e.toString(), e);
					throw new IpnException(e.toString());
				}

			}

		}
		// 8. If all is well, return {@link IpnInfo} to the caller for
		// further
		// business logic execution
		return ipnInfo;

	}

	/**
	 * Utility method to extract all request parameters and their values from
	 * request object
	 * 
	 * @param request
	 *            {@link HttpServletRequest}
	 * @return all request parameters in the form: param-name 1 param-value
	 *         param-name 2 param-value param-value (in case of multiple values)
	 */
	public static String getAllRequestParams(HttpServletRequest request) {
		Map map = request.getParameterMap();
		StringBuilder sb = new StringBuilder("\nREQUEST PARAMETERS\n");
		for (Iterator it = map.keySet().iterator(); it.hasNext();) {
			String pn = (String) it.next();
			sb.append(pn).append("\n");
			String[] pvs = (String[]) map.get(pn);
			for (int i = 0; i < pvs.length; i++) {
				String pv = pvs[i];
				sb.append("\t").append(pv).append("\n");
			}
		}
		return sb.toString();
	}

	public IpnConfig getIpnConfig() {
		return ipnConfig;
	}

	public void setIpnConfig(IpnConfig ipnConfig) {
		this.ipnConfig = ipnConfig;
	}

	public IpnInfoService getIpnInfoService() {
		return ipnInfoService;
	}

	public void setIpnInfoService(IpnInfoService ipnInfoService) {
		this.ipnInfoService = ipnInfoService;
	}
}
