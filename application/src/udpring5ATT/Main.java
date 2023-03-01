package udpring5ATT;

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
            Client client2 = new Client(2, 5003, 3);
            Client client3 = new Client(3, 5004, 4);
            Client client4 = new Client(4, 5002, 1);

            
            Thread t1 = new Thread(client1);
            Thread t3 = new Thread(client2);
            Thread t5 = new Thread(client3);
            Thread t7 = new Thread(client4);

            t1.start();
            t3.start();
            t5.start();
            t7.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

