package Quest1;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class Client {
    private static final int PORT = 12345;
    private static final int BUFFER_SIZE = 1024;

    public static void main(String[] args) {
        try {
            DatagramSocket clientSocket = new DatagramSocket();
            InetAddress serverAddress = InetAddress.getByName("localhost"); // 192.168.1.15 ou localhost

            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.print("Enter message: ");
                String message = scanner.nextLine();

                DatagramPacket sendPacket = new DatagramPacket(message.getBytes(), message.length(), serverAddress, PORT);
                clientSocket.send(sendPacket);

                byte[] buffer = new byte[BUFFER_SIZE];
                DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);
                clientSocket.receive(receivePacket);

                String response = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println("Received message from " + response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
