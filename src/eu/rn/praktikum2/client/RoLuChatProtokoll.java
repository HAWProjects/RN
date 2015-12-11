package eu.rn.praktikum2.client;

public class RoLuChatProtokoll implements ChatProtokoll
{

    @Override
    public void verbinden()
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void nachrichtVerarbeiten(String s)
    {
        if (messageFromServer.equals("Verbindung beendet"))
        {
            socket.close();
        }
    }
    
    @Override
    public void verbindungBeenden()
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void datenVonServerVerarbeiten()
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void nachrichtSenden(String text)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void nachrichtEmpfangen()
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void userlisteAusgeben()
    {
        // TODO Auto-generated method stub

    }

}
