package ircclient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClientGUI {

    GUI newGUI;
    ToServer toServer;
    ArrayList paneArray;
    ArrayList paneNames;

    public ClientGUI(ToServer toServer) {
        this.paneArray = new ArrayList();
        this.paneNames = new ArrayList();
        this.toServer = toServer;

        GUI newGUI = new GUI(paneArray, paneNames, this);
        this.newGUI = newGUI;
    }

    public void addLine(String message) {

        Pattern pattern = Pattern.compile(":(.*?):");
        String[] messageArray = pattern.split(message);

        if (message.contains("PRIVMSG")) {
            privmsgProcess(message);
        } else {
            String sendMessage = Arrays.toString(messageArray);
            if (sendMessage.contains("AUTH")) {
                sendMessage = sendMessage.substring(1, sendMessage.length() - 1);

                sendMainLine(sendMessage);
            } else {
                channelCheck(message);

                sendMessage = sendMessage.substring(3, sendMessage.length() - 1);

                sendMainLine(sendMessage);
            }
        }
    }

    public void privmsgProcess(String message) {
        Pattern pattern = Pattern.compile(":(.*?):");
        Pattern userPattern = Pattern.compile(":(.*)!");
        Pattern channelPattern = Pattern.compile("(#(\\S)+)");

        String[] privMessageArray = pattern.split(message);
        Matcher userMatcher = userPattern.matcher(message);
        Matcher channelMatcher = channelPattern.matcher(message);

        String sendMessage = Arrays.toString(privMessageArray);
        sendMessage = sendMessage.substring(3, sendMessage.length() - 1);

        String userString = "UNKNOWN";
        while (userMatcher.find()) {
            userString = userMatcher.group();
            userString = userString.substring(1, userString.length() - 1);
        }

        String channelString = "UNKNOWN";
        while (channelMatcher.find()) {
            channelString = channelMatcher.group();
            channelString = channelString.substring(0, channelString.length());
        }

        System.err.println("ChannelSTring" + channelString);

        sendLine(sendMessage, channelString, userString);
    }

    public void channelCheck(String message) {
        Pattern qPattern = Pattern.compile(":(.*)(#(\\S)+)(.*):");

        Matcher userMatcher = qPattern.matcher(message);

        String patternString = "UNKNOWN";
        System.err.println(patternString);
        while (userMatcher.find()) {
            patternString = userMatcher.group();
            System.err.println(patternString);
            if (patternString.contains("#")) {
                String channelString = channelReturn(patternString);
                String sendMessage = messageReturn(message);

                sendLineServer(sendMessage, channelString);
            }
        }
    }

    public String messageReturn(String message) {
        Pattern pattern = Pattern.compile("^(:(.*?):)");

        String[] privMessageArray = pattern.split(message);

        String sendMessage = Arrays.toString(privMessageArray);

        sendMessage = sendMessage.substring(3, sendMessage.length() - 1);

        return sendMessage;
    }

    public String channelReturn(String message) {
        Pattern channelPattern = Pattern.compile("(#(\\S)+)");

        Matcher channelMatcher = channelPattern.matcher(message);

        String channelString = "UNKNOWN";
        while (channelMatcher.find()) {
            channelString = channelMatcher.group();
            channelString = channelString.substring(0, channelString.length());
        }

        return channelString;
    }

    public void sendMainLine(String message) {
        Card main = (Card) paneArray.get(0);
        main.addLine(message + "\n");
    }

    public void sendLine(String message, String channel, String user) {
        newGUI.addLine(channel, message, user);

    }

    public void sendLineServer(String message, String channel) {
        newGUI.addLineServer(channel, message);

    }

    public void sendToServer(String message) {
        if (message.contains("PRIVMSG")) {
            try {
                toServer.sendMessage(message);
            } catch (IOException ex) {
                Logger.getLogger(ClientGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (message.substring(0, 1).contains("/")) {
            if (message.substring(0, 9).contains("/join ")) {
                String sendString = message.substring(5, message.length());
                try {
                    toServer.sendMessage("JOIN " + sendString);
                } catch (IOException ex) {
                    Logger.getLogger(ClientGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
                newGUI.addCard(sendString);
            } else if (message.substring(0, 9).contains("/kick ")) {
                String sendString = message.substring(5, message.length());
                try {
                    toServer.sendMessage("KICK " + sendString);
                } catch (IOException ex) {
                    Logger.getLogger(ClientGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
                newGUI.addCard(sendString);
            }
        } else {
            try {
                toServer.sendMessage(message);
            } catch (IOException ex) {
                Logger.getLogger(ClientGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
