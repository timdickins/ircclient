package ircclient;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;


public class GUI implements MouseListener, ActionListener {
    
    JFrame GUIFrame;
    JTabbedPane tabPane;
    ArrayList paneArray;
    ArrayList paneNames;
    int tabCount = 0;
    ClientGUI clientGUI;
    
    GUI(ArrayList paneArray, ArrayList paneNames, ClientGUI clientGUI) {
        this.clientGUI = clientGUI;
        this.paneArray = paneArray;
        this.paneNames = paneNames;
        
        GUIFrame = new JFrame("IRC client");
        GUIFrame.setJMenuBar(createMenu());
        GUIFrame.setLayout(new BoxLayout(GUIFrame.getContentPane(), BoxLayout.Y_AXIS));
        GUIFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        startGUI(GUIFrame.getContentPane());
        
        GUIFrame.setSize(800, 600);
        GUIFrame.setVisible(true);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        GUIFrame.setLocation(dim.width / 2 - GUIFrame.getSize().width / 2, dim.height / 2 - GUIFrame.getSize().height / 2);
        addCard("Main");
    }
    
    private JMenuBar createMenu() {
        JMenuBar menuBar;
        JMenu file;
        JMenu tools;
        JMenuItem login;
        JMenuItem join;
        JMenuItem leave;
        JMenuItem exit;
        JMenuItem settings;
        
        menuBar = new JMenuBar();
        
        file = new JMenu("File");
        
        login = new JMenuItem("Login");
        login.setName("Login");
        login.addActionListener(this);
        file.add(login);
        
        file.addSeparator();
        
        join = new JMenuItem("Join Channel");
        leave = new JMenuItem("Leave Channel");
        
        file.add(join);
        file.add(leave);
        
        file.addSeparator();
        
        exit = new JMenuItem("Exit");
        file.add(exit);
        
        tools = new JMenu("Tools");
        
        settings = new JMenuItem("Settings");
        tools.add(settings);
        
        menuBar.add(file);
        menuBar.add(tools);
        
        return menuBar;
    }
    
    public void addLine(String channel, String message, String user) {
        Card card;
        String currentChannel;
        int paneLength = paneArray.size();
        int counter = 0;
        
        System.err.println("REQUESTED CHANNEL"+channel);
        
        while (counter < paneLength) {
            card = (Card) paneArray.get(counter);
            currentChannel = (String) paneNames.get(counter);
            
            System.err.println("foundchannel"+counter+" "+currentChannel);
            
            if (currentChannel.contains(channel)) {
                counter = paneLength++;
                card.addLineUser(message+"\n",user);
                System.err.println("SUCCESS");
            }
            counter++;
        }
    }
    
    public void addLineServer(String channel, String message) {
        Card card;
        String currentChannel;
        int paneLength = paneArray.size();
        int counter = 0;
        
        System.err.println("REQUESTED CHANNEL"+channel);
        
        while (counter < paneLength) {
            card = (Card) paneArray.get(counter);
            currentChannel = (String) paneNames.get(counter);
            
            System.err.println("foundchannel"+counter+" "+currentChannel);
            
            if (currentChannel.contains(channel)) {
                counter = paneLength++;
                card.addLine(message+"\n");
                System.err.println("SUCCESS");
            }
            counter++;
        }
    }
    
    private void startGUI(Container contentPane) {
        tabPane = new JTabbedPane();
        contentPane.add(tabPane, BorderLayout.CENTER);
    }
    
    public void addCard(String channel) {
        Card newCard = new Card(tabPane, channel, clientGUI, paneArray, paneNames);
        paneArray.add(newCard);
        paneNames.add(channel);
        tabCount++;
    }
    
    public void removeCard(String channel) {
        //TO BE MADE
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("HELLO");
        Object eventComponent = e.getSource();
        if (eventComponent.toString().contains("Login")) {
            clientGUI.login();
        }
    }

}
