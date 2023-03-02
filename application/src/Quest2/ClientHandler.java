package Quest2;

import java.io.*;
import java.net.*;

public class ClientHandler implements Runnable {

    private Socket clientSocket;
    private BufferedReader reader;
    private PrintWriter writer;
    private String username;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    public void run() {
        try {
            reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            writer = new PrintWriter(clientSocket.getOutputStream(), true);

        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void sendMessage(String message) {
        writer.println(message);
    }

    public String getUsername() {
        return username;
    }
}

