package br.com.caesar.sistemas.caesarservice.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;

import javax.xml.bind.DatatypeConverter;

public class StringUtil {

	public static boolean isEmpty(String... strings) {
		for (String string : strings) {
			if (string == null || string.trim().isEmpty()) {
				return true;
			}
		}
		return false;
	}

	public static boolean isEmpty(Collection<?> c) {
		return c == null || c.size() == 0;
	}

	public static String hashHex(String string) {
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("SHA-256");
			byte buffer[] = md.digest(string.getBytes());
			BigInteger bi = new BigInteger(1, buffer);
			return String.format("%0" + (buffer.length << 1) + "X", bi);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String hashBase64(String string) {
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("SHA-256");
			byte buffer[] = md.digest(string.getBytes());
			return DatatypeConverter.printBase64Binary(buffer);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String geraHash(String string) {
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("SHA-256");
			byte[] b = md.digest(string.getBytes());
			StringBuffer sb = new StringBuffer(new BigInteger(1, b).toString(16));
			while (sb.length() < 64) {
				sb.insert(0, '0');
			}
			String hashSenha = sb.toString();
			return hashSenha;
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
	}
}