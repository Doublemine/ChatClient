package thread;

import java.io.FileInputStream;
import java.net.Socket;
import java.util.Properties;

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
	public void run() {
		try {
			Socket socket = new Socket(IPAdree, RegPort);
			ClientDataCenter.regUserSocket = socket;
		} catch (Exception exception) {
			System.err.println("RegConServer--->" + exception.toString() + "\n"
					+ "DEBUG: RegPort=" + RegPort + "\tIPAdress" + IPAdree);
		}
	}
}
