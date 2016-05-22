package com.jevalab.helper.classes;

public class IpnConfig {
	private String ipnUrl;
	private String receiverEmail;
	private String paymentAmount;
	private String paymentCurrency;

	public IpnConfig() {
		this.ipnUrl = StringConstants.PAYPAY_SANDBOX_URL;
		this.receiverEmail = StringConstants.PAYPAL_EMAIL;
		this.paymentAmount = StringConstants.PAYPAL_AMOUNT;
		this.paymentCurrency = StringConstants.PAYPAL_CURRENCY;
	}

	public String getIpnUrl() {
		return ipnUrl;
	}

	public void setIpnUrl(String ipnUrl) {
		this.ipnUrl = ipnUrl;
	}

	public String getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(String paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	public String getPaymentCurrency() {
		return paymentCurrency;
	}

	public void setPaymentCurrency(String paymentCurrency) {
		this.paymentCurrency = paymentCurrency;
	}

	public String getReceiverEmail() {
		return receiverEmail;
	}

	public void setReceiverEmail(String receiverEmail) {
		this.receiverEmail = receiverEmail;
	}

}
