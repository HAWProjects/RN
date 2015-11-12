package eu.rn.praktikum;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Properties;

public class MailFile2 {
	/* Portnummer */
	private int serverPort = -1;
	/* Hostname */
	private String hostname = null;
	private Socket clientSocket; // TCP-Standard-Socketklasse
	private DataOutputStream outToServer; // Ausgabestream zum Server
	private BufferedReader inFromServer; // Eingabestream vom Server
	private boolean serviceRequested = true; // Client beenden?
	static Properties properties = null;

	public static void main(String[] args) throws IOException {
		// String reciAddress = args[0];
		// String anhangFilePath = args[1];
		properties = new Properties();
		BufferedInputStream stream = new BufferedInputStream(
				new FileInputStream("properties/rnMailConfig.properties"));
		properties.load(stream);
		stream.close();
		// System.out.println(body);
		MailFile2 mf = new MailFile2();
		mf.hostname = properties.getProperty("smtpHost");
		mf.serverPort = Integer.parseInt(properties.getProperty("smtpPort"));
		mf.startJob();
	}

	public void startJob() {
		try {
			/* Socket erzeugen --> Verbindungsaufbau mit dem Server */
			clientSocket = new Socket(hostname, serverPort);
			/* Socket-Basisstreams durch spezielle Streams filtern */
			outToServer = new DataOutputStream(clientSocket.getOutputStream());
			inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			writeToServer("EHLO " + hostname);

			writeToServer("AUTH LOGIN");
			String nutzername = properties.getProperty("nutzername");
			writeToServer(Base64.encodeBytes(nutzername.getBytes()));
			String passwort = properties.getProperty("passwort");
			writeToServer(Base64.encodeBytes(passwort.getBytes()));
			String absenderadresse = properties.getProperty("absenderadresse");
			writeToServer("MAIL FROM: <" + absenderadresse + ">");
			String reciAddress = "robert.scheffel@haw-hamburg.de";
			writeToServer("RCPT TO: <" + reciAddress + ">");
			writeToServer("");
			writeToServer("DATA");
			String body = properties.getProperty("mailBody");
			writeToServer(body + '\r' + '\n' + '.');
			String reply;
			while ((reply = inFromServer.readLine()) != null) {
				System.out.println(reply);
			}
			/* Socket-Streams schliessen --> Verbindungsabbau */
			clientSocket.close();
		} catch (IOException e) {
			System.err.println("Connection aborted by server!");
		}
		System.out.println("TCP Client stopped!");
	}

	private void writeToServer(String request) throws IOException {
		/* Sende eine Zeile (mit CRLF) zum Server */
		outToServer.writeBytes(request + '\r' + '\n');
		System.out.println("TCP Client has sent the message: " + request);
	}
}