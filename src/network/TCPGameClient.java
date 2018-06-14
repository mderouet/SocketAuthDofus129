package network;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

import utils.HexaUtils;

public class TCPGameClient {

	private InetAddress host;
	private int port;
	private Socket client;
	private boolean started = false;
	private ClientResponseHandler clientResponseHandler;
	
	//1.29.1 en hexadecimal
	private String gameVersion = "312e32392e310a00";

	public TCPGameClient(InetAddress host, int port) {
		this.host = host;
		this.port = port;
	}

	public void open() {
		if (!started) {
			started = true;
			ReceiveThread receiveThread = new ReceiveThread();
			receiveThread.start();
		}
	}

	public void close() {
		if (started) {
			started = false;
		}
		if (client != null && !client.isClosed()) {
			try {
				client.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void sendTxtMsg(String msg) {
		SendThread sendThread = new SendThread(msg);

		sendThread.start();
	}

	public void sendHexMsg(String msg) {
		SendThread sendThread = new SendThread(HexaUtils.hexToAscii(msg));
		sendThread.start();
	}
	
	private class ReceiveThread extends Thread {
		@Override
		public void run() {
			super.run();
			try {
				client = new Socket(host, port);
				
				InputStream inputStream = client.getInputStream();
				byte[] bytes = new byte[1024];
				int len;
				while (started && (len = inputStream.read(bytes)) != -1) {
					String serverAnswer = new String(bytes, 0, len);
					System.out.println("SERVER : " + serverAnswer);
					clientResponse(serverAnswer);
				}

				client.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void clientResponse(String msgServer) {
		
		// Init string received, sending version,account name and password encoded
		if(msgServer.substring(0, 2).toLowerCase().contains("hc")) {
			sendHexMsg(this.gameVersion);
			sendTxtMsg("dabigdurok\n#153ZYSN32X274acZY_g61a863a_UPUS\n");
		}
	}
	
	private class SendThread extends Thread {
		private String msg;

		public SendThread(String msg) {
			this.msg = msg;
		}

		@Override
		public void run() {
			super.run();
			if (!started || client == null || client.isClosed()) {
				return;
			}
			try {
				System.out.println("CLIENT : " + msg);
				OutputStream outputStream = client.getOutputStream();
				byte[] bytes = msg.getBytes();
				outputStream.write(bytes);
				outputStream.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}