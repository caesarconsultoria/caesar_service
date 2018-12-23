package br.com.caesar.sistemas.caesarservice;

import java.util.TimeZone;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import br.com.caesar.sistemas.caesarservice.util.Config;

@Provider
public class ObjectMapperProvider implements ContextResolver<ObjectMapper> {

	private ObjectMapper mapper;

	public ObjectMapperProvider() {
		mapper = new ObjectMapper().setTimeZone(TimeZone.getTimeZone(Config.get("sistema.timezone")))
				.setSerializationInclusion(Include.NON_NULL).enable(SerializationFeature.INDENT_OUTPUT)
				.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
	}

	@Override
	public ObjectMapper getContext(Class<?> type) {
		return mapper;
	}

}