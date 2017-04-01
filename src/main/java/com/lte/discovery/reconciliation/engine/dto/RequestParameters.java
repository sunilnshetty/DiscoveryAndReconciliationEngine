package com.lte.discovery.reconciliation.engine.dto;

import java.io.Serializable;

public class RequestParameters implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String host;

	private int port;

	private String userName;

	private String password;

	private String sourceFolder;

	private String destinationFolder;

	@Override
	public String toString() {
		return new String("[RequestParameters: host = " + host + " port = "
				+ port + " userName = " + userName + " password = " + password + " sourceFolder = "
				+ sourceFolder + " destinationFolder = " + destinationFolder + "]");
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSourceFolder() {
		return sourceFolder;
	}

	public void setSourceFolder(String sourceFolder) {
		this.sourceFolder = sourceFolder;
	}

	public String getDestinationFolder() {
		return destinationFolder;
	}

	public void setDestinationFolder(String destinationFolder) {
		this.destinationFolder = destinationFolder;
	}

}
