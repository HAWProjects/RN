/**
 * 
 */
package eu.rn.praktikum2.client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;

import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * Modelliert einen Chat-Client mit dem der Benutzer sich mit einem Chat-Server
 * verbinden kann.
 * 
 * @author abt434
 *
 */
public class ChatClient {

	private String hostname;
	private int serverPort;
	private String userName;
	private Socket socket;
	private boolean connected;
	// private MyFileWriter myWriter;

	private DataOutputStream outToServer;
	private BufferedReader inFromServer;
	
	private ChatClientController controller;

	/**
	 * @param hostname
	 *            der Hostname
	 * @param serverPort
	 *            Port auf dem der Server Anfragen annimmt
	 */
	public ChatClient(String hostname, int serverPort, String username, ChatClientController controller) {
		this.hostname = hostname;
		this.serverPort = serverPort;
		connected = false;
		this.controller = controller;

		startConnection();
	}

	/**
	 * Startet eine Verbindung zu einem Server
	 * 
	 * @throws IOException
	 * 
	 */
	private void startConnection() {
		Scanner sc;
		
		String messageFromServer;

		try {
			socket = new Socket(hostname, serverPort);
			outToServer = new DataOutputStream(socket.getOutputStream());
			inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			connected = false;
			// myWriter = new MyFileWriter("ChatLogFile.txt", "files/");

			sc = new Scanner(System.in);
			System.out.println("Bitte Nutzernamen eingeben:");
			userName = sc.nextLine();

			writeToServer("HELO");
			if (readFromServer().equals("HELO")) {
				writeToServer("USER " + userName);
				connected = true;
			}

			Thread t = new Thread() {
				public void run() {
					while (true)
					{
						
						controller.getTxtAInput().setOnKeyReleased(new EventHandler<KeyEvent>() {
							@Override
							public void handle(KeyEvent event) {
								if(event.getCode() == KeyCode.ENTER){
									String input = controller.getTxtAInput().getText();
//										controller.getvBoxOutput().getChildren().add(label);
									controller.getTxtAInput().clear();
									if(input.equals("/users"))
									{

									}
									writeToServer(input);
									System.out.println("Tach");
								}
								
							}
						});

					}
				}
			};
			t.start();

			while (connected) {

				messageFromServer = readFromServer();
				Label label = new Label(messageFromServer);
				controller.getvBoxOutput().getChildren().add(label);
				
				System.out.println(messageFromServer);
				if (messageFromServer.equals("Verbindung beendet")) {
					connected = false;
				}
			}

			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 
	 * Liest eine Zeile vom Server
	 * 
	 * @throws IOException
	 */
	private String readFromServer() throws IOException {
		String result = inFromServer.readLine();
		// myWriter.writeLine("Server: " + result);
		return result;
	}

	/**
	 * Sendet Text an den Server
	 * 
	 * @param input
	 *            Text, der an den Server gesendet werden soll
	 * @throws IOException
	 */
	private void writeToServer(String input)  {
		try {
			outToServer.writeBytes(input + "\r\n");
		} catch(IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// myWriter.writeLine("Client: " + input);
	}

	public String getUsername()
	{
		return userName;
	}

}
