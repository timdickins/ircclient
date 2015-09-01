package ircclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.BufferedWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FromServer extends Thread {
    
    BufferedReader rdr;
    ToServer toServer;
    String nick;
    ClientGUI clientGUI;
    
    public FromServer(BufferedReader rdr, String nick, ClientGUI clientGUI){
        this.rdr = rdr;
        this.nick = nick;
        this.clientGUI = clientGUI;
    }

    @Override
    public void run() {       
        String newLine = null;
        try {
            while (true) {
                newLine = rdr.readLine();
                if (newLine.contains("PING")) {
                    String pingCode = newLine.substring(6);
                    toServer.sendMessage("PONG :"+pingCode);
                    continue;
                } else if (newLine.contains("No ident response")) {
                    toServer.sendMessage("NICK " + nick);
                    toServer.sendMessage("USER " + nick + " 0 * :");
                }
                System.out.println(newLine);
                printLine(newLine);
            }
        } catch (IOException ex) {
            
        }
    }
    
    public void setWriter(ToServer toServer) {
        this.toServer = toServer;
    }
    
    public void printLine(String message){
        clientGUI.addLine(message);
    }
}
