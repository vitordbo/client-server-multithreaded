/*
Trabalho pr�tico sobre sockets UDP,
para disciplina de Sistemas Distribuidos.
Equipe:
Alaim de Jesus Le�o Costa
Manoel Malon Costa de Moura
*/
package gitHub;
import java.util.*;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class ServidorUdp implements Runnable{
 
    private String user;
    private int formPort;
    String questao1 = "1;5;VVFF";
    String questao2 = "2;2;FFFF";
    String questao3 = "3;1;FVFV";
    
    DatagramSocket datagramSocket = null;
	
    public ServidorUdp(int formPort) throws Exception {
        this.user = "localhost";
        this.formPort = formPort;
        datagramSocket = new DatagramSocket(formPort);
    }
    
	@Override
	public void run() {
		while (true){
	           try {
	               byte[] bytes = new byte[1024];
	               DatagramPacket datagramPacket = new DatagramPacket(bytes,0,bytes.length);
	               datagramSocket.receive(datagramPacket);
	               String msgDecode = new String(datagramPacket.getData());
	               int quantAcertos = 0;

	               switch (msgDecode.charAt(0)) {
	               		case '1':
	               			for(int i = 4; i <= 7; i++) {
	               				if (Character.compare(msgDecode.charAt(i), questao1.charAt(i))==0){
	     	            		   quantAcertos++;
	     	            	   }
	               			}
	               		break;
	               		
	               		case '2':
	               			for(int i = 4; i <= 7; i++) {
	               				if (Character.compare(msgDecode.charAt(i), questao2.charAt(i))==0){
		     	            		   quantAcertos++;
		     	            	}
	               			}
		               	break;
		               	
	               		case '3':
	               			for(int i = 4; i <= 7; i++) {
	               				if (Character.compare(msgDecode.charAt(i), questao3.charAt(i))==0){
		     	            		   quantAcertos++;
		     	            	}
	               			}
		               	break;
	               		
	               		default:
	               			System.out.println("Alternativa nao encontrada!");
	               		break;
	               }
	               
	               byte[] sendData = new byte[1024];
	               sendData = (msgDecode.charAt(0)+";"+quantAcertos+";"+(4-quantAcertos)).getBytes();
	              
	               DatagramPacket resposta = new DatagramPacket(sendData, sendData.length,
	            		   datagramPacket.getAddress(), datagramPacket.getPort());
	               datagramSocket.send(resposta);
	               
	           } catch (IOException e) {
	    
	               e.printStackTrace();
	           }
	       }
	}

}
