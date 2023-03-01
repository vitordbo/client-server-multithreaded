package Quest2;

import java.io.*;
import java.net.*;
import java.util.*;

public class Server {

    private static final int PORT = 5000;
    private static ArrayList<ClientHandler> clients = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("Server started on port " + PORT);

        while (true) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("New client connected: " + clientSocket);

            ClientHandler clientHandler = new ClientHandler(clientSocket);
            clients.add(clientHandler);

            Thread thread = new Thread(clientHandler);
            thread.start();
        }
    }

    public static void broadcast(String message, ClientHandler sender) {
        for (ClientHandler client : clients) {
            if (client != sender) {
                client.sendMessage(message);
            }
        }
    }

    public static void unicast(String message, String receiver) {
        for (ClientHandler client : clients) {
            if (client.getUsername().equals(receiver)) {
                client.sendMessage(message);
                break;
            }
        }
    }
}

