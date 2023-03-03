package teste;

public interface ServerHandler {
    void handleMessage(Message message, SocketAddress senderAddress) throws IOException;
}
