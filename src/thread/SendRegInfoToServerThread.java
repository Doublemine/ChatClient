package thread;

import java.net.Socket;

import util.TcpSocketUtil;

/**
 * 
 * @author wangh
 *
 *         向服务发送登录或者注册信息，具体什么信息取决于参数statcaode。
 */
public class SendRegInfoToServerThread extends Thread {
	private Socket socket;

	private String username;
	private String passwd;
	private String statcode;
	private final static String FLAG_CODE = "&&";// 账号和密码之间的分隔符
	private final static String NOR_HEAD = "#NORMAL#";// 账号和密码之间的分隔符

	/**
	 * 用于向服务器发送登录或者注册时使用的账户名或者密码。
	 * 
	 * @param socket
	 *            连接的套接字
	 * @param username
	 *            需要发送的用户名
	 * @param passwd
	 *            需要发送的密码
	 * @param statcode
	 *            需要发送的状态码，可选的有#REG#或者#LOGIN#
	 */
	public SendRegInfoToServerThread(Socket socket, String username,
			String passwd, String statcode) {
		this.socket = socket;
		this.username = username;
		this.passwd = passwd;
		this.statcode = statcode;

	}

	@Override
	public void run() {
		TcpSocketUtil.sendStringToServer(
				socket,
				NOR_HEAD
						+ this.statcode
						+ TcpSocketUtil.encryRegInfo(this.username,
								this.passwd, FLAG_CODE));

	}

}
