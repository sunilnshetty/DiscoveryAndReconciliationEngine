package com.lte.discovery.reconciliation.adapter.parser;

import java.io.InputStream;
import java.util.Iterator;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import com.lte.discovery.reconciliation.adapter.dto.NE;

public class NMSParser {
	XMLInputFactory xmlInputFacotry = null;
	XMLEventReader eventReader = null;
	XMLEvent event = null;
	private StartElement startElement = null;
	private NE ne = null;
	private Attribute attribute = null;


	public NE parse(InputStream inputStream) {
		xmlInputFacotry = XMLInputFactory.newInstance();

		try {
			eventReader = xmlInputFacotry.createXMLEventReader(inputStream);
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		while (eventReader.hasNext()) {
			try {
				event = eventReader.nextEvent();

				if (event.isStartElement()) {
					startElement = event.asStartElement();

					if ("NE".equalsIgnoreCase(startElement.getName().toString())) {

						createNE();
					}
				}

			} catch (XMLStreamException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return ne;
	}

	

	private void createNE() {
		// TODO Auto-generated method stub
		ne = new NE();

		@SuppressWarnings("rawtypes")
		Iterator attributes = startElement.getAttributes();
		while (attributes.hasNext()) {
			attribute = (Attribute) attributes.next();
			if ("NEFdn".equalsIgnoreCase(attribute.getName().toString())) {
				ne.setNeFdn(attribute.getValue());
			} else if ("NEName"
					.equalsIgnoreCase(attribute.getName().toString())) {
				ne.setNeName(attribute.getValue());
			} else if ("NEType"
					.equalsIgnoreCase(attribute.getName().toString())) {
				ne.setNeType(attribute.getValue());
			}
		}
	}
}
