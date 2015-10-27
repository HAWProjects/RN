/**
 * 
 */
package eu.rn.praktikum;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketImpl;
import java.net.SocketImplFactory;
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
	
	
	
	/**
	 * 
	 * @param mailAdressReciever
	 * @param filePath
	 * @throws IOException
	 */
	public MailFile(String mailAdressReciever, String filePath) throws IOException {
		this.mailAdressReciever = mailAdressReciever;
		this.filePath = filePath;
		Properties prop = new Properties();
		try {
			prop.load(getClass().getClassLoader().getResourceAsStream("mail.properties"));
		} catch (IOException e) {
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
	 * @param mailadresse
	 * @param benutzername
	 * @param pw
	 * @param absenderHostname
	 * @param port
	 */
	private void erzeugeVerbindung(String mailadresse, String benutzername, String pw, String absenderHostname,
			int port) {
		try {
			Socket socket = new Socket(absenderHostname,port);
			//Handshake
			
			
			//transfer
			// header + Body codieren
			socket.getOutputStream().write(1);
			
			
			//Schlieﬂen
			
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}




























	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			new MailFile(args[0],args[1]);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
