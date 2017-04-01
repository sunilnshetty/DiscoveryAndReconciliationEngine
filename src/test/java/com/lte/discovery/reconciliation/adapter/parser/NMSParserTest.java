package com.lte.discovery.reconciliation.adapter.parser;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.lte.discovery.reconciliation.adapter.dto.NE;
import com.lte.discovery.reconciliation.adapter.util.AdapterUtils;

public class NMSParserTest {

	@Test
	public void singleNETest() {
		NMSParser nmsParser = new NMSParser();
		File dataFolder = new File(
				"C:\\Users\\suniln\\app\\LTEDiscoveryAndReconciliation\\GIT\\DiscoveryAndReconciliationEngine\\src\\test\\resource\\DestinationDirectory");

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
		
		

		assertEquals("Mismatch in number of NE", 1, neList.size());
		
		NE ne = neList.get(0);
				
		assertEquals("Mismatch in number of NE", "ENODEB001", ne.getNeName());
		assertEquals("Mismatch in number of NE", "NE=1077", ne.getNeFdn());
		assertEquals("Mismatch in number of NE", "ENODE-B", ne.getNeType());
	}
	
	@Test
	public void multipleNETest() {
		NMSParser nmsParser = new NMSParser();
		File dataFolder = new File(
				"C:\\Users\\suniln\\app\\LTEDiscoveryAndReconciliation\\GIT\\DiscoveryAndReconciliationEngine\\src\\test\\resource\\DestinationDirectory_multi");

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
		
		

		assertEquals("Mismatch in number of NE", 2, neList.size());
		
		NE ne = neList.get(0);
				
		assertEquals("Mismatch in number of NE", "ENODEB001", ne.getNeName());
		assertEquals("Mismatch in number of NE", "NE=1077", ne.getNeFdn());
		assertEquals("Mismatch in number of NE", "ENODE-B", ne.getNeType());
	}

}
