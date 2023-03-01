package Quest15;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Process implements Runnable {

    private int id;
    private String ipProx;
    private int prox;
    private String ipPrev;
    private DatagramSocket socket;
    private int port;

    public Process(int id, String ipProx, int prox, String ipPrev, int port ) throws SocketException, UnknownHostException{
        this.id = id;
        this.ipProx = ipProx;
        this.prox = prox;
        this.port = port;
        this.socket = new DatagramSocket(port,InetAddress.getByName(ipProx));
        this.ipPrev = ipPrev;
    }

    @Override
    public void run() {
       try {
            while (true) {
                byte[] buffer = new byte[1024];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);

                String message = new String(packet.getData());

                System.out.println("Process p" + id + " receive: " + message);

                String text = String.valueOf(message.charAt(0));
                int numId = Integer.parseInt(x);
                if (id % 2 == 0) {
                    numId *= 2;
                }
                
                message = Integer.toString(numId);

                System.out.println("Process p" + id + " send: " + message + " to " + prox);

                buffer = message.getBytes();

                packet = new DatagramPacket(buffer, buffer.length);
                
                send(buffer);
            } 
            }catch (Exception e) {
                e.printStackTrace();
        }
    }

    private void send (byte[] message) throws IOException {

        DatagramPacket packet = new DatagramPacket(message, message.length, InetAddress.getByName(ipProx), 6000 + prox);

        socket.send(packet);
    }

}

