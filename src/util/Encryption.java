package util;

import java.security.MessageDigest;

public class Encryption {

	/**
	 * 
	 * @param str
	 *            传入带加密的str字符串
	 * @return 返回加密过后的字符串
	 * 
	 *         此函数为静态函数，不需要实例化即可使用。使用MD5粗略完成加密
	 */
	public static String EncryptionStr(String str) {
		String md5srt = "";
		try {
			MessageDigest mdDigest = MessageDigest.getInstance("MD5");
			mdDigest.update(str.getBytes());
			byte[] temp = mdDigest.digest();
			md5srt = byteArrayToHex(temp);
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return md5srt;
	}

	/**
	 * 
	 * @param bytes
	 *            传入一个字符数组
	 * @return 返回16进制数组的String类型
	 */
	private static String byteArrayToHex(byte[] bytes) {
		// 字符数组，用来存放十六进制字符
		char[] hexReferChars = { '0', '1', '2', '3', '4', '5', '6', '7', '8',
				'9', 'A', 'B', 'C', 'D', 'E', 'F' };
		char[] hexChars = new char[bytes.length * 2];// 一个字节占8位，一个十六进制字符占4位；十六进制字符数组的长度为字节数组长度的两倍
		int index = 0;
		for (byte b : bytes) {
			hexChars[index++] = hexReferChars[b >> 4 & 0xf];// 取字节的高4位
			hexChars[index++] = hexReferChars[b & 0xf];// 取字节的低4位
		}
		return new String(hexChars);
	}

	// public static void main(String[] args) {
	// String s = "000000";
	// String d = "00000";
	// System.out.println("EncryptionStr('123'):" + EncryptionStr(s));
	// System.out.println("EncryptionStr('456'):" + EncryptionStr(d));
	// }
}
