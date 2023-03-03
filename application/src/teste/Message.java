package teste;

import java.nio.ByteBuffer;

public class Message {
    private final int senderId;
    private final int value;

    public Message(int senderId, int value) {
        this.senderId = senderId;
        this.value = value;
    }

    public Message(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        this.senderId = buffer.getInt();
        this.value = buffer.getInt();
    }

    public int getSenderId() {
        return senderId;
    }

    public int getValue() {
        return value;
    }

    public byte[] getBytes() {
        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.putInt(senderId);
        buffer.putInt(value);
        return buffer.array();
    }
}
