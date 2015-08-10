package thread;

import java.net.Socket;

import javax.swing.JOptionPane;

import util.TcpSocketUtil;
import from.LoginForm;
import from.RegistForm;

public class ReceiveRegStatThread extends Thread {
	private Socket socket;
	private LoginForm loginForm;
	private RegistForm registForm;
	private JOptionPane jop;
	private int msg;

	public ReceiveRegStatThread(Socket socket, LoginForm loginForm,
			RegistForm registForm, JOptionPane jop) {
		this.socket = socket;
		this.jop = jop;
		this.registForm = registForm;
		this.loginForm = loginForm;
		this.msg = 0;
	}

	@SuppressWarnings("static-access")
	@Override
	public void run() {
		// System.out.println("ReceiveRegStatThread-->msg初始值=" + msg);
		while (true) {
			msg = TcpSocketUtil.getServerFlag(socket);
			if (msg != 0) {
				break;
			}
		}
		// System.out.println("ReceiveRegStatThread-->msg非初始值=" + msg);
		if (msg == 1) {// 失败
			jop.showMessageDialog(null, "此用户已经被注册，你还是想想其他的用户名吧。", "悲催的少年",
					JOptionPane.ERROR_MESSAGE);
		} else if (msg == 2) {// 成功
			jop.showMessageDialog(null, "恭喜你，注册成功！现在将不可阻挡的自动跳转到登录页。", "幸运的少年",
					JOptionPane.INFORMATION_MESSAGE);
			registForm.dispose();
			loginForm = new LoginForm();
			loginForm.setVisible(true);
		} else {// 未知错误
			jop.showMessageDialog(null, "未知错误！", "错误",
					JOptionPane.ERROR_MESSAGE);
		}
	}

}
