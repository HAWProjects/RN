package eu.rn.praktikum2.protokolle;

import eu.rn.praktikum2.client.Observable;
import eu.rn.praktikum2.client.Observer;
import eu.rn.praktikum2.client.ServerConnector;

public abstract class ChatProtokoll extends Observable implements Observer
{
    protected ServerConnector conn;

    public void verbinde(String hostname, String serverPort) throws Exception
    {
        conn = new ServerConnector(hostname, serverPort);
        conn.registriereBeobachter(this);
    }

    // sendet auch steuerbefehle wie /user
    abstract public void nachrichtSenden(String s);

    abstract public void handshake();

    public abstract void legeNutzernamenFest(String userName);
    
    abstract void verbindungBeenden();

}
