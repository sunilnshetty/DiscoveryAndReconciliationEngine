package com.lte.discovery.reconciliation.adapter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;

import com.lte.discovery.reconciliation.adapter.dto.NE;
import com.lte.discovery.reconciliation.adapter.exception.AdapterIOException;
import com.lte.discovery.reconciliation.adapter.parser.NMSParser;
import com.lte.discovery.reconciliation.adapter.util.AdapterUtils;
import com.lte.discovery.reconciliation.engine.RequestManager;
import com.lte.discovery.reconciliation.engine.ResponseManager;
import com.lte.discovery.reconciliation.managed.element.GetManagedElementRequest;
import com.lte.discovery.reconciliation.managed.element.RequestType;

public class NMSAdapter {

	public static String process(String adapterRequest) {
		System.out.println("NMSAdapter.process()");

		GetManagedElementRequest getManagedElementRequest = null;

		RequestType requestType = null;
		try {
			getManagedElementRequest = RequestManager.unmarshal(adapterRequest);

			requestType = getManagedElementRequest.getRequestParams();
			
			cleanDestinationFolder(requestType.getDestinationFolder());

			AdapterUtils.copyFiles(requestType.getSourceFolder(),
					requestType.getDestinationFolder());
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AdapterIOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return generateAdapterResponse(parseDataFiles(requestType
				.getDestinationFolder()));

	}

	private static void cleanDestinationFolder(String destinationFolder) {
		// TODO Auto-generated method stub
		
	}

	private static String generateAdapterResponse(List<NE> neList) {

		return ResponseManager.marshal(neList);

	}

	private static List<NE> parseDataFiles(String destinationFolder) {
		NMSParser nmsParser = new NMSParser();
		File dataFolder = new File(destinationFolder);

		List<NE> neList = new ArrayList<NE>();

		for (File dataFile : dataFolder.listFiles(AdapterUtils.getFileFilter())) {
			InputStream inputStream = null;
			try {
				inputStream = new FileInputStream(dataFile);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			neList.add(nmsParser.parse(inputStream));
		}
		return neList;
	}
}
