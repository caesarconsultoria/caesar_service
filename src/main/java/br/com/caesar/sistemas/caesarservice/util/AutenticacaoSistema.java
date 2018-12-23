package br.com.caesar.sistemas.caesarservice.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

//import br.com.caesar.sistemas.caesarservice.model.Usuario;

public class AutenticacaoSistema {

	/*public static boolean autentica(Usuario usuario, String senha) {
		if (usuario == null || StringUtil.isEmpty(senha, usuario.getHashSenha())) {
			return false;
		}

		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte[] b = md.digest(senha.getBytes());
			StringBuffer sb = new StringBuffer(new BigInteger(1, b).toString(16));
			while (sb.length() < 64) {
				sb.insert(0, '0');
			}
			String hashSenha = sb.toString();
			return hashSenha.equalsIgnoreCase(usuario.getHashSenha());
		} catch (NoSuchAlgorithmException e) {
			return false;
		}
	}*/

}
