package br.com.caesar.sistemas.caesarservice.util;

import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.persistence.EntityManager;

import br.com.caesar.sistemas.caesarservice.exception.AutenticacaoException;
import br.com.caesar.sistemas.caesarservice.manager.PersistenceManager;

public class AutenticacaoLDAP {
	private static final Logger LOGGER = Logger.getLogger(AutenticacaoLDAP.class.getSimpleName());

	private AutenticacaoLDAP() {
	}

	/*
	 * public static boolean autentica(Usuario usuario, String senha) { if
	 * (StringUtil.isEmpty(senha) || usuario == null) return false;
	 * 
	 * String sigla = usuario.getOrgao().toString();
	 * 
	 * boolean resultado = false; EntityManager em =
	 * PersistenceManager.getEntityManager(); try { Hashtable<String, String>
	 * properties = new Hashtable<String, String>();
	 * 
	 * try { ConfiguracaoDAO confDao = new ConfiguracaoDAO(em); String
	 * chaveMascara = sigla.concat(".ldap.mascara"); String chaveUrl =
	 * sigla.concat(".ldap.url"); String principal =
	 * confDao.getAsString(chaveMascara); String url =
	 * confDao.getAsString(chaveUrl);
	 * properties.put(Context.INITIAL_CONTEXT_FACTORY,
	 * "com.sun.jndi.ldap.LdapCtxFactory"); properties.put(Context.PROVIDER_URL,
	 * url); properties.put(Context.SECURITY_AUTHENTICATION, "simple");
	 * properties.put(Context.SECURITY_PRINCIPAL, String.format(principal,
	 * usuario.getLogin())); properties.put(Context.SECURITY_CREDENTIALS,
	 * senha);
	 * 
	 * DirContext authContext = new InitialDirContext(properties);
	 * authContext.close(); resultado = true; } catch (NoResultException nre) {
	 * LOGGER.log(Level.WARNING, "Não há configuração de LDAP para orgao " +
	 * sigla, nre); } } catch (AuthenticationException e) {
	 * LOGGER.log(Level.WARNING, "Erro durante autenticação", e); } catch
	 * (NamingException e) { LOGGER.log(Level.WARNING, "Erro durante conexão",
	 * e); } finally { em.close(); }
	 * 
	 * return resultado; }
	 */
	public static boolean testarAutenticacao(String url, String usuario, String senha) throws AutenticacaoException {
		if (StringUtil.isEmpty(senha) || usuario == null)
			return false;

		boolean resultado = false;
		EntityManager em = PersistenceManager.getEntityManager();
		try {
			Hashtable<String, String> properties = new Hashtable<String, String>();

			properties.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
			properties.put(Context.PROVIDER_URL, url);
			properties.put(Context.SECURITY_AUTHENTICATION, "simple");
			properties.put(Context.SECURITY_PRINCIPAL, usuario);
			properties.put(Context.SECURITY_CREDENTIALS, senha);

			DirContext authContext = new InitialDirContext(properties);
			authContext.close();
			resultado = true;
		} catch (AuthenticationException e) {
			LOGGER.log(Level.WARNING, "Erro durante autenticação", e);
			throw new AutenticacaoException(e.getMessage());
		} catch (NamingException e) {
			LOGGER.log(Level.WARNING, "Erro durante conexão", e);
			throw new AutenticacaoException(e.getMessage());
		} finally {
			em.close();
		}

		return resultado;
	}

}
