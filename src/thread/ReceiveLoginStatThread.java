package thread;

import java.net.Socket;

import javax.swing.JOptionPane;

import util.TcpSocketUtil;
import from.ChatForm;
import from.LoginForm;

public class ReceiveLoginStatThread extends Thread {
	private Socket socket;
	private int flag;
	private ChatForm chatform;
	private JOptionPane jop;
	private LoginForm loginForm;
	private String username;

	public ReceiveLoginStatThread(Socket socket, ChatForm chatform,
			JOptionPane jop, LoginForm loginForm, String username) {
		this.socket = socket;
		this.flag = 0;
		this.chatform = chatform;
		this.jop = jop;
		this.loginForm = loginForm;
		this.username = username;
	}

	@SuppressWarnings("static-access")
	@Override
	public void run() {

		while (true) {

			flag = TcpSocketUtil.getServerFlag(this.socket);
			if (flag != 0) {
				break;
			}

		}

		if (flag == 1) {// 失败
			jop.showMessageDialog(null, "密码或者用户名无效！", "悲催的少年",
					JOptionPane.ERROR_MESSAGE);

		} else if (flag == 2) {// 成功
			loginForm.dispose();
			chatform = new ChatForm(username);
			chatform.setVisible(true);

		} else {// 其他错误
			// System.err.println("cuowu");
			jop.showMessageDialog(null, "未知错误！", "悲催的少年",
					JOptionPane.ERROR_MESSAGE);

		}

	}
}
