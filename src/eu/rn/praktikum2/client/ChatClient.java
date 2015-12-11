/**
 * 
 */
package eu.rn.praktikum2.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import eu.rn.praktikum2.protokolle.ChatProtokoll;
import eu.rn.praktikum2.protokolle.MyChatProtokoll;

/**
 * Modelliert einen Chat-Client mit dem der Benutzer sich mit einem Chat-Server
 * verbinden kann.
 * 
 * @author abt434
 *
 */
public class ChatClient extends Thread implements Observer
{

    private String hostname;
    private String serverPort;
    private String userName;

    private ChatClientSwingGUI gui;
    private ChatProtokoll protokoll;

    /**
     * @param hostname
     *            der Hostname
     * @param serverPort
     *            Port auf dem der Server Anfragen annimmt
     */
    public ChatClient(String hostname, String serverPort, String username, ChatProtokoll protokoll)
    {
        this.hostname = hostname;
        this.serverPort = serverPort;
        this.userName = username;
        this.protokoll = protokoll;
        protokoll.registriereBeobachter(this);
        gui = new ChatClientSwingGUI();
        gui.getUsername().setText(username);
        gui.getServerIP().setText(hostname);
        gui.getServerPort().setText(serverPort);
        gui.getVerbinden().addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                startConnection();
            }
        });

        gui.getUserInputField().addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                protokoll.nachrichtSenden(gui.getUserInputField().getText());
                gui.getUserInputField().setText("");
            }
        });

    }

    /**
     * Startet eine Verbindung zu einem Server
     * 
     * @throws IOException
     * 
     */
    private void startConnection()
    {
        hostname = gui.getServerIP().getText();
        serverPort = gui.getServerPort().getText();
        userName = gui.getUsername().getText();
        try
        {
            protokoll.verbinde(hostname, serverPort);
            protokoll.handshake();
            protokoll.legeNutzernamenFest(userName);
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void reagiereAufNachricht(String s)
    {
        gui.getTextArea().append(s+"\r\n");
        gui.getTextArea().setCaretPosition(gui.getTextArea().getDocument().getLength());
    }

    public static void main(String[] args)
    {
        new ChatClient("127.0.0.1", "45619", "Robert", new MyChatProtokoll());
    }

}
