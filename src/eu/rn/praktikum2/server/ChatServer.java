/**
 * 
 */
package eu.rn.praktikum2.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Semaphore;

import javafx.scene.control.Label;

/**
 * Modelliert einen Server, zu dem sich mehrere Clients verbinden können um miteinander zu kommunizieren.
 * 
 * @author abt434
 *
 */
public class ChatServer extends Thread {

	private final int serverPort;
	
	private Semaphore semaphore;
	
	private MyFileWriter writer;
	
	private Verbindungen verbindungen;
	
	private Userliste users;
	
	private ChatServerController controller;
	
	/**
	 * @param port Port auf dem der Server Anfragen annehmen soll
	 * @param maxClientAnzahl Anzahl der Clients, die sich verbinden können
	 */
	public ChatServer(int port, int maxClientAnzahl, ChatServerController controller) {
		serverPort = port;
		semaphore = new Semaphore(maxClientAnzahl);
		verbindungen = new Verbindungen();
		users = new Userliste();
		this.controller = controller;
		
	}
	
	public void run(){
		startServer();
	}

	/**
	 * Startet den Chat-Server
	 */
	private void startServer() {
		ServerSocket mainSocketServer; // Socket was auf Anfragen wartet und arbeitsthreads erzeugt
		Socket arbeitsSocketConn; // Verbindungssocket für den Arbeitsthread
		
		int indexThread = 0;
		try{
			writer = new MyFileWriter("ChatLogFile.txt", "files/");
			mainSocketServer = new ServerSocket(serverPort);
			
			
			while(true){
				semaphore.acquire();
				System.out.println("Server waiting for Connection");
//				controller.getvBoxOutput().getChildren().add(new Label("Server waiting for Connection"));
				arbeitsSocketConn = mainSocketServer.accept();
				indexThread++;
				Verbindung neuesSocket = new Verbindung(indexThread,arbeitsSocketConn,this);
				verbindungen.fuegeHinzu(neuesSocket);
				neuesSocket.start();
			}
		}catch(Exception e){
			
		}
		
	}
	
	/**
	 * Sendet Text an alle verbundenen Clients
	 * @param text der zu sendende Text
	 */
	public synchronized void writeToSockets(String text)
	{
	    for(Verbindung s : verbindungen.getVerbindungen())
	    {
	        try
            {
                s.writeToClient(text);
            }
            catch (IOException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
	    }
	}
	
	public Semaphore getSemaphore(){
		return semaphore;
	}

	
//	public static void main(String[] args) {
//		new ChatServer(45619, 4);
//	}
	
	public Userliste getUsernames()
	{
		return users;
	}
	
	protected ChatServerController getController(){
		return controller;
	}

}
