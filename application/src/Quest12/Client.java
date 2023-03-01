package Quest12;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.Scanner;


public class Client {
    private static final int PORT = 12345;
    private static final String[] PROCESS_NAMES = {"P1", "P2", "P3", "P4"};
    private static final String[] PROCESS_IPS = {"192.168.1.4", "192.168.1.4", "192.168.1.4", "192.168.1.4"};
    private static final int[] PROCESS_PORTS = {12346, 12347, 12348, 12349};

    public static void main(String[] args) {

        try {
            DatagramSocket socket = new DatagramSocket();
            InetAddress serverAddress = InetAddress.getByName("192.168.1.4");

            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter message (type 'exit' to quit):");

            String message = scanner.nextLine();

            while (!message.equals("exit")) {
                // Send message to all processes in the ring
                for (int i = 0; i < PROCESS_NAMES.length; i++) {
                    String processName = PROCESS_NAMES[i];
                    String processIP = PROCESS_IPS[i];
                    int processPort = PROCESS_PORTS[i];

                    InetAddress address = InetAddress.getByName(processIP);
                    byte[] buffer = message.getBytes();

                    DatagramPacket packet = new DatagramPacket(message.getBytes(), buffer.length, serverAddress, processPort);

                    socket.send(packet);
                    System.out.println("Sent to " + processName + ": " + message);
                    System.out.println(serverAddress);
                }

                System.out.println("Enter message (type 'exit' to quit):");
                message = scanner.nextLine();
            }

            socket.close();
            System.out.println("Connection closed.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ClientThread extends Thread {
        private String processName;
        private int processPort;

        public ClientThread(String processName, int processPort) {
            this.processName = processName;
            this.processPort = processPort;
        }

        public void run() {
            try {
                DatagramSocket socket = new DatagramSocket(processPort);
                byte[] buffer = new byte[1024];

                while (true) {
                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                    socket.receive(packet);
                    String receivedMessage = new String(packet.getData(), 0, packet.getLength());

                    System.out.println("Received from " + processName + ": " + receivedMessage);

                    // Send message to next process in the ring
                    int nextProcessIndex = (Arrays.asList(PROCESS_NAMES).indexOf(processName) + 1) % PROCESS_NAMES.length;
                    String nextProcessIP = PROCESS_IPS[nextProcessIndex];
                    int nextProcessPort = PROCESS_PORTS[nextProcessIndex];

                    InetAddress address = InetAddress.getByName(nextProcessIP);
                    buffer = receivedMessage.getBytes();

                    packet = new DatagramPacket(buffer, buffer.length, address, nextProcessPort);

                    socket.send(packet);
                    System.out.println("Sent to " + PROCESS_NAMES[nextProcessIndex] + ": " + receivedMessage);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
