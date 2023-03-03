package teste;

import java.io.IOException;
import java.net.*;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Process {
    private final int id;
    private final Map<Integer, InetSocketAddress> neighbors;
    private final DatagramSocket socket;
    private final List<Message> messageLog;

    public Process(int id, Map<Integer, InetSocketAddress> neighbors) throws SocketException {
        this.id = id;
        this.neighbors = neighbors;
        this.socket = new DatagramSocket();
        this.messageLog = new ArrayList<>();
    }

    public void start() {
        InetSocketAddress nextAddress = neighbors.get((id + 1) % 4);
    
        // send the first message to the next neighbor
        Message firstMessage = new Message(id, 1);
        try {
            sendMessage(firstMessage, nextAddress);
            messageLog.add(firstMessage);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    
        // receive messages until the process is interrupted
        while (!Thread.currentThread().isInterrupted()) {
            try {
                receiveMessage();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
    }    

    private void sendMessage(Message message, InetSocketAddress address) throws IOException {
        byte[] buffer = message.getBytes();
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address);
        socket.send(packet);
    }

    private void receiveMessage() throws IOException {
        byte[] buffer = new byte[1024];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        socket.receive(packet);
        Message message = new Message(packet.getData());
        messageLog.add(message);
        handleReceivedMessage(message);
    }

    private void handleReceivedMessage(Message message) throws IOException {
        int senderId = message.getSenderId();
        int value = message.getValue();

        if (value % 2 == 0 && id % 2 == 0) {
            // multiply the value by 2 and send it to the next neighbor
            value *= 2;
            InetSocketAddress nextAddress = neighbors.get((id + 1) % 4);
            Message newMessage = new Message(id, value);
            sendMessage(newMessage, nextAddress);
            messageLog.add(newMessage);
        } else if (value % 2 != 0 && id % 2 != 0) {
            // pass the value to the next neighbor
            InetSocketAddress nextAddress = neighbors.get((id + 1) % 4);
            Message newMessage = new Message(id, value);
            sendMessage(newMessage, nextAddress);
            messageLog.add(newMessage);
        } else {
            // print an error message
            System.out.println("It is not possible to operate with this kind of data. Try again!");
        }
    }

    public List<Message> getMessageLog() {
        return Collections.unmodifiableList(messageLog);
    }
}
