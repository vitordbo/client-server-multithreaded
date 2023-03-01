package udpring5ATT;

import java.io.*;
import java.net.*;

public class Server implements Runnable {
    private DatagramSocket socket;
    private InetAddress address;
    private int port;
    private int id;
    private int prevId;

    public Server(int id, int port, int prevId) throws SocketException, UnknownHostException {
        this.id = id;
        this.port = port;
        this.prevId = prevId;
        socket = new DatagramSocket(port);
        address = InetAddress.getByName("localhost");
    }

    public void run() {
        try {
            while (true) {
                byte[] buffer = new byte[1024];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);
                String message = new String(packet.getData(), 0, packet.getLength());
                System.out.println("Process " + id + " received message from Process " + prevId + ": " + message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            socket.close();
        }
    }
}