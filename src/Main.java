import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

import network.TCPGameClient;

public class Main {

	public static final String ADDRESS = "213.248.126.93";
	public static final int PORT = 443;

	public static void main(String[] args) throws InterruptedException {
		try {
			TCPGameClient tcpClient = new TCPGameClient(InetAddress.getByName(ADDRESS), PORT);
			tcpClient.open();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		
		
	}
}
