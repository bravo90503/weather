package com.mlb.homework.utils;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {
	private static ObjectMapper mapper = new ObjectMapper();

	public static Object unmarshal(String body, Class<?> clazz)
			throws IOException, JsonParseException, JsonMappingException {
		return mapper.readValue(body.getBytes(), clazz);
	}

}
