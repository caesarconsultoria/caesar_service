package br.com.caesar.sistemas.caesarservice.util;

import java.io.IOException;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class ServletUtil {

	private static final Logger LOGGER = Logger.getLogger(ServletUtil.class.getSimpleName());

	private static final ObjectMapper MAPPER = new ObjectMapper().setTimeZone(TimeZone.getTimeZone("America/Recife")) // TODO
																														// mover
																														// configuração
																														// da
																														// timezone
																														// para
																														// arquivo
																														// de
																														// configuração
			.setSerializationInclusion(Include.NON_NULL).enable(SerializationFeature.INDENT_OUTPUT)
			.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);

	public static void noCache(HttpServletResponse resp) {
		resp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
		resp.setHeader("Pragma", "no-cache");
		resp.setHeader("Expires", "0");

		resp.setCharacterEncoding("UTF-8");
	}

	public static long getLongParameter(String parametro, HttpServletRequest req) {
		try {
			return Long.parseLong(req.getParameter(parametro));
		} catch (NumberFormatException | NullPointerException e) {
			return 0;
		}
	}

	public static void sendJson(Object objeto, HttpServletResponse resp) {
		try {
			String json = MAPPER.writeValueAsString(objeto);
			resp.setContentType("application/json");
			resp.setCharacterEncoding("utf-8");
			resp.getOutputStream().write(json.getBytes("utf-8"));
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
			resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}

	public static String getCookie(String cookieName, HttpServletRequest req) {
		if (req.getCookies() != null) {
			for (Cookie cookie : req.getCookies()) {
				if (cookie.getName().equals(cookieName)) {
					return cookie.getValue();
				}
			}
		}
		return null;
	}

}
