package teste;


import java.net.*;

public class Client {
    private static final String SERVER_HOSTNAME = "localhost";
    private static final int SERVER_PORT = 9876;
    private static final int BUFFER_SIZE = 1024;

    public static void main(String[] args) throws Exception {
        // Start threads for each process
        Thread t1 = new Thread(new ProcessThread(1));
        Thread t2 = new Thread(new ProcessThread(2));
        Thread t3 = new Thread(new ProcessThread(3));
        Thread t4 = new Thread(new ProcessThread(4));
        t1.start();
        t2.start();
        t3.start();
        t4.start();
    }

    private static class ProcessThread implements Runnable {
        private final int processId;

        public ProcessThread(int processId) {
            this.processId = processId;
        }

        @Override
        public void run() {
            try {
                DatagramSocket socket = new DatagramSocket();

                InetAddress serverAddress = InetAddress.getByName(SERVER_HOSTNAME);

                while (true) {
                    // Send message to next process in the ring
                    int number = (int) (Math.random() * 10) + 1;
                    String message = Integer.toString(number);

                    if (processId % 2 == 0) {
                        // If process ID is even, multiply number by 2
                        int result = number * 2;
                        message = Integer.toString(result);
                    }

                    byte[] data = message.getBytes();
                    int nextProcessId = getNextProcessId(processId);
                    InetAddress nextProcessAddress = InetAddress.getByName(getProcessAddress(nextProcessId));
                    DatagramPacket packet = new DatagramPacket(data, data.length, nextProcessAddress, SERVER_PORT);
                    socket.send(packet);

                    // Receive response from server
                    byte[] receiveBuffer = new byte[BUFFER_SIZE];
                    DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                    socket.receive(receivePacket);

                    String response = new String(receivePacket.getData(), 0, receivePacket.getLength());
                    System.out.println("Process " + processId + " received response from server: " + response);

                    // Wait for some time before sending the next message
                    Thread.sleep(1000);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private int getNextProcessId(int currentProcessId) {
            if (currentProcessId == 4) {
                return 1;
            } else {
                return currentProcessId + 1;
            }
        }

        private String getProcessAddress(int processId) {
            switch (processId) {
                case 1:
                    return "192.168.0.1";
                case 2:
                    return "192.168.0.2";
                case 3:
                    return "192.168.1.1";
                case 4:
                    return "192.168.1.2";
                default:
                    return "";
            }
        }
    }
}
