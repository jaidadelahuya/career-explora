package com.jevalab.helper.classes;

import java.io.Serializable;

public class VoguePay implements Serializable {
	
	private static final String MERCHANT_ID = "2941-0033213";
	private String merchantId , merchantRef, memo;

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getMerchantRef() {
		return merchantRef;
	}

	public void setMerchantRef(String merchantRef) {
		this.merchantRef = merchantRef;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public VoguePay(String merchantRef) {
		super();
		this.merchantId = MERCHANT_ID;
		this.merchantRef = merchantRef;
		this.memo = "Payment for "+merchantRef;
	}

	@Override
	public String toString() {
		return "VoguePay [merchantId=" + merchantId + ", merchantRef="
				+ merchantRef + ", memo=" + memo + "]";
	}
	
	

}
