/**
 * 
 */
package eu.rn.praktikum2;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;

/**
 * @author abt434
 *
 */
public class ChatClient
{

    private String localHost;
    private int serverPort;
    private String userName;
    private Socket socket;
    private boolean connected;
    // private MyFileWriter myWriter;

    private DataOutputStream outToServer;
    private BufferedReader inFromServer;

    /**
     * @param localHost
     * @param serverPort
     * @param userName
     */
    public ChatClient(String localHost, int serverPort)
    {
        this.localHost = localHost;
        this.serverPort = serverPort;
        connected = false;

        startConnection();
    }

    /**
     * @throws IOException
     * 
     */
    private void startConnection()
    {
        Scanner sc;
        String input;
        String messageFromServer;

        try
        {
            socket = new Socket(localHost, serverPort);
            outToServer = new DataOutputStream(socket.getOutputStream());
            inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            connected = false;
            // myWriter = new MyFileWriter("ChatLogFile.txt", "files/");

            sc = new Scanner(System.in);
            System.out.println("Bitte Nutzernamen eingeben:");
            userName = sc.nextLine();

            writeToServer("HELO");
            if (readFromServer().equals("HELO"))
            {
                writeToServer("USER " + userName);
                connected = true;
            }

            Thread t = new Thread() {
                public void run() {
                    try
                    {
                        while(true)
                        writeToServer(sc.nextLine());
                    }
                    catch (IOException e)
                    {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            };
            t.start();

            while (connected)
            {

                messageFromServer = readFromServer();
                System.out.println(messageFromServer);
                if (messageFromServer.equals("Verbindung beendet"))
                {
                    connected = false;
                }
            }

            socket.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    /**
     * @return
     * @throws IOException
     */
    private String readFromServer() throws IOException
    {
        String result = inFromServer.readLine();
        // myWriter.writeLine("Server: " + result);
        return result;
    }

    /**
     * @param input
     * @throws IOException
     */
    private void writeToServer(String input) throws IOException
    {
        outToServer.writeBytes(input + "\r\n");
        // myWriter.writeLine("Client: " + input);
    }

    /**
     * @param args
     */
    public static void main(String[] args)
    {
        // new ChatClient("localhost",45619);
        new ChatClient("192.168.178.23", 45619);

    }

}
