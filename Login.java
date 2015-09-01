package ircclient;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.KeyboardFocusManager;
import static java.awt.TextArea.SCROLLBARS_NONE;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;

public class Login implements MouseListener, KeyListener {

    private JFrame LoginFrame;
    private JTextPane nickArea;
    private JTextPane serverArea;
    private JButton submitButton;
    private Client client;

    public Login(Client client) {
        this.client = client;
        LoginFrame = new JFrame("Login");
        LoginFrame.setLayout(new BoxLayout(LoginFrame.getContentPane(), BoxLayout.Y_AXIS));
        LoginFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);

        startGUI(LoginFrame.getContentPane());

        LoginFrame.pack();
        LoginFrame.setVisible(true);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        LoginFrame.setSize(200, 150);
        LoginFrame.setLocation(dim.width / 2 - LoginFrame.getSize().width / 2, dim.height / 2 - LoginFrame.getSize().height / 2);
    }

    private void startGUI(Container container) {
        nickArea = new JTextPane();
        Dimension nickDim = new Dimension(123, 23);
        nickArea.setPreferredSize(nickDim);
        serverArea = new JTextPane();
        Dimension serverDim = new Dimension(144, 23);
        serverArea.setPreferredSize(serverDim);
        submitButton = new JButton("Connect");

        JLabel nickLabel = new JLabel("Nickname");
        JLabel serverLabel = new JLabel("Server");

        JPanel nickPanel = new JPanel();
        JPanel serverPanel = new JPanel();

        nickPanel.add(nickLabel);
        nickPanel.add(nickArea);
        serverPanel.add(serverLabel);
        serverPanel.add(serverArea);

        nickArea.addKeyListener(this);
        serverArea.addKeyListener(this);

        JPanel buttonPanel = new JPanel();

        submitButton.addMouseListener(this);

        buttonPanel.add(submitButton);

        container.add(nickPanel);
        container.add(serverPanel);
        container.add(buttonPanel);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        String nick = nickArea.getText();
        String server = serverArea.getText();

        client.setLogin(server, nick);
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
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_TAB) {
            e.consume();
            KeyboardFocusManager.getCurrentKeyboardFocusManager().focusNextComponent();
        } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            e.consume();
            String nick = nickArea.getText();
            String server = serverArea.getText();

            client.setLogin(server, nick);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
