package br.com.caesar.sistemas.caesarservice.util;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Config {
	private static final String BUNDLE_NAME = "config"; //$NON-NLS-1$

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

	private Config() {
	}

	public static String get(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return null;
		}
	}
}
