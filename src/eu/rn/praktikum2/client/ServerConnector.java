package eu.rn.praktikum2.client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

public class ServerConnector
{
    
    private Socket socket;
    private DataOutputStream outToServer;
    private BufferedReader inFromServer;

    public ServerConnector(String hostname, String serverPort) throws Exception
    {
        socket = new Socket(hostname, Integer.parseInt(serverPort));
        outToServer = new DataOutputStream(socket.getOutputStream());
        inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }
    
}
