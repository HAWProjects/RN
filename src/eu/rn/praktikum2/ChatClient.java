/**
 * 
 */
package eu.rn.praktikum2;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;

/**
 * @author abt434
 *
 */
public class ChatClient {
	
	private String localHost;
	private int serverPort;
	private String userName;
	private Socket socket;
	private boolean connected;
	private MyFileWriter myWriter;
	
	
	
	

	private DataOutputStream outToServer;
	private BufferedReader inFromServer;
	
	
	/**
	 * @param localHost
	 * @param serverPort
	 * @param userName
	 */
	public ChatClient(String localHost,int serverPort, String userName) {
		this.localHost = localHost;
		this.userName = userName;
		this.serverPort = serverPort;
		connected = false;
		
		startConnection();
	}

	/**
	 * @throws IOException 
	 * 
	 */
	private void startConnection() {
		Scanner sc;
		String input;
		String messageFromServer;
		
		try {
			socket = new Socket(localHost, serverPort);
			outToServer = new DataOutputStream(socket.getOutputStream());
			inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			connected = true;
			myWriter = new MyFileWriter("ChatLogFile.txt", "files/");
			
			sc = new Scanner(System.in);
			
			writeToServer("HELO");
			if(readFromServer().equals("HELO")){
				writeToServer("USER " +userName);
			}
			
			while(connected){
				
				input = sc.nextLine();
				writeToServer(input);
				messageFromServer = readFromServer();
				if(messageFromServer.startsWith("QUIT")){
					connected = false;
				}
			}
			
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * @return
	 * @throws IOException
	 */
	private String readFromServer() throws IOException {
		String result = "";
		result = inFromServer.readLine();
		myWriter.writeLine("Server: " + result);
		return result;
	}

	/**
	 * @param input
	 * @throws IOException
	 */
	private void writeToServer(String input) throws IOException {
		outToServer.writeBytes(input + "\r\n");
		myWriter.writeLine("Client: " + input);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new ChatClient("localhost",45619,"UserName");

	}

}
