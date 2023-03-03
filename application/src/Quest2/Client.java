package Quest2;

import java.net.*;
import java.io.*;
import java.util.*;

public class Client {
    private static DatagramSocket socket;
    private static Map<Integer, String> logs = new HashMap<>();

    public static void main(String[] args) throws IOException {
        socket = new DatagramSocket();

        System.out.println("Client started...");

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the process ID (1, 2, 3, or 4): ");
        int processId = sc.nextInt();

        System.out.println("Enter the number to be sent: ");
        int number = sc.nextInt();

        System.out.println("Enter the Id for the destination (1, 2, 3, or 4): ");
        int processIdDesti = sc.nextInt(); // id from destination

        String message = processId + "-" + number + "-" + processIdDesti;
        byte[] sendData = message.getBytes();

        InetAddress address = InetAddress.getByName("localhost"); // ip from ther other pc
        int port = 12345;

        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, address, port);
        
        // Create a thread for sending the message
        Thread sendThread = new Thread(() -> {
            try {
                socket.send(sendPacket);
                String log = processId + " sent " + message + " to all and to destination = " + getDesti(processIdDesti);
                System.out.println(log);
                logs.put(processId, logs.getOrDefault(processId, "") + log + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        sendThread.start();

        byte[] buffer = new byte[1024];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        
        // Create a thread for receiving the message
        Thread receiveThread = new Thread(() -> {
            try {
                socket.receive(packet);

                String received = new String(packet.getData(), 0, packet.getLength());
                int id = Integer.parseInt(received.split("-")[0]);
                int IdDestina = Integer.parseInt(received.split("-")[1]);

                String log1 = "Received " + received + " from " + IdDestina;
                System.out.println(log1);
                logs.put(processId, logs.getOrDefault(processId, "") + log1 + "\n");

                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        receiveThread.start();
    }

    private static int getDesti(int currentProcessId) {
        switch (currentProcessId) {
            case 1:
                return 1;
            case 2:
                return 2;
            case 3:
                return 3;
            case 4:
                return 4;
            default:
                return -1;
        }
    }
}
