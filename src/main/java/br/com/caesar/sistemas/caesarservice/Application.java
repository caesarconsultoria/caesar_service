package br.com.caesar.sistemas.caesarservice;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("api")
public class Application extends ResourceConfig {

	public static final String MEDIA_TYPE_JSON = "application/json; charset=utf-8";

	public Application() {
		super();
		packages("br.com.caesar.sistemas.caesarservice.rest", "com.fasterxml.jackson.jaxrs.json");
		register(MultiPartFeature.class);
		register(ObjectMapperProvider.class);
	}
}
