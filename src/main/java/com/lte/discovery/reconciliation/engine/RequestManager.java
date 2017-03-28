package com.lte.discovery.reconciliation.engine;

import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import com.lte.discovery.reconciliation.engine.dto.RequestDetails;
import com.lte.discovery.reconciliation.managed.element.GetManagedElementRequest;
import com.lte.discovery.reconciliation.managed.element.NamingAttributeType;
import com.lte.discovery.reconciliation.managed.element.ObjectFactory;
import com.lte.discovery.reconciliation.managed.element.RelativeDistinguishNameType;

public class RequestManager {

	/**
	 * Process {@link RequestDetails} and generate MTOSI request for adapter.
	 * 
	 * @param requestDetails
	 * @return 
	 */
	public static String create(RequestDetails requestDetails) {
		System.out.println("RequestManager.create()");
		
		StringWriter stringWriter = new StringWriter();
		
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(GetManagedElementRequest.class);
			Marshaller marshaller = jaxbContext.createMarshaller();
			
//			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
			
			GetManagedElementRequest getManagedElementRequest = new GetManagedElementRequest();
			
			NamingAttributeType namingAttributeType = new NamingAttributeType();
			
			RelativeDistinguishNameType relativeDistinguishNameType = new RelativeDistinguishNameType();
			
			relativeDistinguishNameType.setType(requestDetails.getNmsEmsType());
			relativeDistinguishNameType.setValue(requestDetails.getNmsEmsName());
			
			namingAttributeType.getRdn().add(relativeDistinguishNameType);
			
			getManagedElementRequest.setMeRef(namingAttributeType);
			
			marshaller.marshal(getManagedElementRequest, stringWriter);
			
		} catch (JAXBException e) {
			System.err.println(e.getMessage());
		}
		
		return stringWriter.toString();
	}

}
