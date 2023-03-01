package gitHub;

public class Test {
	public static void main(String[] args) throws Exception {
		new Thread(new ClienteUdp(8888)).start();
		new Thread(new ServidorUdp(8888)).start();
	}
}
