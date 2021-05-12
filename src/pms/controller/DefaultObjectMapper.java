package pms.controller;

import java.text.SimpleDateFormat;

import com.fasterxml.jackson.databind.ObjectMapper;

public class DefaultObjectMapper extends ObjectMapper {

	private static final long serialVersionUID = -2599911885743630132L;

	public DefaultObjectMapper() {
		setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
	}

}
