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
        conn.writeToServer(s);
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
    public void reagiereAufNachricht(String s)
    {
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
        else if(/*s.startsWith("MSG")*/true)
        {
            leiteNachrichtWeiter(s);
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
            conn.getSocket().close();
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
