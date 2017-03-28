package com.lte.discovery.reconciliation.engine;

import static org.junit.Assert.*;

import org.junit.Test;

import com.lte.discovery.reconciliation.engine.dto.RequestDetails;

public class RequestManagerTest {

	@Test
	public void test() {
		RequestDetails requestDetails = new RequestDetails();
		requestDetails.setNmsEmsName("Ericsson OSS-RC");
		requestDetails.setNmsEmsType("EMS");

		String actualResult = RequestManager.create(requestDetails);

		System.out.println("mtosiRequest: " + actualResult);

		String expectedResult = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
				+ "<ns2:getManagedElementRequest xmlns:ns2=\"http://www.tmforum.org/mtop/mri/xsd/mer/v1\" xmlns=\"http://www.tmforum.org/mtop/fmw/xsd/nam/v1\">"
				+ "<ns2:meRef>"
				+ "<rdn>"
				+ "<type>EMS</type>"
				+ "<value>Ericsson OSS-RC</value>"
				+ "</rdn>"
				+ "</ns2:meRef>"
				+ "</ns2:getManagedElementRequest>";
		assertEquals(expectedResult, actualResult);
	}

}
