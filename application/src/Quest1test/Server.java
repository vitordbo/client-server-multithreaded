package Quest1test;

import java.net.InetAddress;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.*;

public class Server {
    public static void main(String[] args) {
        try {
            DatagramSocket socket = new DatagramSocket(5000);
            byte[] buffer = new byte[1024];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            Map<Integer, Integer> processValues = new HashMap<>();
            
            while (true) {
                socket.receive(packet);
                String message = new String(buffer, 0, packet.getLength());
                String[] parts = message.split("-");
                int processId = Integer.parseInt(parts[0].trim());
                int value = Integer.parseInt(parts[1].trim());
                String newMessage = "";
                
                if (value % 2 == 0) {
                    value *= 2;
                    newMessage = "[" + processId + " - " + value + "]";
                } else if (value % 2 == 1) {
                    newMessage = "[" + processId + " - " + value + "]";
                } else {
                    System.out.println("it is not possible to operate with this kind of data Try again!");
                    continue;
                }
                
                processValues.put(processId, value);
                System.out.println("Received message: " + message);
                System.out.println("Stored value: " + value);
                
                int nextProcessId = getNextProcess(processId);
                InetAddress address = InetAddress.getByName("localhost");
                int port = 5000 + nextProcessId;
                byte[] newBuffer = newMessage.getBytes();
                DatagramPacket newPacket = new DatagramPacket(newBuffer, newBuffer.length, address, port);
                socket.send(newPacket);
                System.out.println("Sent message: " + newMessage);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static int getNextProcess(int processId) {
        if (processId == 4) {
            return 1;
        } else {
            return processId + 1;
        }
    }
}

