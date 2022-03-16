package com.mlb.homework.utils;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.mlb.homework.response.model.mlb.VenueResponse;

public class UnitTest {

	@Test
	public void TestLocalDateTime() {
		Date endDateTime = new Date();
		LocalDateTime pEndLocalDateTime = TimeUtils.convertDateToLocalDateTime(endDateTime);
		Assert.assertNotNull(pEndLocalDateTime);
	}

	@Test
	public void TestJsonUnmarshal() throws JsonParseException, JsonMappingException, IOException {
		String body = "{\"copyright\": \"2022\"}";
		VenueResponse venueResponse = (VenueResponse) JsonUtils.unmarshal(body, VenueResponse.class);

		Assert.assertEquals("expecting 2022 but got " + venueResponse.getCopyright(), "2022",
				venueResponse.getCopyright());
	}
}
