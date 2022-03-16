package com.mlb.homework.service;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.mlb.homework.response.model.weather.Forecast;
import com.mlb.homework.response.model.weather.Points;
import com.mlb.homework.utils.HttpUtils;
import com.mlb.homework.utils.JsonUtils;

@Service
public class WeatherService {
	private static Logger logger = LoggerFactory.getLogger(WeatherService.class);
	@Value("${microservice.host}")
	public String microserviceHost;
	@Value("${server.port}")
	public String serverPort;

	public Points getPoints(int latitude, int longitude) {
		try {
			ResponseEntity<String> responseEntity = null;
			StringBuilder path = new StringBuilder(microserviceHost + ":" + serverPort)//
					.append("/weather/api/v1/points?")//
					.append("latitude=").append(latitude).append("&")//
					.append("longitude=").append(longitude);

			final HttpGet request = new HttpGet(path.toString());
			// execute request
			CloseableHttpResponse response = HttpUtils.client.execute(request);

			responseEntity = HttpUtils.getResponseEntity(response);
			if (HttpUtils.isSuccessful(responseEntity)) {
				return (Points) JsonUtils.unmarshal(responseEntity.getBody(), Points.class);
			} else {
				logger.warn(responseEntity.getBody());
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}

		return null;
	}

	public Forecast getForecast(String gridId, int x, int y) {

		try {
			ResponseEntity<String> responseEntity = null;
			StringBuilder path = new StringBuilder(microserviceHost + ":" + serverPort)//
					.append("/weather/api/v1/forecast?")//
					.append("gridId=").append(gridId).append("&")//
					.append("x=").append(x).append("&")//
					.append("y=").append(y);

			final HttpGet request = new HttpGet(path.toString());
			// execute request
			CloseableHttpResponse response = HttpUtils.client.execute(request);

			responseEntity = HttpUtils.getResponseEntity(response);
			if (HttpUtils.isSuccessful(responseEntity)) {
				return (Forecast) JsonUtils.unmarshal(responseEntity.getBody(), Forecast.class);
			} else {
				logger.warn(responseEntity.getBody());
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}

		return null;
	}
}
