package eu.rn.praktikum2.client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import eu.rn.praktikum.Base64;


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
        
        // ständig Nachrichten vom Server weiterleiten
        new Thread()
        {
            public void run()
            {
                while (true)
                {
                    try
                    {
                        leiteNachrichtWeiter(readFromServer());
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }
    
    public Socket getSocket()
    {
        return socket;
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
            input = Base64.encodeBytes(input.getBytes());
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
