/**
 * 
 */
package eu.rn.praktikum2;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Semaphore;

/**
 * @author abt434
 *
 */
public class ChatServer {

	private final int serverPort;
	
	private Semaphore semaphore;
	
	private MyFileWriter writer;
	
	private Set<ArbeitsSocket> sockets;
	
	/**
	 * @param port
	 * @param thread
	 */
	public ChatServer(int port, int threadCount) {
		serverPort = port;
		semaphore = new Semaphore(threadCount);
		sockets = new HashSet<ArbeitsSocket>();
		startServer();
	}

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
				ArbeitsSocket neuesSocket = new ArbeitsSocket(indexThread,arbeitsSocketConn,this);
				sockets.add(neuesSocket);
				neuesSocket.start();
			}
		}catch(Exception e){
			
		}
		
	}
	
	public void writeToSockets(String text)
	{
	    for(ArbeitsSocket s : sockets)
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

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new ChatServer(45619, 4);
	}

}
