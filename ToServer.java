package ircclient;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ToServer extends Thread {
    
    BufferedWriter writer;
    
    public void login(String nick) {
        try {
            writer.write("NICK "+nick+"\r\n");
            writer.write("USER "+nick+" 0 "+":\r\n");
        } catch (IOException ex) {
            
        }
    }
    
    public ToServer(BufferedWriter newWriter) {
        this.writer = newWriter;
    }
    
    @Override
    public void run() {
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        
        String newLine = null;
        try {
            while (true) {
                newLine = stdIn.readLine();
                sendMessage(newLine);
            }
        } catch (IOException ex) {
            System.err.println("Error with writer.");
        }
    }
    
    public void sendMessage(String newLine) throws IOException {
        writer.write(newLine+"\r\n");
        writer.flush();
    }
}
