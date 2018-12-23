package br.com.caesar.sistemas.caesarservice.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.net.ssl.HttpsURLConnection;
import javax.persistence.EntityManager;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.caesar.sistemas.caesarservice.dao.ConfiguracaoDAO;
import br.com.caesar.sistemas.caesarservice.manager.PersistenceManager;

public class ReCaptcha {

	private static final Logger LOGGER = Logger.getLogger(ReCaptcha.class.getSimpleName());
	private static final String URL = "captcha.url";
	private static final String SECRET = "captcha.secret";
	private static final String HOST = "captcha.proxy.host";
	private final static String USER_AGENT = "Mozilla/5.0";

	public static final Proxy PROXY = getProxy();

	public boolean validate(String gRecaptchaResponse) {
		if (gRecaptchaResponse == null || "".equals(gRecaptchaResponse)) {
			return false;
		}

		EntityManager em = PersistenceManager.getEntityManager();
		ConfiguracaoDAO configuracaoDAO = new ConfiguracaoDAO(em);
		try {
			String configUrl = configuracaoDAO.getAsString(URL);
			String configSecret = configuracaoDAO.getAsString(SECRET);
			URL url = new URL(configUrl);
			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection(PROXY);

			conn.setRequestMethod("POST");
			conn.setRequestProperty("User-Agent", USER_AGENT);
			conn.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

			String postParams = "secret=" + configSecret + "&response=" + gRecaptchaResponse;
			conn.setDoOutput(true);
			DataOutputStream out = new DataOutputStream(conn.getOutputStream());
			out.writeBytes(postParams);
			out.flush();
			out.close();

			int responseCode = conn.getResponseCode();
			LOGGER.info("ReCaptcha: " + configUrl + ", (" + postParams + ") -> (" + responseCode + ")");

			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			StringBuffer response = new StringBuffer();

			while ((line = in.readLine()) != null) {
				response.append(line);
			}
			in.close();

			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode node = objectMapper.readTree(response.toString());

			return node.get("success").asBoolean();
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, e.getMessage(), e);
			return false;
		} finally {
			em.close();
		}
	}

	private static Proxy getProxy() {
		EntityManager em = PersistenceManager.getEntityManager();
		ConfiguracaoDAO configDao = new ConfiguracaoDAO(em);
		try {
			String host = configDao.getAsString(HOST);
			if (!StringUtil.isEmpty(host)) {
				int porta = (int) configDao.getAsLong("captcha.proxy.porta", 8080);
				LOGGER.info(String.format("ReCaptcha: usando proxy %s:%d", host, porta));
				return new Proxy(Proxy.Type.HTTP, new InetSocketAddress(host, porta));
			}
			LOGGER.info("ReCaptcha: sem proxy");
		} finally {
			em.close();
		}
		return Proxy.NO_PROXY;
	}
}