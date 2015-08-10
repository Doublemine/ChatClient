package thread;

import java.io.IOException;
import java.net.Socket;

import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import util.TcpSocketUtil;

public class ReceiveServerMsgThread extends Thread {
	private JTextArea chatText;
	private Socket socket;
	private DefaultListModel listModel;// 在线用户列表

	public ReceiveServerMsgThread(Socket socket, JTextArea chatText,
			DefaultListModel listModel) {
		this.chatText = chatText;
		this.socket = socket;
		this.listModel = listModel;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void run() {
		while (true) {
			String msg = TcpSocketUtil.ReceiveServerData(socket);
			// System.out.println("接收到来自服务器的聊天信息：" + msg + "\n"
			// + socket.isClosed());
			if (msg.startsWith("#USERINFOADD#")) {// 如果为添加用户信息
				listModel.addElement(msg.substring("#USERINFOADD#".length()));
			} else if (msg.startsWith("#USERINFOCLOSED#")) {// 如果为删除用户信息
				listModel.removeElement(msg.substring("#USERINFOCLOSED#"
						.length()));
			} else if (msg.startsWith("#ERROR#")) {
				try {

					socket.close();
					JOptionPane.showMessageDialog(null, "系统遇到非常严重的错误，将自动关闭！",
							"严重错误", JOptionPane.ERROR_MESSAGE);
					System.exit(-1);
					break;

				} catch (IOException e) {

				}

			} else {// 正常的聊天信息

				chatText.append(msg);
			}

		}
	}
}
