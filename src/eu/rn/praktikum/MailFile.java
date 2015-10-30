/**
 * 
 */
package eu.rn.praktikum;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.Properties;

/**
 * @author Robert
 * 
 */
public class MailFile {

	private final String mailAdressReceiver;
	private final String filePath;

	private static String ABSENDER_MAILADRESSE = "mail.mailadresse";
	private static String ABSENDER_BENUTZERNAME = "mail.benutzername";
	private static String ABSENDER_PW = "mail.passwort";
	private static String ABSENDER_HOSTNAME = "mail.hostname";
	private static String ABSENDER_PORTSMTP = "mail.portSMTP";
	private static String ABSENDER_PORTSMTPS = "mail.portSMTPS";
	private static String MAIL_BODY = "mail.body";

	private MyFileWriter myFileWriter;

	private String outputLogfileName = "Logfile.txt";
	private String outputLogfilePath = "files/";

	private DataOutputStream dos;
	private BufferedReader inFromServer;

	/**
	 * 
	 * @param mailAdressReceiver
	 * @param filePath
	 * @throws IOException
	 */
	public MailFile(String mailAdressReceiver, String filePath) throws IOException {
		this.myFileWriter = new MyFileWriter(outputLogfileName, outputLogfilePath);
		this.mailAdressReceiver = mailAdressReceiver;
		this.filePath = filePath;
		Properties prop = new Properties();
		try {
			prop.load(getClass().getClassLoader().getResourceAsStream("mail.properties"));
		} catch (IOException e) {
			throw new FileNotFoundException("PropertiesFile not found");
		}

		String mailadresse = prop.getProperty(ABSENDER_MAILADRESSE);
		String benutzername = prop.getProperty(ABSENDER_BENUTZERNAME);
		String pw = prop.getProperty(ABSENDER_PW);
		String absenderHostname = prop.getProperty(ABSENDER_HOSTNAME);
		int port = Integer.parseInt(prop.getProperty(ABSENDER_PORTSMTP));
		String body = prop.getProperty(MAIL_BODY).replace("\n.", "\n..").replace("\r.", "\r.."); // body mit transparency

		erzeugeVerbindung(mailadresse, benutzername, pw, absenderHostname, port, body);

	}

	/**
	 * 
	 * @param String
	 *            mailadresse
	 * @param String
	 *            benutzername
	 * @param String
	 *            pw
	 * @param String
	 *            absenderHostname
	 * @param int
	 *            port
	 */
	private void erzeugeVerbindung(String mailadresse, String benutzername, String pw, String absenderHostname,
			int port, String body) {
		try {
			// Handshake
			Socket socket = new Socket(absenderHostname, port);
			dos = new DataOutputStream(socket.getOutputStream());
			inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			// transfer
			// header + Body codieren
			receiveFromServer();

			sendToServer("EHLO " + absenderHostname);
			receiveFromServer();

			sendToServer("AUTH LOGIN");
			sendToServer(Base64.encodeBytes(benutzername.getBytes()));
			sendToServer(Base64.encodeBytes(pw.getBytes()));
			receiveFromServer();

			sendToServer("MAIL FROM: " + mailadresse);
			receiveFromServer();
			sendToServer("RCPT TO: " + mailAdressReceiver);
			receiveFromServer();
			sendToServer("DATA");
			receiveFromServer();
			
			sendToServer("From: Deine Mudder");
			sendToServer("To: Bastard");
			sendToServer("Subject: Du riechst");
			sendToServer("MIME-Version: 1.0");
			sendToServer("Content-Type: multipart/mixed; boundary=8484636278363");
			sendToServer("");
			
			sendToServer("--8484636278363");
			sendToServer("Content-Transfer-Encoding: quoted-printable");
			sendToServer("Content-Type: text/plain");
			sendToServer("");
			sendToServer(body);
			
			sendToServer("--8484636278363");
			sendToServer("Content-Transfer-Encoding: base64");
			sendToServer("Content-Type: image/jpg");
			sendToServer("Content-Disposition: attachment; filename=putty.jpg");
			sendToServer("");
			
			byte[] byteFile = Files.readAllBytes(Paths.get("files/putty.jpg"));
			String encodedString = Base64.encodeBytes(byteFile);
			sendToServer(encodedString);
			sendToServer("--8484636278363--");

			// terminieren
			sendToServer("\r\n.");
			receiveFromServer();
			// schliessen
			sendToServer("QUIT");

			dos.close();
			inFromServer.close();
			socket.close();

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}


	private void sendToServer(String message) {
		try {
			dos.writeBytes(message + "\r\n");
			message = "Client: " + message;
			myFileWriter.writeLine(message);
			System.err.println(message);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void receiveFromServer() {
		try {
			do {
				String message = "Server: " + inFromServer.readLine();
				System.err.println(message);
				myFileWriter.writeLine(message);
			} while (inFromServer.ready());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			new MailFile("robert.scheffel@haw-hamburg.de", "/RN_Praktikum/files/TestFile.txt");
			// new MailFile(args[0], args[1]);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
