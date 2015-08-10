package thread;

import java.io.FileInputStream;
import java.net.Socket;
import java.util.Properties;

import javax.swing.JOptionPane;

import main.ClientDataCenter;

public class RegConServerThread extends Thread {

	private static int RegPort;
	private static String IPAdree;
	private final static String SET_STRING = "config/config.properties";
	static {
		Properties getProperties = new Properties();
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(SET_STRING);
			getProperties.load(fis);
			fis.close();
			RegPort = Integer.parseInt(getProperties.getProperty("RegPort"));
			IPAdree = getProperties.getProperty("IPAdress");
		} catch (Exception e) {
			System.err.println(e.toString());

		}
	}

	@Override
	/**
	 * 此进程用于建立与服务器的连接
	 */
	public void run() {
		try {
			Socket socket = new Socket(IPAdree, RegPort);
			ClientDataCenter.regUserSocket = socket;
		} catch (Exception exception) {
			JOptionPane.showMessageDialog(null,
					"未能够连接到服务器，程序将自动退出。\n请检查网络连接或者config目录下的配置文件是否正确！", "严重警告",
					JOptionPane.ERROR_MESSAGE);
			System.exit(-1);
		}
	}
}
