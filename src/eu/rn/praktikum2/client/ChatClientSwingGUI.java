package eu.rn.praktikum2.client;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class ChatClientSwingGUI
{
  private JFrame frame;
  private JTextField username;
  private JTextField serverIP;
  private JTextField serverPort;
  private JButton verbinden;
  private JTextArea textArea;
  private JPanel einstellungenPanel;
  private JPanel chatPanel;
  private JTextField userInputField;
    
    
    public JTextField getUserInputField()
{
    return userInputField;
}


    public ChatClientSwingGUI()
    {
        frame = new JFrame();
        frame.setTitle("Chat-Client");
        frame.setSize(400, 8000);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);

        // We create a TextArea object
        textArea = new JTextArea(5, 30);
        // We put the TextArea object in a Scrollable Pane
        JScrollPane scrollPane = new JScrollPane(textArea);

        // In order to ensure the scroll Pane object appears in your window,
        // set a preferred size to it!
        scrollPane.setPreferredSize(new Dimension(380, 100));

        // Lines will be wrapped if they are too long to fit within the
        // allocated width
        textArea.setLineWrap(true);

        // Lines will be wrapped at word boundaries (whitespace) if they are
        // too long to fit within the allocated width
        textArea.setWrapStyleWord(true);

        // Assuming this is the chat client's window where we read text sent out
        // and received, we don't want our Text Area to be editable!
        textArea.setEditable(false);

        // We also want a vertical scroll bar on our pane, as text is added to
        // it
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        // Now let's just add a Text Field for user input, and make sure our
        // text area stays on the last line as subsequent lines are
        // added and auto-scrolls
        userInputField = new JTextField(30);
//        userInputField.addActionListener(new ActionListener()
//        {
//            public void actionPerformed(ActionEvent event)
//            {
//                // We get the text from the textfield
//                String fromUser = userInputField.getText();
//
//                if (fromUser != null)
//                {
//                    // We append the text from the user
//                    textArea.append("Asim: " + fromUser + "\n");
//
//                    // The pane auto-scrolls with each new response added
//                    textArea.setCaretPosition(textArea.getDocument().getLength());
//                    // We reset our text field to "" each time the user presses
//                    // Enter
//                    userInputField.setText("");
//                }
//            }
//        });
        
        
        // Chatpanel
        chatPanel = new JPanel();
        chatPanel.add(scrollPane);
        chatPanel.add(userInputField);
        chatPanel.setLayout(new BoxLayout(chatPanel, BoxLayout.PAGE_AXIS));
        
        // Einstellungenpanel
        JLabel usernameLabel = new JLabel("Nutzername");
        username = new JTextField();
        JLabel serverIPLabel = new JLabel("Server-IP");
        serverIP = new JTextField();
        JLabel serverPortLabel = new JLabel("Server-Port");
        serverPort = new JTextField();
        verbinden = new JButton("Verbinden");
        einstellungenPanel = new JPanel();
        einstellungenPanel.add(usernameLabel);
        einstellungenPanel.add(username);
        einstellungenPanel.add(serverIPLabel);
        einstellungenPanel.add(serverIP);
        einstellungenPanel.add(serverPortLabel);
        einstellungenPanel.add(serverPort);
        einstellungenPanel.add(verbinden);
        einstellungenPanel.setLayout(new BoxLayout(einstellungenPanel, BoxLayout.PAGE_AXIS));

        frame.setLayout(new FlowLayout());
        // adds and centers the text field to the frame
        frame.add(chatPanel, SwingConstants.CENTER);
        // adds and centers the scroll pane to the frame
        frame.add(einstellungenPanel, SwingConstants.CENTER);
        
        frame.pack();
    }


    public JTextField getUsername()
    {
        return username;
    }

    public JTextArea getTextArea()
    {
        return textArea;
    }


    public JTextField getServerIP()
    {
        return serverIP;
    }

    public JTextField getServerPort()
    {
        return serverPort;
    }

    public JButton getVerbinden()
    {
        return verbinden;
    }
}
