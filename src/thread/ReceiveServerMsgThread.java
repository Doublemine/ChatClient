package thread;

import java.net.Socket;

import javax.swing.JTextArea;

import util.TcpSocketUtil;

public class ReceiveServerMsgThread extends Thread {
	private JTextArea chatText;
	private Socket socket;

	public ReceiveServerMsgThread(Socket socket, JTextArea chatText) {
		this.chatText = chatText;
		this.socket = socket;

	}

	@Override
	public void run() {
		while (true) {
			String msg = TcpSocketUtil.getServerData(this.socket);
			chatText.append(msg);
		}
	}

}
