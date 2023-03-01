package udpring5ATT;

public class ServerUDP {
    
    public static void main(String[] args) {
          
        try {
            Server server1 = new Server(1, 5002, 4);
            Server server2 = new Server(2, 5001, 1);
            Server server3 = new Server(3, 5003, 2);
            Server server4 = new Server(4, 5004, 3);

            Thread t2 = new Thread(server1);
            Thread t4 = new Thread(server2);
            Thread t6 = new Thread(server3);
            Thread t8 = new Thread(server4);

            t2.start();
            t4.start();         
            t6.start();        
            t8.start();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}