package udpring5;

import java.io.*;
import java.net.*;

public class Client implements Runnable {
    private DatagramSocket socket;
    private InetAddress address;
    private int port;
    private int id;
    private int nextId;

    public Client(int id, int port, int nextId) throws SocketException, UnknownHostException {
        this.id = id;
        this.port = port;
        this.nextId = nextId;
        socket = new DatagramSocket();
        address = InetAddress.getByName("localhost");
    }

    public void run() {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            while (true) {
                String message = br.readLine();
                byte[] buffer = message.getBytes();
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, port);
                socket.send(packet);
                System.out.println("Process " + id + " sent message to Process " + nextId + ": " + message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            socket.close();
        }
    }
}
