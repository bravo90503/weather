package com.mlb.homework.service;

import java.io.IOException;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.mlb.homework.response.model.mlb.Schedule;
import com.mlb.homework.response.model.mlb.Venue;
import com.mlb.homework.response.model.mlb.VenueResponse;
import com.mlb.homework.utils.HttpUtils;
import com.mlb.homework.utils.JsonUtils;

@Service
public class StatsService {
	private static Logger logger = LoggerFactory.getLogger(StatsService.class);
	@Value("${microservice.host}")
	public String microserviceHost;
	@Value("${server.port}")
	public String serverPort;

	public Venue getVenue(int id) {
		try {
			ResponseEntity<String> responseEntity = null;
			try {
				StringBuilder path = new StringBuilder(microserviceHost + ":" + serverPort)//
						.append("/stats/api/v1/venues/")//
						.append(id).append("?hydrate=location");

				final HttpGet request = new HttpGet(path.toString());
				// execute request
				CloseableHttpResponse response = HttpUtils.client.execute(request);

				responseEntity = HttpUtils.getResponseEntity(response);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}

			if (HttpUtils.isSuccessful(responseEntity)) {
				logger.info("/venue; status=successful");
				HttpHeaders headers = new HttpHeaders();
				headers.add("Content-Type", "application/json; charset=UTF-8");

				VenueResponse venueResponse = (VenueResponse) JsonUtils.unmarshal(responseEntity.getBody(),
						VenueResponse.class);
				return venueResponse.getVenues().get(0);
			} else {
				logger.warn(responseEntity.getBody());
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return null;
	}

	public Schedule getSchedule(String teamIds, String startDate, String endDate) {
		return getSchedule("games", "1", teamIds, startDate, endDate);
	}

	public Schedule getSchedule(String scheduleTypes, String sportIds, String teamIds, String startDate,
			String endDate) {
		try {
			ResponseEntity<String> responseEntity = null;
			try {
				StringBuilder path = new StringBuilder(microserviceHost + ":" + serverPort)//
						.append("/stats/api/v1/schedule?")//
						.append("scheduleTypes=").append(scheduleTypes).append("&") //
						.append("sportIds=").append(sportIds).append("&")//
						.append("teamIds=").append(teamIds).append("&")//
						.append("startDate=").append(startDate).append("&")//
						.append("endDate=").append(endDate);

				final HttpGet request = new HttpGet(path.toString());
				// execute request
				CloseableHttpResponse response = HttpUtils.client.execute(request);

				responseEntity = HttpUtils.getResponseEntity(response);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}

			if (HttpUtils.isSuccessful(responseEntity)) {
				logger.info("/venue; status=successful");
				HttpHeaders headers = new HttpHeaders();
				headers.add("Content-Type", "application/json; charset=UTF-8");

				Schedule schedule = (Schedule) JsonUtils.unmarshal(responseEntity.getBody(), Schedule.class);
				return schedule;
			} else {
				logger.warn(responseEntity.getBody());
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return null;
	}
}
