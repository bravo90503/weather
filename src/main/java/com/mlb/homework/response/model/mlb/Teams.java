package com.mlb.homework.response.model.mlb;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Teams {
	private  Away away;
	private  Home home;
	public Away getAway() {
		return away;
	}
	public void setAway(Away away) {
		this.away = away;
	}
	public Home getHome() {
		return home;
	}
	public void setHome(Home home) {
		this.home = home;
	}
	
	

}
