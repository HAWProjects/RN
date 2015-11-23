/**
 * 
 */
package eu.rn.praktikum2;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Semaphore;

/**
 * @author abt434
 *
 */
public class ChatServer {

	private final int serverPort;
	
	private Semaphore semaphore;
	
	private MyFileWriter writer;
	
	/**
	 * @param port
	 * @param thread
	 */
	public ChatServer(int port, int threadCount) {
		serverPort = port;
		semaphore = new Semaphore(threadCount);
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
				new ArbeitsSocket(indexThread,arbeitsSocketConn,this).start();				
			}
		}catch(Exception e){
			
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
