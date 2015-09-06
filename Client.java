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
    Client client;
    String server;
    ClientGUI clientGUI;
    int connected = 0;

    @Override
    public void run() {
        this.client = this;
        clientGUI = new ClientGUI(client);
    }

    public void login() {
        if (connected == 0) {
            (new Thread(new Login(client))).start();
        }
    }

    public void setLogin(String server, String nick) {
        connected = 1;
        this.server = server;
        this.nick = nick;
        
        try {
            socket = new Socket(server, 6667);
            writer = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));
            rdr = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        } catch (IOException ex) {
            System.err.println("Error with host.");
        }

        ToServer toServer = new ToServer(writer);
        toServer.start();

        clientGUI.setSockets(toServer);

        FromServer fromServer = new FromServer(rdr, nick, clientGUI);
        fromServer.start();

        fromServer.setWriter(toServer);
    }
    
    public void disconnected() {
        connected = 0;
    }

}
