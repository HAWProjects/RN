package eu.rn.praktikum2.protokolle;

public class MyChatProtokoll extends ChatProtokoll
{

    public MyChatProtokoll()
    {
        super();
        // TODO Auto-generated constructor stub
    }

    @Override
    public void reagiereAufTexteingabe()
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void reagiereAufVerbinden()
    {
        // TODO Auto-generated method stub

    }

    @Override
    public
    void nachrichtSenden(String s)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public
    void handshake(Object[] params)
    {
        conn.writeToServer("HELO");
        if (conn.readFromServer().equals("HELO"))
        {
            conn.writeToServer("USER " + params[0]);
        }

    }

}
