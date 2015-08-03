package thread;

import java.net.Socket;
import java.util.concurrent.Callable;

import util.TcpSocketUtil;

public class ReceiveRegStatThread implements Callable<Boolean> {
	private Socket socket;
	private final static String REG_SUCC_STR = "&LOGIN_COMPLETE&";

	public ReceiveRegStatThread(Socket socket) {
		this.socket = socket;
	}

	@Override
	public Boolean call() throws Exception {
		while (true) {
			String msg = TcpSocketUtil.getServerData(socket);
			if (msg.equals(REG_SUCC_STR)) {

				return true;
			} else {
				/**
				 * 此处可能会发生逻辑错误
				 */
				return false;
			}
		}
	}
}
