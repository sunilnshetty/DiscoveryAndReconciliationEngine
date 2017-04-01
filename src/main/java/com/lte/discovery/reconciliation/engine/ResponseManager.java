package com.lte.discovery.reconciliation.engine;

import java.io.StringWriter;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import com.lte.discovery.reconciliation.adapter.dto.NE;
import com.lte.discovery.reconciliation.adapter.response.AdapterResponse;
import com.lte.discovery.reconciliation.adapter.response.AnyListType;
import com.lte.discovery.reconciliation.adapter.response.ManagedElementListType;
import com.lte.discovery.reconciliation.adapter.response.ManagedElementType;
import com.lte.discovery.reconciliation.adapter.response.MultipleMeObjectsResponseType;
import com.lte.discovery.reconciliation.adapter.response.NamingAttributeType;
import com.lte.discovery.reconciliation.adapter.response.ObjectFactory;
import com.lte.discovery.reconciliation.adapter.response.RelativeDistinguishNameType;

public class ResponseManager {

	public static String marshal(List<NE> nelist) {
		System.out.println("ResponseManager.marshal()");

		StringWriter stringWriter = new StringWriter();

		try {
			ObjectFactory objectFactory = new ObjectFactory();

			AdapterResponse adapterResponse = objectFactory
					.createAdapterResponse();

			MultipleMeObjectsResponseType multipleMeObjectsResponseType = objectFactory
					.createMultipleMeObjectsResponseType();

			ManagedElementListType managedElementListType = objectFactory
					.createManagedElementListType();

			for (NE ne : nelist) {

				ManagedElementType managedElementType = objectFactory
						.createManagedElementType();

				managedElementType.setDiscoveredName(ne.getNeName());

				NamingAttributeType namingAttributeType = objectFactory
						.createNamingAttributeType();
				RelativeDistinguishNameType relativeDistinguishNameType = objectFactory
						.createRelativeDistinguishNameType();
				relativeDistinguishNameType.setType("Name");
				relativeDistinguishNameType.setValue(ne.getNeName());
				namingAttributeType.getRdn().add(relativeDistinguishNameType);

				JAXBElement<NamingAttributeType> jaxbNamingAttributeType = objectFactory
						.createCommonObjectInfoTypeName(namingAttributeType);
				managedElementType.setName(jaxbNamingAttributeType);

				NamingAttributeType namingAttributeType1 = objectFactory
						.createNamingAttributeType();
				relativeDistinguishNameType = objectFactory
						.createRelativeDistinguishNameType();
				relativeDistinguishNameType.setType("NE Type");
				relativeDistinguishNameType.setValue(ne.getNeType());
				namingAttributeType1.getRdn().add(relativeDistinguishNameType);

				JAXBElement<NamingAttributeType> jaxbNamingAttributeType1 = objectFactory
						.createCommonObjectInfoTypeName(namingAttributeType1);

				AnyListType anyListType = objectFactory.createAnyListType();

				anyListType.getAny().add(jaxbNamingAttributeType1);

				JAXBElement<AnyListType> jaxbAnyListType = objectFactory
						.createCommonObjectInfoTypeVendorExtensions(anyListType);
				managedElementType.setVendorExtensions(jaxbAnyListType);

				managedElementListType.getMe().add(managedElementType);

			}

			multipleMeObjectsResponseType.setMeList(managedElementListType);
			adapterResponse.setNetworkElements(multipleMeObjectsResponseType);

			JAXBContext jaxbContext = JAXBContext
					.newInstance(AdapterResponse.class);
			Marshaller marshaller = jaxbContext.createMarshaller();

			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");

			marshaller.marshal(adapterResponse, stringWriter);

		} catch (JAXBException e) {
			System.err.println(e.getMessage());
		}

		return stringWriter.toString();
	}

}
