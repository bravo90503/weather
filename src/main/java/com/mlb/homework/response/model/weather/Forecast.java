package com.mlb.homework.response.model.weather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Forecast {
	private String id;
	private ForecastProps properties;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public ForecastProps getProperties() {
		return properties;
	}

	public void setProperties(ForecastProps properties) {
		this.properties = properties;
	}

}
