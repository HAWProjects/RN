/**
 * 
 */
package eu.rn.praktikum2;

import java.util.concurrent.Semaphore;

/**
 * @author abt434
 *
 */
public class ChatServer {

	private final int serverPort;
	
	private Semaphore semaphore;
	
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
		// TODO Auto-generated method stub
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		new ChatServer(45619, 2);

	}

}
