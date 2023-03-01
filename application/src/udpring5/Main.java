package udpring5;

public class Main {
    
    /* 
    static int contador = 0;
    public static void imprime() {
        
        * Sincronizar bloco estático
        * 
        synchronized (Main.class) {
            contador++;
            System.out.println(Thread.currentThread().getName() + ": " +  contador);
        }
    }
    */
        public static void main(String[] args) {
        try {
            Client client1 = new Client(1, 5001, 2);
            Server server1 = new Server(1, 5002, 4);
            Client client2 = new Client(2, 5003, 3);
            Server server2 = new Server(2, 5001, 1);
            Client client3 = new Client(3, 5004, 4);
            Server server3 = new Server(3, 5003, 2);
            Client client4 = new Client(4, 5002, 1);
            Server server4 = new Server(4, 5004, 3);

            
            Thread t1 = new Thread(client1);
            Thread t2 = new Thread(server1);
            Thread t3 = new Thread(client2);
            Thread t4 = new Thread(server2);
            Thread t5 = new Thread(client3);
            Thread t6 = new Thread(server3);
            Thread t7 = new Thread(client4);
            Thread t8 = new Thread(server4);

            t1.start();
            t2.start();
            t3.start();
            t4.start();
            t5.start();
            t6.start();
            t7.start();
            t8.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

