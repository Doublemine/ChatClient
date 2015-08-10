package thread;

import java.io.FileInputStream;
import java.net.Socket;
import java.util.Properties;

import javax.swing.JOptionPane;

import main.ClientDataCenter;
import util.TcpSocketUtil;

public class MsgConServerThread extends Thread {

	private String username;
	private static int MsgPort;
	private static String IPAdree;
	private final static String SET_STRING = "config/config.properties";
	static {
		Properties getProperties = new Properties();
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(SET_STRING);
			getProperties.load(fis);
			fis.close();
			MsgPort = Integer.parseInt(getProperties.getProperty("MsgPort"));
			IPAdree = getProperties.getProperty("IPAdress");
		} catch (Exception e) {
			System.err.println(e.toString());

		}
	}

	public MsgConServerThread(String username) {
		this.username = username;
	}

	@Override
	public void run() {
		try {

			Socket socket = new Socket(IPAdree, MsgPort);// 建立到服务器的连接
			TcpSocketUtil.sendStringToServer(socket, "#USERINFO#" + username
					+ "&&" + socket.getLocalAddress().toString());// 向服务器发送用户信息
			ClientDataCenter.conServerSocket = socket;
		} catch (Exception exception) {
			JOptionPane.showMessageDialog(null,
					"未能够连接到服务器，程序将自动退出。\n请检查网络连接或者config目录下的配置文件是否正确！", "严重警告",
					JOptionPane.ERROR_MESSAGE);
			System.exit(-1);

		}
	}
}
