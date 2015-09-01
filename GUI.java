package ircclient;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class GUI implements MouseListener {
    
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
        GUIFrame.setLayout(new BoxLayout(GUIFrame.getContentPane(), BoxLayout.Y_AXIS));
        GUIFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        startGUI(GUIFrame.getContentPane());
        
        GUIFrame.setSize(800, 600);
        GUIFrame.setVisible(true);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        //GUIFrame.setSize(200, 150);
        GUIFrame.setLocation(dim.width / 2 - GUIFrame.getSize().width / 2, dim.height / 2 - GUIFrame.getSize().height / 2);
        addCard("Main");
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

}
