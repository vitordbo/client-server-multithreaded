package udpringTest;
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
        
        String message = processId + " - " + number;
        byte[] sendData = message.getBytes();
        
        InetAddress address = InetAddress.getByName("localhost"); // put ip
        int port = (processId == 1) ? 1111 : ((processId == 2) ? 2222 : ((processId == 3) ? 3333 : 4444));
        
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, address, port);
        socket.send(sendPacket);
        
        String log = processId + " sent " + message + " to " + ((processId % 4) + 1);
        System.out.println(log);
        logs.put(processId, logs.getOrDefault(processId, "") + log + "\n");

        byte[] buffer = new byte[1024];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        socket.receive(packet);
        
        String received = new String(packet.getData(), 0, packet.getLength());
        int processId1 = Integer.parseInt(received.split("-")[0]);
        int number1 = Integer.parseInt(received.split("-")[1]);
        
        String log1 = "Received " + received + " from " + processId;
        System.out.println(log1);
        logs.put(processId, logs.getOrDefault(processId, "") + log1 + "\n");
        
        socket.close();
    }
}
