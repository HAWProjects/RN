package eu.rn.praktikum2.protokolle;

import java.io.IOException;


public class MyChatProtokoll extends ChatProtokoll
{

    public MyChatProtokoll()
    {
        super();
        // TODO Auto-generated constructor stub
    }

    @Override
    public
    void nachrichtSenden(String s)
    {
        conn.writeToServer("MSG " + s);
    }

    @Override
    public
    void handshake()
    {
        conn.writeToServer("HELO");
        try
        {
            if (!conn.readFromServer().equals("HELO"))
            {
                conn.getSocket().close();
            }
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @Override
    public void reagiereAufNachricht(String text)
    {
        String s = text;
        if(s.equals("Verbindung beendet"))
        {
            try
            {
                conn.getSocket().close();
            }
            catch (IOException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        else /*if(s.startsWith("MSG "))*/
        {
            leiteNachrichtWeiter(s.replaceFirst("MSG ", ""));
        }
    }

    @Override
    public void legeNutzernamenFest(String username)
    {
        conn.writeToServer("USER " + username);
    }

    @Override
    public void verbindungBeenden()
    {
        conn.writeToServer("QUIT");
        try
        {
            if(conn.readFromServer().equals("Verbindung beendet"))
            {
                conn.beendeVerbindung();
            }
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
