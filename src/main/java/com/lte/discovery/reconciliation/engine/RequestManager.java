package com.lte.discovery.reconciliation.engine;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.lte.discovery.reconciliation.engine.dto.RequestDetails;
import com.lte.discovery.reconciliation.engine.dto.RequestParameters;
import com.lte.discovery.reconciliation.managed.element.GetManagedElementRequest;
import com.lte.discovery.reconciliation.managed.element.NamingAttributeType;
import com.lte.discovery.reconciliation.managed.element.ObjectFactory;
import com.lte.discovery.reconciliation.managed.element.RelativeDistinguishNameType;
import com.lte.discovery.reconciliation.managed.element.RequestType;

public class RequestManager {

	/**
	 * Process {@link RequestDetails} and generate MTOSI request for adapter.
	 * 
	 * @param requestDetails
	 * @return
	 */
	public static String marshal(RequestDetails requestDetails) {
		System.out.println("RequestManager.create()");

		StringWriter stringWriter = new StringWriter();

		try {
			JAXBContext jaxbContext = JAXBContext
					.newInstance(GetManagedElementRequest.class);
			Marshaller marshaller = jaxbContext.createMarshaller();

			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");

			GetManagedElementRequest getManagedElementRequest = new GetManagedElementRequest();

			NamingAttributeType namingAttributeType = new NamingAttributeType();

			RelativeDistinguishNameType relativeDistinguishNameType = new RelativeDistinguishNameType();

			relativeDistinguishNameType.setType(requestDetails.getNmsEmsType());
			relativeDistinguishNameType
					.setValue(requestDetails.getNmsEmsName());

			namingAttributeType.getRdn().add(relativeDistinguishNameType);

			RequestParameters requestParameters = requestDetails
					.getRequestParameters();

			RequestType requestType = new RequestType();
			requestType.setHost(requestParameters.getHost());
			requestType.setPort(requestParameters.getPort());
			requestType.setUserName(requestParameters.getUserName());
			requestType.setPassword(requestParameters.getPassword());
			requestType.setSourceFolder(requestParameters.getSourceFolder());
			requestType.setDestinationFolder(requestParameters
					.getDestinationFolder());

			getManagedElementRequest.setRequestParams(requestType);

			getManagedElementRequest.setMeRef(namingAttributeType);

			marshaller.marshal(getManagedElementRequest, stringWriter);

		} catch (JAXBException e) {
			System.err.println(e.getMessage());
		}

		return stringWriter.toString();
	}

	public static GetManagedElementRequest unmarshal(String input)
			throws JAXBException {

		JAXBContext jaxbContext = JAXBContext
				.newInstance(GetManagedElementRequest.class);
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

		StringReader stringReader = new StringReader(input);
		return (GetManagedElementRequest) unmarshaller.unmarshal(stringReader);
	}

}
