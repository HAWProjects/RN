/**
 * 
 */
package eu.rn.praktikum;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Properties;

/**
 * @author Robert
 *		
 */
public class MailFile {
	
	private final String mailAdressReciever;
	private final String filePath;
	
	private static final String ABSENDER_MAILADRESSE = "mail.mailadresse";
	private static final String ABSENDER_BENUTZERNAME = "mail.benutzername";
	private static final String ABSENDER_PW = "mail.passwort";
	private static final String ABSENDER_HOSTNAME = "mail.hostname";
	private static final String ABSENDER_PORTSMTP = "mail.portSMTP";
	private static final String ABSENDER_PORTSMTPS = "mail.portSMTPS";
	
	private MyFileWriter myFileWriter;
	
	private String outputLogfileName = "";
	private String outputLogfilePath = "";
	

	
	/**
	 * 
	 * @param mailAdressReciever
	 * @param filePath
	 * @throws IOException
	 */
	public MailFile(String mailAdressReciever, String filePath) throws IOException {
		this.myFileWriter = new MyFileWriter(outputLogfileName, outputLogfilePath);
		this.mailAdressReciever = mailAdressReciever;
		this.filePath = filePath;
		Properties prop = new Properties();
		try {
			prop.load(getClass().getClassLoader().getResourceAsStream("mail.properties"));
		} catch(IOException e) {
			throw new FileNotFoundException("PropertieFile not found");
		}
		
		String mailadresse = prop.getProperty(ABSENDER_MAILADRESSE);
		String benutzername = prop.getProperty(ABSENDER_BENUTZERNAME);
		String pw = prop.getProperty(ABSENDER_PW);
		String absenderHostname = prop.getProperty(ABSENDER_HOSTNAME);
		int port = Integer.parseInt(prop.getProperty(ABSENDER_PORTSMTP));
		
		erzeugeVerbindung(mailadresse, benutzername, pw, absenderHostname, port);
		
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
	private void erzeugeVerbindung(String mailadresse, String benutzername, String pw, String absenderHostname, int port) {
		try {
			// Handshake
			Socket socket = new Socket(absenderHostname, port);
			DataInputStream dis = new DataInputStream(socket.getInputStream());
			DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
	
			
			// transfer
			// header + Body codieren
			dos.writeBytes("HELO\n");
			dos.writeBytes("MAIL From: " + Base64.decode(ABSENDER_MAILADRESSE) + "\n");
			dos.writeBytes("RCPT To: " + Base64.decode(mailAdressReciever) + "\n");
			dos.writeBytes("DATA\n");
			dos.writeBytes("DATA\n");
			// nachricht
			dos.writeBytes("");
			
			
			dos.writeBytes("\n.\n");
			// Schlie�en
			dos.writeBytes("QUIT");
			
			String inLine;
			while((inLine = dis.readLine()) != null) {
				System.out.println("Antwort: " + inLine);
				if(inLine.indexOf("Ok") != -1) {
					break;
				}
			}
			
			dos.close();
			dis.close();
			socket.close();
			
		} catch(UnknownHostException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			new MailFile("robert.scheffel@haw-hamburg.de", "/RN_Praktikum/files/TestFile.txt");
//			new MailFile(args[0], args[1]);
		} catch(IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
