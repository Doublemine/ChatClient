package thread;

import java.net.Socket;

import util.TcpSocketUtil;

public class SendMsgToServerThread extends Thread {
	private Socket socket;
	private String msg;

	public SendMsgToServerThread(Socket socket, String msg) {
		this.socket = socket;
		this.msg = msg;
	}

	@Override
	public void run() {
		TcpSocketUtil.sendClientData(this.socket, this.msg);
	}
}
