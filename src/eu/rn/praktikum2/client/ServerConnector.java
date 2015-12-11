package eu.rn.praktikum2.client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

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
        Runnable reader = new MyThread();
        new Thread(reader).start();
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
            input = Base64.encode(input.getBytes());
            outToServer.writeBytes(input + "\r\n");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        // myWriter.writeLine("Client: " + input);
    }
    
    public class MyThread implements Runnable
    {
        //FIXME: parameter nicht benutzt
        public MyThread()
        {
            // store parameter for later user
        }

        public void run()
        {
            String messageFromServer;
            while (true)
            {
                try
                {
                    messageFromServer = readFromServer();
                    gui.getTextArea().append(messageFromServer+"\r\n");
                    System.out.println(messageFromServer);
                    if (messageFromServer.equals("Verbindung beendet"))
                    {
                        socket.close();
                    }
                    gui.getTextArea().setCaretPosition(gui.getTextArea().getDocument().getLength());
                }
                catch (IOException e)
                {
                    e.printStackTrace();
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
            return new String(Base64.decode(inFromServer.readLine()));
        }
    }
    
}
