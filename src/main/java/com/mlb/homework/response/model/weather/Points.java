package com.mlb.homework.response.model.weather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Points {
	private String id;
	private PointsProps properties;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public PointsProps getProperties() {
		return properties;
	}

	public void setProperties(PointsProps properties) {
		this.properties = properties;
	}

}
