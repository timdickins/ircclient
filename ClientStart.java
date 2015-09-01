package ircclient;

public class ClientStart {
    
    public static void main(String args[]) {
        (new Thread(new Client())).start();
    }
}
