/*
Trabalho prático sobre sockets UDP,
para disciplina de Sistemas Distribuidos.
Equipe:
Alaim de Jesus Leão Costa
Manoel Malon Costa de Moura
*/
package gitHub;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;

public class ClienteUdp implements Runnable {

    private String toIP;
    //private int fromPort;
    private int toPort;
    BufferedReader bufferedReader=null;
    DatagramSocket datagramSocket=null;
	
    
    public ClienteUdp(int toPort) {
        this.toIP = "localhost";
        this.toPort = toPort;
        //this.fromPort = fromPort;
        try {
            datagramSocket = new DatagramSocket();
            bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println(" Insira uma mensagem: ");
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }
    
	@Override
	public void run() {
        while (true){
            try {
                String s = bufferedReader.readLine();
                DatagramPacket datagramPacket = new DatagramPacket(s.getBytes(), 0, s.getBytes().length, new InetSocketAddress(this.toIP,this.toPort));
                datagramSocket.send(datagramPacket);
                
                byte[] buffer = new byte[1000];
                DatagramPacket resp = new DatagramPacket(buffer, buffer.length);
                datagramSocket.setSoTimeout(10000);//definido tempo de resposta conexão ficara bloqueada
                datagramSocket.receive(resp); 
                System.out.println("* Resposta do servidor:\n" + new String(resp.getData()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
	}
}
