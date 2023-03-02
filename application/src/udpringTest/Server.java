package udpringTest;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class Server {
    private static final int PORT = 12345;
    private static final int BUFFER_SIZE = 1024;

    public static void main(String[] args) throws IOException {
        DatagramSocket serverSocket = new DatagramSocket(PORT);
        System.out.println("Server started...");

        byte[] receiveData = new byte[BUFFER_SIZE];
        byte[] sendData = new byte[BUFFER_SIZE];

        while (true) {
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            serverSocket.receive(receivePacket);
            String message = new String(receivePacket.getData(), 0, receivePacket.getLength());

            System.out.println("Received message: " + message);

            // Evaluate message content
            String[] parts = message.split("-");
            int processId = Integer.parseInt(parts[0]);
            int value = Integer.parseInt(parts[1]);

            if (value % 2 == 0) {
                // Multiply the number by 2 if the process id is even
                value *= 2;
            } else if (value % 2 != 0 && processId % 2 != 0) {
                // Only pass the number if the process id is odd and the value is odd
            } else {
                // Invalid input
                System.out.println("Invalid input: " + message);
                continue;
            }

            // Create a new message with the process id and the new value
            int nextProcessId = getNextProcessId(processId);
            String newMessage = nextProcessId + "-" + value;

            // Send the new message to the next process in the ring
            sendData = newMessage.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, receivePacket.getAddress(), receivePacket.getPort());
            serverSocket.send(sendPacket);
        }
    }

    private static int getNextProcessId(int currentProcessId) {
        switch (currentProcessId) {
            case 1:
                return 2;
            case 2:
                return 3;
            case 3:
                return 4;
            case 4:
                return 1;
            default:
                return -1;
        }
    }
}
