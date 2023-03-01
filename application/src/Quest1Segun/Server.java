package Quest1Segun;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Server {
    private static final int PORT = 12345;
    private static final int BUFFER_SIZE = 1024;
    private static final String[] CLIENTS = {"P1", "P2", "P3", "P4"};
    private static int index = 0;

    public static void main(String[] args) {
        try {
            DatagramSocket serverSocket = new DatagramSocket(PORT);
            System.out.println("Server started at port " + PORT);

            byte[] buffer = new byte[BUFFER_SIZE];

            while (true) {
                
                DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);
                serverSocket.receive(receivePacket);

                String message = new String(receivePacket.getData(), 0, receivePacket.getLength());

                // ver oq colocar aqui => So mensagem??
                System.out.println("Received message, menssage = " + message);

                index++;
                if (index >= CLIENTS.length) {
                    index = 0;
                }

                InetAddress address = receivePacket.getAddress();
                int port = receivePacket.getPort();

                String response = CLIENTS[index];
                DatagramPacket sendPacket = new DatagramPacket(response.getBytes(), response.length(), address, port);
                serverSocket.send(sendPacket);

                System.out.println("Sent message to " + response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
