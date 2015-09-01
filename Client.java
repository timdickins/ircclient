package ircclient;

import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client implements Runnable {

    Socket socket;
    BufferedWriter writer;
    BufferedReader rdr;
    String nick;
    int loginFlag;
    Client client;
    String server;

    @Override
    public void run() {
        this.client = this;
        loginFlag = 0;

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                Login newLogin = new Login(client);
            }
        });
        while (loginFlag == 0) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        try {
            socket = new Socket(server, 6667);
            writer = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));
            rdr = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        } catch (IOException ex) {
            System.err.println("Error with host.");
        }
        
        ToServer toServer = new ToServer(writer);
        toServer.start();

        ClientGUI clientGUI = new ClientGUI(toServer);
        
        FromServer fromServer = new FromServer(rdr, nick, clientGUI);
        fromServer.start();

        

        fromServer.setWriter(toServer);

        
    }

    public void setLogin(String server, String nick) {
        loginFlag = 1;
        this.server = server;
        this.nick = nick;
    }

}
