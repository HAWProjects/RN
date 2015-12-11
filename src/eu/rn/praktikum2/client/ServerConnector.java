package eu.rn.praktikum2.client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

public class ServerConnector extends Observable
{
    
    private Socket socket;
    private DataOutputStream outToServer;
    private BufferedReader inFromServer;

    public ServerConnector(String hostname, String serverPort) throws Exception
    {
        socket = new Socket(hostname, Integer.parseInt(serverPort));
        outToServer = new DataOutputStream(socket.getOutputStream());
        inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        new Thread()
        {
            public void run()
            {
                String messageFromServer;
                while (true)
                {
                    try
                    {
                        messageFromServer = readFromServer();
                        gui.getTextArea().append(messageFromServer+"\r\n");

                        gui.getTextArea().setCaretPosition(gui.getTextArea().getDocument().getLength());
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }
    
    /**
     * Sendet Text an den Server
     * 
     * @param input
     *            Text, der an den Server gesendet werden soll
     * @throws IOException
     */
    public void writeToServer(String input)
    {
        try
        {
            input = Base64.encode(input.getBytes());
            outToServer.writeBytes(input + "\r\n");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        // myWriter.writeLine("Client: " + input);
    }
    
    /**
     * 
     * Liest eine Zeile vom Server
     * 
     * @throws IOException
     */
    public String readFromServer() throws IOException
    {
        // myWriter.writeLine("Server: " + result);
        return new String(Base64.decode(inFromServer.readLine()));
    }
    
}
