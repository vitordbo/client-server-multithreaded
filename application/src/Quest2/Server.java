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

        while (true) {
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            serverSocket.receive(receivePacket);

            MessageHandler handler = new MessageHandler(serverSocket, receivePacket);
            Thread thread = new Thread(handler);
            thread.start();
        }
    }

    private static class MessageHandler implements Runnable {
        private DatagramSocket serverSocket;
        private DatagramPacket receivePacket;

        public MessageHandler(DatagramSocket serverSocket, DatagramPacket receivePacket) {
            this.serverSocket = serverSocket;
            this.receivePacket = receivePacket;
        }

        @Override
        public void run() {
            String message = new String(receivePacket.getData(), 0, receivePacket.getLength());
            System.out.println("Received message: " + message);

            // Evaluate message content
            String[] parts = message.split("-");
            int processId = Integer.parseInt(parts[0]);
            int value = Integer.parseInt(parts[1]);

            // Create a new message with the process id and the new value
            int nextProcessId = 1;
            String newMessage = nextProcessId + "-" + value;

            // Send the new message to all other processes in the network
            byte[] sendData = newMessage.getBytes();

            // broadcast
            // Iterate through all possible process IDs except for the center node
            for (int i = 2; i <= 4; i++) {
                try {
                    DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, receivePacket.getAddress(), PORT + i);
                    serverSocket.send(sendPacket);
                    System.out.println("Sent message to process " + i + ": " + newMessage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
