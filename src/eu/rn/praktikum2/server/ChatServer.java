/**
 * 
 */
package eu.rn.praktikum2.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Semaphore;

/**
 * Modelliert einen Server, zu dem sich mehrere Clients verbinden können um miteinander zu kommunizieren.
 * 
 * @author abt434
 *
 */
public class ChatServer {

	private final int serverPort;
	
	private Semaphore semaphore;
	
	private MyFileWriter writer;
	
	private Verbindungen verbindungen;
	
	private Userliste users;
	
	/**
	 * @param port Port auf dem der Server Anfragen annehmen soll
	 * @param maxClientAnzahl Anzahl der Clients, die sich verbinden können
	 */
	public ChatServer(int port, int maxClientAnzahl) {
		serverPort = port;
		semaphore = new Semaphore(maxClientAnzahl);
		verbindungen = new Verbindungen();
		users = new Userliste();
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

	
	public static void main(String[] args) {
		new ChatServer(45619, 4);
	}
	
	public String getUsernames()
	{
		return users.getNames();
	}

}
