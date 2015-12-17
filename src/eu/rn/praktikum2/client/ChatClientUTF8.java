/**
 * 
 */
package eu.rn.praktikum2.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

/**
 * Modelliert einen Chat-Client mit dem der Benutzer sich mit einem Chat-Server
 * verbinden kann.
 * 
 * @author abt434
 *
 */
public class ChatClientUTF8 extends Thread implements Observer
{

    private String hostname;
    private String serverPort;
    private String userName;
    private Socket socket;
    private boolean connected;
    // private MyFileWriter myWriter;

    private DataOutputStream outToServer;
    private BufferedReader inFromServer;

    private ChatClientSwingGUI gui;

    /**
     * @param hostname
     *            der Hostname
     * @param serverPort
     *            Port auf dem der Server Anfragen annimmt
     */
    public ChatClientUTF8(String hostname, String serverPort, String username)
    {
        this.hostname = hostname;
        this.serverPort = serverPort;
        connected = false;
        this.userName = username;
        gui = new ChatClientSwingGUI();
        gui.getUsername().setText(username);
        gui.getServerIP().setText(hostname);
        gui.getServerPort().setText(serverPort);
        gui.getVerbinden().addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                reagiereAufVerbinden();
            }
        });

        gui.getUserInputField().addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                reagiereAufTexteingabe();
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
        if(connected)return;
        hostname = gui.getServerIP().getText();
        serverPort = gui.getServerPort().getText();
        userName = gui.getUsername().getText();

        try
        {
            socket = new Socket(hostname, Integer.parseInt(serverPort));
            outToServer = new DataOutputStream(socket.getOutputStream());
            inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            // myWriter = new MyFileWriter("ChatLogFile.txt", "files/");

            // handshake
            writeToServer("HELO");
            if (readFromServer().equals("HELO"))
            {
                writeToServer("USER " + userName);
                connected = true;
            }

            if (connected)
            {
                Runnable vomServerLeser = new MyThread();
                new Thread(vomServerLeser).start();
                gui.getVerbinden().setEnabled(false);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    /**
     * Sendet Text an den Server
     * 
     * @param input
     *            Text, der an den Server gesendet werden soll
     * @throws IOException
     */
    private void writeToServer(String input)
    {
        try
        {
            outToServer.write((input + "\r\n").getBytes("UTF-8"));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        // myWriter.writeLine("Client: " + input);
    }

    public String getUsername()
    {
        return userName;
    }

    @Override
    public void reagiereAufTexteingabe()
    {
        String input = gui.getUserInputField().getText();
        if(!input.startsWith("/"))
        {
            input = "MSG "+input;
        }
        writeToServer(input);
        gui.getUserInputField().setText("");
    }

    @Override
    public void reagiereAufVerbinden()
    {
        startConnection();
    }

    public class MyThread implements Runnable
    {

        public void run()
        {
            String messageFromServer;
            while (connected)
            {
                try
                {
                    messageFromServer = readFromServer();
                    gui.getTextArea().append(messageFromServer+"\r\n");
                    System.out.println(messageFromServer);
                    if (messageFromServer.equals("Verbindung beendet"))
                    {
                        socket.close();
                        gui.getVerbinden().setEnabled(true);
                    }
                    gui.getTextArea().setCaretPosition(gui.getTextArea().getDocument().getLength());
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }

    }

    /**
     * 
     * Liest eine Zeile vom Server
     * 
     * @throws IOException
     */
    private String readFromServer() throws IOException
    {
        // myWriter.writeLine("Server: " + result);
        if(socket.isClosed()){
            connected = false;
            return "";
        }
        else
        {
        return new String(inFromServer.readLine().getBytes(),"UTF-8");
        }
    }
    
    public static void main(String[] args)
    {
        new ChatClientUTF8("LAB21", "45619", "Robert");
    }

}
