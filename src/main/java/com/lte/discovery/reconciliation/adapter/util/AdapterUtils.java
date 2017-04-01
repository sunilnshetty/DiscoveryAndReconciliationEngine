package com.lte.discovery.reconciliation.adapter.util;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;

import org.apache.openjpa.lib.util.Files;

import com.lte.discovery.reconciliation.adapter.exception.AdapterIOException;

public class AdapterUtils {
	
	public static void cleanFolder(String folderPath) {
		File folder = new File(folderPath);
		
		for(File dataFiles : folder.listFiles(getFileFilter())) {
			dataFiles.delete();
		}
	}

	public static void copyFiles(String sourceFolder, String destinationFolder)
			throws IOException, AdapterIOException {
		File source = new File(sourceFolder);

		if (source.listFiles() != null) {
			for (File file : source.listFiles(getFileFilter())) {
				String destinationFile = destinationFolder + File.separator
						+ file.getName();
				File destination = new File(destinationFile);
				Files.copy(file, destination);
			}
		} else {
			throw new AdapterIOException("Folder " + sourceFolder + "does not exist.");
		}
	}

	public static FileFilter getFileFilter() {

		return new FileFilter() {

			@Override
			public boolean accept(File file) {
				return file.isFile();
			}
		};
	}
}
