package thread;

import java.net.Socket;

import util.TcpSocketUtil;

public class SendRegInfoToServerThread extends Thread {
	private Socket socket;

	private String username;
	private String passwd;
	private String statcode;
	private final static String FLAG_CODE = "&&";

	public SendRegInfoToServerThread(Socket socket, String username,
			String passwd, String statcode) {
		this.socket = socket;
		this.username = username;
		this.passwd = passwd;
		this.statcode = statcode;

	}

	@Override
	public void run() {
		TcpSocketUtil.sendClientData(
				socket,
				this.statcode
						+ TcpSocketUtil.encryRegInfo(this.username,
								this.passwd, FLAG_CODE));
	}

}
