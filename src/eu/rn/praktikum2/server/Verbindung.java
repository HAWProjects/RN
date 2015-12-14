package eu.rn.praktikum2.server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Set;

/**
 * Modelliert eine Verbindung zu einem Client
 * 
 * @author abt434
 *
 */
public class Verbindung extends Thread {

	private int threadNumber;
	private Socket socket;
	private ChatServer server;
	private BufferedReader input;
	private DataOutputStream output;
	private boolean working = false;
	private MyFileWriter writer;
	private String username;

	public Verbindung(int indexThread, Socket arbeitsSocketConn, ChatServer chatServer) {
		this.threadNumber = indexThread;
		this.socket = arbeitsSocketConn;
		this.server = chatServer;

	}

	@Override
	public void run() {
		String currentInput;
		System.out.println("TCP Worker Thread " + threadNumber + " is running until QUIT is received!");
		try {
			input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			output = new DataOutputStream(socket.getOutputStream());
			writer = new MyFileWriter("ChatLogFile.txt", "files/");

			if (readFromClient().equals("HELO")) {
				writeToClient("HELO");
				username = readFromClient().replace("USER ", "");
				Set<String> users = server.getUsernames().getNames();
				if(users.contains(username))
				{
				    for (int i = 2; i < Integer.MAX_VALUE; i++)
                    {
                        if(!users.contains(username + i))
                        {
                            username += i;
                            break;
                        }
                    }
				    writeToClient("gewünschter Nutzname belegt. Sie heißen nun "+username);
				}
				server.getUsernames().fuegeHinzu(username);
				working = true;
				}
			

			while (working) {
				currentInput = readFromClient();
				if(currentInput.length() > 500)
				{
				    writeToClient("Eingabe darf max. 500 Zeichen sein.");
				}
				else if (currentInput.equals("/quit")) {
				    writeToClient("Verbindung beendet");
				    working = false;
				}
				else if (currentInput.equals("/users")) {

					writeToClient(server.getUsernames().getNames().toString());
				} else
				{
					server.writeToSockets(username + ": " + currentInput);
				}


			}

		} catch (Exception e) {

		} finally {
			System.out.println("TCP Worker Thread " + threadNumber + " stopped!");
			server.getUsernames().entferne(username);
			server.getVerbindungen().getVerbindungen().remove(this);
			try
            {
                socket.close();
            }
            catch (IOException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
			server.getSemaphore().release();
			server.writeToSockets(username + " hat den chat verlassen.");
		}
	}

	/**
	 * Liest eine Zeile vom Client
	 * 
	 * @throws IOException
	 */
	private String readFromClient() throws IOException {
		String inFromClient = input.readLine();
		inFromClient = new String(Base64.decode(inFromClient));
		System.out.println("TCP Worker Thread " + threadNumber + " detected job: " + inFromClient);
//		server.writeToSockets(inFromClient);
		return inFromClient;
	}

	/**
	 * Sendet einen Text an den Client
	 * 
	 * @param value
	 *            der zu sendende Text
	 * @throws IOException
	 */
	public synchronized void writeToClient(String value) throws IOException {
	    value = eu.rn.praktikum.Base64.encodeBytes(value.getBytes());
		output.writeBytes(value + "\r\n");

		// writer.writeLine(""+ value);
	}

}
