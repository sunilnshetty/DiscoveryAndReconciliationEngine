package com.lte.discovery.reconciliation.engine.dto;

import java.io.Serializable;

public class RequestDetails implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String nmsEmsType;
	private String nmsEmsName;

	private RequestParameters requestParameters;

	public String getNmsEmsType() {
		return nmsEmsType;
	}

	public void setNmsEmsType(String nmsEmsType) {
		this.nmsEmsType = nmsEmsType;
	}

	public String getNmsEmsName() {
		return nmsEmsName;
	}

	public void setNmsEmsName(String nmsEmsName) {
		this.nmsEmsName = nmsEmsName;
	}

	@Override
	public String toString() {
		return new String("[RequestDetails: nmsEmsType = " + nmsEmsType
				+ " nmsEmsName = " + nmsEmsName + " nmsEmsName = "
				+ requestParameters.toString() + "]");
	}

	public RequestParameters getRequestParameters() {
		return requestParameters;
	}

	public void setRequestParameters(RequestParameters requestParameters) {
		this.requestParameters = requestParameters;
	}

}
