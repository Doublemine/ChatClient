package thread;

import java.net.Socket;
import java.util.concurrent.Callable;

import util.TcpSocketUtil;

public class ReceiveLoginStatThread implements Callable<Boolean> {
	private Socket socket;

	public ReceiveLoginStatThread(Socket socket) {
		this.socket = socket;
	}

	@Override
	public Boolean call() throws Exception {
		while (true) {
			boolean msg = TcpSocketUtil.getServerFlag(this.socket);
			return msg;

		}
	}
}
