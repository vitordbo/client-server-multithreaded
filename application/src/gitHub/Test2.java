/*
Trabalho pr�tico sobre sockets UDP,
para disciplina de Sistemas Distribuidos.
Equipe:
Alaim de Jesus Le�o Costa
Manoel Malon Costa de Moura
*/
package gitHub;

public class Test2 {
	public static void main(String[] args) throws Exception{
		new Thread(new ClienteUdp(8884)).start();
		new Thread(new ServidorUdp(8884)).start();
	}
}
