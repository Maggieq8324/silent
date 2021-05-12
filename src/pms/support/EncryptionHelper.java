package pms.support;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncryptionHelper {

	private EncryptionHelper() {
	}

	public static String encrypt(final String data) {
		return md5Hex("copyright." + data + "pms@2016");
	}

	private static String md5Hex(final String data) {
		return new String(encodeHex(md5(data)));
	}

	private static char[] encodeHex(final byte[] data) {
		final char[] digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		final int l = data.length;
		final char[] out = new char[l << 1];
		for (int i = 0, j = 0; i < l; i++) {
			out[j++] = digits[(0xF0 & data[i]) >>> 4];
			out[j++] = digits[0x0F & data[i]];
		}
		return out;
	}

	private static byte[] md5(final String data) {
		MessageDigest messageDigest;
		try {
			messageDigest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
		return messageDigest.digest(getBytesUtf8(data));
	}

	private static byte[] getBytesUtf8(final String string) {
		if (string == null) {
			return null;
		}
		return string.getBytes(Charset.forName("UTF-8"));
	}

}
