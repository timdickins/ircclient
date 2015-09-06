package ircclient;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class Card implements MouseListener, KeyListener {

    JTabbedPane tabPane;
    int tabIndex;
    JTextPane serverText;
    JTextPane userBar;
    JPanel card;
    JPanel newCard;
    ToServer toServer;
    ClientGUI clientGUI;
    String channel;
    StyledDocument doc;
    SimpleAttributeSet bold;
    SimpleAttributeSet userAttributeSet;
    SimpleAttributeSet plain;
    ArrayList paneArray;
    ArrayList paneNames;

    public Card(JTabbedPane tabPane, String channel, ClientGUI clientGUI, ArrayList paneArray, ArrayList paneNames) {
        this.paneArray = paneArray;
        this.paneNames = paneNames;
        this.channel = channel;
        this.clientGUI = clientGUI;
        this.toServer = toServer;
        newCard = new JPanel();
        this.tabPane = tabPane;
        tabPane.addTab(channel, newCard);
        tabIndex = tabPane.getTabCount()-1;
        serverText = new JTextPane();
        userBar = new JTextPane();

        JButton sendButton = new JButton("Send");
        sendButton.setName("Send");
        
        JButton leaveButton = new JButton("Leave Channel");
        leaveButton.setName("Leave");
        leaveButton.addMouseListener(this);

        serverText.setEditable(false);
        Font font = new Font("Courier New", Font.PLAIN, 12);
        serverText.setFont(font);
        serverText.setText("");
        doc = serverText.getStyledDocument();

        bold = new SimpleAttributeSet();
        StyleConstants.setBold(bold, true);
        StyleConstants.setForeground(bold, Color.BLUE);

        userAttributeSet = new SimpleAttributeSet();
        StyleConstants.setBold(userAttributeSet, true);
        StyleConstants.setForeground(userAttributeSet, Color.red);

        plain = new SimpleAttributeSet();

        userBar.addKeyListener(this);

        JScrollPane serverScroll = new JScrollPane(serverText);
        serverScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        Dimension dim = new Dimension(780, 500);
        serverScroll.setPreferredSize(dim);
        
        newCard.setLayout(new BorderLayout());
        
        JPanel panel = new JPanel(new BorderLayout());

        newCard.add(serverScroll, BorderLayout.CENTER);
        panel.add(leaveButton, BorderLayout.WEST);
        panel.add(userBar, BorderLayout.CENTER);
        panel.add(sendButton, BorderLayout.EAST);
        
        newCard.add(panel, BorderLayout.PAGE_END);
        sendButton.addMouseListener(this);

        //this.tabPane = tabPane;
    }

    private int commandCheck(String input) {
        Pattern pattern = Pattern.compile("^/");

        Matcher matcher = pattern.matcher(input);

        String string = "UNKNOWN";
        while (matcher.find()) {
            string = matcher.group();
            if (string.contains("/")) {
                clientGUI.sendToServer(input);
                return 1;
            }
        }
        return 0;
    }

    private void processSend(String message) {
        if (commandCheck(message) == 0) {
            if (channel.equals("Main")) {
                clientGUI.sendToServer(message);
            } else {
                message = "PRIVMSG " + channel + " :" + message;
                clientGUI.sendToServer(message);
                System.out.println(message);
            }
        }
    }

    public void addLine(String newLine) {
        try {
            doc.insertString(doc.getLength(), newLine, plain);
        } catch (BadLocationException ex) {
            Logger.getLogger(Card.class.getName()).log(Level.SEVERE, null, ex);
        }
        int length = serverText.getDocument().getLength();
        serverText.setCaretPosition(length);
    }

    public void addLineUser(String newLine, String user) {
        try {
            doc.insertString(doc.getLength(), "<" + user + ">", bold);
            doc.insertString(doc.getLength(), newLine, plain);
        } catch (BadLocationException ex) {
            Logger.getLogger(Card.class.getName()).log(Level.SEVERE, null, ex);
        }
        int length = serverText.getDocument().getLength();
        serverText.setCaretPosition(length);
    }

    public String getMessage() {
        String retString = userBar.getText();
        try {
            doc.insertString(doc.getLength(), "<" + "You" + "> ", userAttributeSet);
            doc.insertString(doc.getLength(), retString + "\n", plain);
        } catch (BadLocationException ex) {
            Logger.getLogger(Card.class.getName()).log(Level.SEVERE, null, ex);
        }
        userBar.setText("");
        return retString;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        Component eventComponent = e.getComponent();
        String componentName = eventComponent.getName();
        if (componentName.contains("Send")) {
            String message = getMessage();
            processSend(message);
        } else if (componentName.contains("Leave")) {
            closeTab();
        }
    }
    
    public void closeTab() {
        if (channel.equals("Main")) {
                
            } else {
                clientGUI.sendToServer("PART "+channel);
                tabPane.removeTabAt(tabIndex);
                paneArray.remove(tabIndex);
                paneNames.remove(tabIndex);
            }
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
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_TAB) {
            e.consume();
            KeyboardFocusManager.getCurrentKeyboardFocusManager().focusNextComponent();
        } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            e.consume();
            String message = getMessage();
            processSend(message);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
