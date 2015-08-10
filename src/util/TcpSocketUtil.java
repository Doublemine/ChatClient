package util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class TcpSocketUtil {

	/**
	 * 
	 * @param socket
	 *            传入与服务器连接的Socket
	 * @return 返回从服务器返回的布尔值状态
	 */
	public static int getServerFlag(Socket socket) {
		try {
			InputStream is = socket.getInputStream();
			DataInputStream dis = new DataInputStream(is);
			return dis.readInt();

		} catch (Exception e) {
			// System.err.println("TcpSocketUtil--getServerFlag--Error:"
			// + e.toString());
			return 1;
		}

	}

	/**
	 * 
	 * @param socket
	 *            传入与服务器连接的Socket
	 * @return 返回服务器返回的String消息
	 */
	public synchronized static String ReceiveServerData(Socket socket) {
		try {
			InputStream is = socket.getInputStream();
			DataInputStream dis = new DataInputStream(is);
			return dis.readUTF();

		} catch (Exception e) {
			// System.err.println("TcpSocketUtil--ReceiveServerData--Error:"
			// + e.toString());
			return "#ERROR#MSG";
		}

	}

	/**
	 * 
	 * @param strusername
	 *            待加密的用户名
	 * @param strpasswd
	 *            待加密的密码
	 * @param Flag
	 *            加密之后用户名和密码之间的连接标识符
	 * @return 返回加密之后用户名和密码连接之后的String
	 */
	public static String encryRegInfo(String strusername, String strpasswd,
			String Flag) {
		return Encryption.EncryptionStr(strusername) + Flag
				+ Encryption.EncryptionStr(strpasswd);
	}

	/**
	 * 
	 * @param str
	 *            待处理的字符串
	 * @param statcode
	 *            需要添加的前置数据
	 * @return 返回添加之后的String=statcode+str。
	 */
	public static String addStatCode(String str, String statcode) {
		return statcode + str;
	}

	/**
	 * 
	 * @param socket
	 *            指定的服务器连接socket
	 * @param msg
	 *            字符串消息
	 * @return 给指定的socket服务器发送数据，成功true，否则为false。
	 */
	public static boolean sendStringToServer(Socket socket, String msg) {
		try {
			OutputStream os = socket.getOutputStream();
			DataOutputStream dos = new DataOutputStream(os);
			dos.writeUTF(msg);// 写入字符串
			return true;

		} catch (Exception e) {
			// System.err.println("TcpSocketUtil-ERROR-sendClientData:"
			// + e.toString());
			return false;
		}

	}
}
