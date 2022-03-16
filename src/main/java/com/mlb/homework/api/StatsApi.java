package com.mlb.homework.api;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mlb.homework.utils.HttpUtils;

@RestController
@RequestMapping("/stats")
public class StatsApi {
	private static Logger logger = LoggerFactory.getLogger(StatsApi.class);
	@Value("${mlb.stats.host}")
	public String mlbStatsHost;
	@Value("${weather.api.host}")
	public String weatherApiHost;

	@GetMapping("/api/v1/venues/{id}")
	public ResponseEntity<String> venue(@PathVariable(value = "id") int id) {
		ResponseEntity<String> responseEntity = null;
		try {
			StringBuilder path = new StringBuilder(mlbStatsHost)//
					.append("/api/v1/venues/")//
					.append(id).append("?hydrate=location");

			final HttpGet request = new HttpGet(path.toString());
			// execute request
			CloseableHttpResponse response = HttpUtils.client.execute(request);

			responseEntity = HttpUtils.getResponseEntity(response);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		if (HttpUtils.isSuccessful(responseEntity)) {
			logger.info("/venues; status=successful");
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Type", "application/json; charset=UTF-8");
			return new ResponseEntity<String>(responseEntity.getBody(), headers, responseEntity.getStatusCode());
		}

		return responseEntity;
	}

	@GetMapping("/api/v1/schedule")
	public ResponseEntity<String> schedule(@RequestParam String scheduleTypes, @RequestParam String sportIds,
			@RequestParam String teamIds, @RequestParam String startDate, @RequestParam String endDate) {
		ResponseEntity<String> responseEntity = null;
		try {
			StringBuilder path = new StringBuilder(mlbStatsHost)//
					.append("/api/v1/schedule?")//
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
			logger.info("/schedule; status=successful");
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Type", "application/json; charset=UTF-8");
			return new ResponseEntity<String>(responseEntity.getBody(), headers, responseEntity.getStatusCode());
		}

		return responseEntity;
	}

}
