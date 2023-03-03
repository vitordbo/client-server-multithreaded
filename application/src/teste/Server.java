package teste;

import java.net.*;

public class Server {
    private static final int PORT = 9876;
    private static final int BUFFER_SIZE = 1024;

    public static void main(String[] args) throws Exception {
        DatagramSocket serverSocket = new DatagramSocket(PORT);

        System.out.println("Server started...");
        System.out.println("Listening on port " + PORT);

        byte[] receiveBuffer = new byte[BUFFER_SIZE];
        while (true) {
            DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
            serverSocket.receive(receivePacket);

            InetAddress clientAddress = receivePacket.getAddress();
            int clientPort = receivePacket.getPort();
            byte[] data = receivePacket.getData();

            String message = new String(data, 0, receivePacket.getLength());
            System.out.println("Received message from " + clientAddress + ":" + clientPort + " - " + message);

            // TODO: Forward message to appropriate destination process according to ring topology

            // Send response to client
            byte[] responseBuffer = "Message received".getBytes();
            DatagramPacket responsePacket = new DatagramPacket(responseBuffer, responseBuffer.length, clientAddress, clientPort);
            serverSocket.send(responsePacket);
        }
    }
}
