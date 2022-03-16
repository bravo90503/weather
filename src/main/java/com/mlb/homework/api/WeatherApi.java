package com.mlb.homework.api;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mlb.homework.utils.HttpUtils;

@RestController
@RequestMapping("/weather")
public class WeatherApi {
	private static Logger logger = LoggerFactory.getLogger(WeatherApi.class);
	@Value("${mlb.stats.host}")
	public String mlbStatsHost;
	@Value("${weather.api.host}")
	public String weatherApiHost;

	@GetMapping("/api/v1/points")
	public ResponseEntity<String> points(@RequestParam(value = "latitude") int latitude,
			@RequestParam(value = "longitude") int longitude) {
		ResponseEntity<String> responseEntity = null;
		try {
			StringBuilder path = new StringBuilder(weatherApiHost)//
					.append("/points/") //
					.append(latitude).append(",").append(longitude);

			final HttpGet request = new HttpGet(path.toString());
			request.addHeader("User-Agent", "mlbhomework/1.0.0");
			request.addHeader("Accept", "*/*");
			// execute request
			CloseableHttpResponse response = HttpUtils.client.execute(request);

			responseEntity = HttpUtils.getResponseEntity(response);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		if (HttpUtils.isSuccessful(responseEntity)) {
			logger.info("/points; status=successful");
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Type", "application/json; charset=UTF-8");
			return new ResponseEntity<String>(responseEntity.getBody(), headers, responseEntity.getStatusCode());
		}

		return responseEntity;
	}

	@GetMapping("/api/v1/forecast")
	public ResponseEntity<String> forecast(@RequestParam String gridId, @RequestParam(value = "x") int gridX,
			@RequestParam(value = "y") int gridY) {
		ResponseEntity<String> responseEntity = null;
		try {
			StringBuilder path = new StringBuilder(weatherApiHost)//
					.append("/gridpoints/").append(gridId).append("/")//
					.append(gridX).append(",").append(gridY)//
					.append("/forecast");

			final HttpGet request = new HttpGet(path.toString());
			request.addHeader("User-Agent", "mlbhomework/1.0.0");
			request.addHeader("Accept", "*/*");
			// execute request
			CloseableHttpResponse response = HttpUtils.client.execute(request);

			responseEntity = HttpUtils.getResponseEntity(response);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		if (HttpUtils.isSuccessful(responseEntity)) {
			logger.info("/forecast; status=successful");
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Type", "application/json; charset=UTF-8");
			return new ResponseEntity<String>(responseEntity.getBody(), headers, responseEntity.getStatusCode());
		}

		return responseEntity;
	}

}
