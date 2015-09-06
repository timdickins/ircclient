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
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

public class StringEnter implements MouseListener, KeyListener, Runnable {

    private JFrame frame;
    private JTextPane textArea;
    private JButton submitButton;
    private GUI gui;

    public StringEnter(GUI gui) {
        this.gui = gui;
    }

    private void startGUI(Container container) {
        textArea = new JTextPane();
        Dimension textDim = new Dimension(123, 23);
        textArea.setPreferredSize(textDim);
        submitButton = new JButton("Connect");

        JLabel label = new JLabel("Text:");

        JPanel panel = new JPanel();

        panel.add(label);
        panel.add(textArea);

        textArea.addKeyListener(this);

        JPanel buttonPanel = new JPanel();

        submitButton.addMouseListener(this);

        buttonPanel.add(submitButton);

        container.add(panel);
        container.add(buttonPanel);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        String text = textArea.getText();
        gui.addCard(text);
        gui.joinChannel(text);
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
            String text = textArea.getText();
            gui.addCard(text);
            gui.joinChannel(text);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void run() {
        frame = new JFrame("Enter Text");
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        startGUI(frame.getContentPane());

        frame.pack();
        frame.setVisible(true);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize(200, 150);
        frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);
    }
}
