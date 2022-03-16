package com.mlb.homework.response.model.mlb;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Location {
	private  String city;
	private  String state;
	private  Coordinates defaultCoordinates;

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Coordinates getDefaultCoordinates() {
		return defaultCoordinates;
	}

	public void setDefaultCoordinates(Coordinates defaultCoordinates) {
		this.defaultCoordinates = defaultCoordinates;
	}

}
