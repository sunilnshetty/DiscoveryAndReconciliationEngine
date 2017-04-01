package com.lte.discovery.reconciliation.engine;

import static org.junit.Assert.assertEquals;

import javax.xml.bind.JAXBException;

import org.junit.Test;

import com.lte.discovery.reconciliation.engine.dto.RequestDetails;
import com.lte.discovery.reconciliation.engine.dto.RequestParameters;
import com.lte.discovery.reconciliation.managed.element.GetManagedElementRequest;
import com.lte.discovery.reconciliation.managed.element.RelativeDistinguishNameType;

public class RequestManagerTest {

	@Test
	public void marshall() {
		RequestDetails requestDetails = new RequestDetails();
		requestDetails.setNmsEmsName("Ericsson OSS-RC");
		requestDetails.setNmsEmsType("EMS");

		RequestParameters requestParameters = new RequestParameters();
		requestParameters.setUserName("suniln");
		requestParameters.setHost("127.0.0.1");
		requestParameters.setPort(22);
		requestParameters.setPassword("1234");
		requestParameters
				.setSourceFolder("/engine/src/test/resource/SourceDirectory");
		requestParameters
				.setDestinationFolder("/engine/src/test/resource/DestinationDirectory");

		requestDetails.setRequestParameters(requestParameters);

		String actualResult = RequestManager.marshal(requestDetails);

		System.out.println("mtosiRequest: " + actualResult);
	}

	@Test
	public void unmarshall() {
		RequestDetails requestDetails = new RequestDetails();
		requestDetails.setNmsEmsName("Ericsson OSS-RC");
		requestDetails.setNmsEmsType("EMS");

		String host = "127.0.0.1";
		int port = 22;
		String userName = "suniln";
		String password = "1234";
		String sourceFolder = "/engine/src/test/resource/SourceDirectory";
		String destinationFolder = "/engine/src/test/resource/DestinationDirectory";

		RequestParameters requestParameters = new RequestParameters();
		requestParameters.setUserName(userName);
		requestParameters.setHost(host);
		requestParameters.setPort(port);
		requestParameters.setPassword(password);
		requestParameters.setSourceFolder(sourceFolder);
		requestParameters.setDestinationFolder(destinationFolder);

		requestDetails.setRequestParameters(requestParameters);

		String actualResult = RequestManager.marshal(requestDetails);

		try {
			GetManagedElementRequest getManagedElementRequest = RequestManager
					.unmarshal(actualResult);

			RelativeDistinguishNameType rdn = getManagedElementRequest
					.getMeRef().getRdn().get(0);

			assertEquals("RDN Type mismatch", "EMS", rdn.getType());
			assertEquals("RDN Value mismatch", "Ericsson OSS-RC",
					rdn.getValue());

			assertEquals("Host mismatch", host, getManagedElementRequest
					.getRequestParams().getHost());
			assertEquals("Port mismatch", port, getManagedElementRequest
					.getRequestParams().getPort());
			assertEquals("User name mismatch", userName,
					getManagedElementRequest.getRequestParams().getUserName());
			assertEquals("Password mismatch", password,
					getManagedElementRequest.getRequestParams().getPassword());
			assertEquals("Source folder mismatch", sourceFolder,
					getManagedElementRequest.getRequestParams()
							.getSourceFolder());
			assertEquals("destination folder mismatch", destinationFolder,
					getManagedElementRequest.getRequestParams()
							.getDestinationFolder());
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
