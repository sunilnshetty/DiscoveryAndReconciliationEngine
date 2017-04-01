package com.lte.discovery.reconciliation.adapter;

import java.io.File;
import java.io.IOException;

import org.apache.openjpa.lib.util.Files;


public class SftpTest {

	public static void main(String[] args) {/*
		JSch jsch = new JSch();

		try {
			Session sessionRead = jsch.getSession("suniln", "127.0.0.1", 22);
			sessionRead.connect();

			Session sessionWrite = jsch.getSession("suniln", "127.0.0.1", 22);
			sessionWrite.connect();
			
			ChannelSftp channelRead = (ChannelSftp) sessionRead.openChannel("sftp");
			channelRead.connect();
			
			ChannelSftp channelWrite = (ChannelSftp) sessionWrite.openChannel("sftp");
			channelWrite.connect();
			
			PipedInputStream pipedInputStream = new PipedInputStream(2048);
			PipedOutputStream pipedOutputStream = new PipedOutputStream(pipedInputStream);
			
			channelRead.get("C:/Users/suniln/Desktop/source/DailyNotes.txt",pipedOutputStream);
			channelWrite.put(pipedInputStream, "C:/Users/suniln/Desktop/destination/DailyNotes.txt");
			
			channelRead.disconnect();
			channelWrite.disconnect();
			
			sessionRead.disconnect();
			sessionWrite.disconnect();

		} catch (JSchException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		} catch (SftpException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}

	*/
		
		File source = new File("C:/Users/suniln/Desktop/source/DailyNotes.txt");
		File destination = new File("C:/Users/suniln/Desktop/destination/DailyNotes.txt");
		try {
			Files.copy(source, destination);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
