package com.lte.discovery.reconciliation.adapter.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import com.lte.discovery.reconciliation.adapter.exception.AdapterIOException;

public class AdapterUtilsTest {

	@Test
	public void emptyFoldertest() {

		String sourceFolder = "C:\\Users\\suniln\\app\\LTEDiscoveryAndReconciliation\\GIT\\DiscoveryAndReconciliationEngine\\src\\test\\resource\\empty\\source";
		String destinationFolder = "C:\\Users\\suniln\\app\\LTEDiscoveryAndReconciliation\\GIT\\DiscoveryAndReconciliationEngine\\src\\test\\resource\\empty\\destination";
		try {
			AdapterUtils.copyFiles(sourceFolder, destinationFolder);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AdapterIOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		File destination = new File(destinationFolder);

		assertTrue(destination.isDirectory());

		assertEquals("folder is not empty", 0, destination.list().length);
	}

	@Test
	public void copySingleFile() {

		String sourceFolder = "C:\\Users\\suniln\\app\\LTEDiscoveryAndReconciliation\\GIT\\DiscoveryAndReconciliationEngine\\src\\test\\resource\\SourceDirectory";
		String destinationFolder = "C:\\Users\\suniln\\app\\LTEDiscoveryAndReconciliation\\GIT\\DiscoveryAndReconciliationEngine\\src\\test\\resource\\DestinationDirectory";

		try {
			AdapterUtils.copyFiles(sourceFolder, destinationFolder);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AdapterIOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		File destination = new File(destinationFolder);

		assertTrue(destination.isDirectory());

		assertEquals("mismatch in number of files", 1,
				destination.list().length);
	}

	@Test
	public void copyMultipleFiles() {

		String sourceFolder = "C:\\Users\\suniln\\app\\LTEDiscoveryAndReconciliation\\GIT\\DiscoveryAndReconciliationEngine\\src\\test\\resource\\SourceDirectory_multi";
		String destinationFolder = "C:\\Users\\suniln\\app\\LTEDiscoveryAndReconciliation\\GIT\\DiscoveryAndReconciliationEngine\\src\\test\\resource\\DestinationDirectory_multi";

		try {
			AdapterUtils.copyFiles(sourceFolder, destinationFolder);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AdapterIOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		File destination = new File(destinationFolder);

		assertTrue(destination.isDirectory());
		assertEquals("mismatch in number of files", 2,
				destination.list().length);
	}

	@Test(expected = AdapterIOException.class)
	public void sourceFolderNotExists() throws IOException, AdapterIOException {

		String sourceFolder = "C:\\Users\\suniln\\app\\LTEDiscoveryAndReconciliation\\GIT\\DiscoveryAndReconciliationEngine\\src\\test\\resource\\SourceDirectory_notExsist";
		String destinationFolder = "C:\\Users\\suniln\\app\\LTEDiscoveryAndReconciliation\\GIT\\DiscoveryAndReconciliationEngine\\src\\test\\resource\\DestinationDirectory_multi";

		AdapterUtils.copyFiles(sourceFolder, destinationFolder);

	}

	@Test
	public void sourceFolderContainsSubFolder() {

		String sourceFolder = "C:\\Users\\suniln\\app\\LTEDiscoveryAndReconciliation\\GIT\\DiscoveryAndReconciliationEngine\\src\\test\\resource\\SourceDirectory_subFolder";
		String destinationFolder = "C:\\Users\\suniln\\app\\LTEDiscoveryAndReconciliation\\GIT\\DiscoveryAndReconciliationEngine\\src\\test\\resource\\DestinationDirectory_subFolder";

		try {
			AdapterUtils.copyFiles(sourceFolder, destinationFolder);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AdapterIOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		File destination = new File(destinationFolder);

		assertTrue(destination.isDirectory());
		assertEquals("mismatch in number of files", 2,
				destination.list().length);
	}

	@Test
	public void cleanFolder() {
		String folderPath = "C:\\Users\\suniln\\app\\LTEDiscoveryAndReconciliation\\GIT\\DiscoveryAndReconciliationEngine\\src\\test\\resource\\tempDestinationDirectory";
		AdapterUtils.cleanFolder(folderPath);
		
		File folder = new File(folderPath);
		
		assertEquals("Mismatch number of files.", 0, folder.listFiles(AdapterUtils.getFileFilter()).length);
	}

}
