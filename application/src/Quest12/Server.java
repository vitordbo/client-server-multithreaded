package Quest12;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;

public class Server {
    private static final int PORT = 5000;
    private static final int BUFFER_SIZE = 1024;
    private static ArrayList<String> messages = new ArrayList<String>();
    private static int expectedSender = 1;

    public static void main(String[] args) {
        try {
            DatagramSocket socket = new DatagramSocket(PORT);
            System.out.println("Server started on port " + PORT);

            while (true) {
                byte[] buffer = new byte[BUFFER_SIZE];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);
                InetAddress clientAddress = packet.getAddress();
                int clientPort = packet.getPort();
                String message = new String(packet.getData(), 0, packet.getLength());

                synchronized(messages) {
                    if (expectedSender == getClientId(message)) {
                        messages.add(message);
                        expectedSender = getNextExpectedSender(expectedSender);
                        System.out.println("Message received: " + message);
                    }
                    else {
                        System.out.println("Discarded message from unexpected sender: " + message);
                    }

                    if (messages.size() == 4) {
                        System.out.println("Messages received:");
                        for (String m : messages) {
                            System.out.println(m);
                        }
                        messages.clear();
                        expectedSender = 1;
                    }
                }

                String response = "ACK " + message;
                byte[] responseData = response.getBytes();
                DatagramPacket responsePacket = new DatagramPacket(responseData, responseData.length, clientAddress, clientPort);
                socket.send(responsePacket);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static int getClientId(String message) {
        String[] parts = message.split(":");
        return Integer.parseInt(parts[0].substring(1));
    }

    private static int getNextExpectedSender(int current) {
        switch (current) {
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
