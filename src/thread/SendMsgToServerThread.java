package thread;

import java.net.Socket;

import util.TcpSocketUtil;

public class SendMsgToServerThread extends Thread {
	private Socket socket;
	private String msg;
	private final static String NOR_HEAD = "#NORMAL#";

	public SendMsgToServerThread(Socket socket, String msg) {
		this.socket = socket;
		this.msg = msg;
	}

	@Override
	public void run() {
		TcpSocketUtil.sendStringToServer(socket, NOR_HEAD + msg);
	}
}
