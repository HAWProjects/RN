package eu.rn.praktikum2.protokolle;

import java.net.Socket;

import eu.rn.praktikum2.client.Observable;
import eu.rn.praktikum2.client.Observer;
import eu.rn.praktikum2.client.ServerConnector;

public abstract class ChatProtokoll extends Observable implements Observer
{

    private ServerConnector conn;
    
    public ChatProtokoll(String hostname, String serverPort) throws Exception
    {
        conn = new ServerConnector(hostname, serverPort);
    }
    
    abstract void nachrichtSenden(String s);
    
    abstract void handshake();
    
}
