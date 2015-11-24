package eu.rn.praktikum2;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ArbeitsSocket extends Thread
{

    private int threadNumber;
    private Socket socket;
    private ChatServer server;
    private BufferedReader input;
    private DataOutputStream output;
    private boolean working = false;
    private MyFileWriter writer;
    private String username;

    public ArbeitsSocket(int indexThread, Socket arbeitsSocketConn, ChatServer chatServer)
    {
        this.threadNumber = indexThread;
        this.socket = arbeitsSocketConn;
        this.server = chatServer;

    }

    public void run()
    {
        String currentInput;
        System.out.println("TCP Worker Thread " + threadNumber + " is running until QUIT is received!");
        try
        {
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new DataOutputStream(socket.getOutputStream());
            writer = new MyFileWriter("ChatLogFile.txt", "files/");

            if (readFromClient().equals("HELO"))
            {
                writeToClient("HELO");
                username = readFromClient().replace("USER ", "");
                working = true;
            }

            while (working)
            {
                currentInput = readFromClient();

                writeToClient(currentInput);

                if (currentInput.toUpperCase().startsWith("QUIT"))
                {
                    writeToClient("Verbindung beendet");
                    working = false;
                }

            }

        }
        catch (Exception e)
        {

        }
        finally
        {
            System.out.println("TCP Worker Thread " + threadNumber + " stopped!");
            server.getSemaphore().release();
        }
    }

    private String readFromClient() throws IOException
    {
        String inFromClient = input.readLine();
        System.out.println("TCP Worker Thread " + threadNumber + " detected job: " + inFromClient);
        return inFromClient;
    }

    public void writeToClient(String value) throws IOException
    {
        output.writeBytes(value + "\r\n");

        // writer.writeLine(""+ value);
    }

}
