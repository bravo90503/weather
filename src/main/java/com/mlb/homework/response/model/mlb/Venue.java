package com.mlb.homework.response.model.mlb;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mlb.homework.response.model.weather.Forecast;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Venue {
	private  String formId;
	private  int id;
	private  String name;
	private  Location location;
	private  Forecast forecast;
	private  String forecastDescription;

	@NotNull
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Forecast getForecast() {
		return forecast;
	}

	public void setForecast(Forecast forecast) {
		this.forecast = forecast;
	}

	public String getForecastDescription() {
		return forecastDescription;
	}

	public void setForecastDescription(String forecastDescription) {
		this.forecastDescription = forecastDescription;
	}

	public String getFormId() {
		return formId;
	}

	public void setFormId(String formId) {
		this.formId = formId;
	}

	
}
