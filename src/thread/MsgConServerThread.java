package thread;

import java.io.FileInputStream;
import java.net.Socket;
import java.util.Properties;

import main.ClientDataCenter;

public class MsgConServerThread extends Thread {

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

	@Override
	public void run() {
		try {

			Socket socket = new Socket(IPAdree, MsgPort);
			ClientDataCenter.conServerSocket = socket;
		} catch (Exception exception) {
			System.err.println("MsgConServer--->" + exception.toString() + "\n"
					+ "DEBUG: MsgPort=" + MsgPort + "\tIPAdress" + IPAdree);
		}
	}
}
