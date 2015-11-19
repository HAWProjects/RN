package eu.rn.praktikum2;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import eu.rn.praktikum.TCPServer;

public class ArbeitsSocket extends Thread {
	
	private int threadNumber;
	private Socket socket;
	private ChatServer server;
	private BufferedReader input;
	private DataOutputStream output;
	private boolean working = true;
	private MyFileWriter writer;
	
	public ArbeitsSocket(int indexThread, Socket arbeitsSocketConn, ChatServer chatServer) {
		this.threadNumber = indexThread;
		this.socket = arbeitsSocketConn;
		this.server = chatServer;
		
	}
	
	public void run() {
		String currentInput;
		try {
			input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			output = new DataOutputStream(socket.getOutputStream());
			writer = new MyFileWriter("ChatLogFile.txt", "files/");
			
			while(working) {
				currentInput = readFromClient().toUpperCase();
				
				writeToClient(currentInput);
				
				if(currentInput.startsWith("QUIT")){
					working = false;
				}
			}
			
		} catch(Exception e) {
		} finally {
			server.getSemaphore().release();
		}
	}
	
	private String readFromClient() throws IOException{
		return input.readLine();
	}
	
	private void writeToClient(String value) throws IOException{
		output.writeBytes(value + "\r\n");

		//		writer.writeLine(""+ value);
	}
	
}
