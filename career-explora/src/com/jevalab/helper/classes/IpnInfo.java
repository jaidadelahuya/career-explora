package com.jevalab.helper.classes;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.datanucleus.api.jpa.annotations.Extension;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

@Entity
public class IpnInfo implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Key key;
	private String txnId;
	private String userId;
	private String paymentStatus;
	@Extension(vendorName = "datanucleus", key = "gae.unindexed", value = "true")
	private String paymentAmount;
	@Extension(vendorName = "datanucleus", key = "gae.unindexed", value = "true")
	private String paymentCurrency;
	@Extension(vendorName = "datanucleus", key = "gae.unindexed", value = "true")
	private String receiverEmail;
	@Extension(vendorName = "datanucleus", key = "gae.unindexed", value = "true")
	private String payerEmail;
	@Extension(vendorName = "datanucleus", key = "gae.unindexed", value = "true")
	private Long logTime;

	@Transient
	private String response;
	@Transient
	private String requestParams;
	@Transient
	private String error;
	@Transient
	private String custom;
	@Transient
	private String itemName;


	public IpnInfo(String userId, String txnId) {
		key = KeyFactory.createKey(IpnInfo.class.getSimpleName(), userId+txnId);
		this.userId = userId;
		this.txnId = txnId;
	}
	
	

	public String getUserId() {
		return userId;
	}



	public void setUserId(String userId) {
		this.userId = userId;
	}



	public Key getKey() {
		return key;
	}



	public void setKey(Key key) {
		this.key = key;
	}



	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public Long getLogTime() {
		return logTime;
	}

	public void setLogTime(Long logTime) {
		this.logTime = logTime;
	}

	public String getPayerEmail() {
		return payerEmail;
	}

	public void setPayerEmail(String payerEmail) {
		this.payerEmail = payerEmail;
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

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public String getReceiverEmail() {
		return receiverEmail;
	}

	public void setReceiverEmail(String receiverEmail) {
		this.receiverEmail = receiverEmail;
	}

	public String getRequestParams() {
		return requestParams;
	}

	public void setRequestParams(String requestParams) {
		this.requestParams = requestParams;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public String getTxnId() {
		return txnId;
	}

	public void setTxnId(String txnId) {
		this.txnId = txnId;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getCustom() {
		return custom;
	}

	public void setCustom(String custom) {
		this.custom = custom;
	}

	@Override
	public String toString() {
		return "IpnInfo [key=" + key + ", paymentAmount=" + paymentAmount
				+ ", paymentCurrency=" + paymentCurrency + ", txnId=" + txnId
				+ ", receiverEmail=" + receiverEmail + ", payerEmail="
				+ payerEmail + ", logTime=" + logTime + ", response="
				+ response + ", requestParams=" + requestParams + ", error="
				+ error + ", custom=" + custom + ", itemName=" + itemName
				+ ", paymentStatus=" + paymentStatus + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((custom == null) ? 0 : custom.hashCode());
		result = prime * result
				+ ((payerEmail == null) ? 0 : payerEmail.hashCode());
		result = prime * result
				+ ((paymentAmount == null) ? 0 : paymentAmount.hashCode());
		result = prime * result
				+ ((paymentCurrency == null) ? 0 : paymentCurrency.hashCode());
		result = prime * result
				+ ((receiverEmail == null) ? 0 : receiverEmail.hashCode());
		result = prime * result + ((txnId == null) ? 0 : txnId.hashCode());
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
		IpnInfo other = (IpnInfo) obj;
		if (custom == null) {
			if (other.custom != null)
				return false;
		} else if (!custom.equals(other.custom))
			return false;
		if (payerEmail == null) {
			if (other.payerEmail != null)
				return false;
		} else if (!payerEmail.equals(other.payerEmail))
			return false;
		if (paymentAmount == null) {
			if (other.paymentAmount != null)
				return false;
		} else if (!paymentAmount.equals(other.paymentAmount))
			return false;
		if (paymentCurrency == null) {
			if (other.paymentCurrency != null)
				return false;
		} else if (!paymentCurrency.equals(other.paymentCurrency))
			return false;
		if (receiverEmail == null) {
			if (other.receiverEmail != null)
				return false;
		} else if (!receiverEmail.equals(other.receiverEmail))
			return false;
		if (txnId == null) {
			if (other.txnId != null)
				return false;
		} else if (!txnId.equals(other.txnId))
			return false;
		return true;
	}

}
