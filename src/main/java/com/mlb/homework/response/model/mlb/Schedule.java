package com.mlb.homework.response.model.mlb;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Schedule {
	private  int totalGames;
	@JsonProperty(value = "dates")
	private  List<GameDate> gameDates;

	public int getTotalGames() {
		return totalGames;
	}

	public void setTotalGames(int totalGames) {
		this.totalGames = totalGames;
	}

	public List<GameDate> getGameDates() {
		return gameDates;
	}

	public void setGameDates(List<GameDate> gameDates) {
		this.gameDates = gameDates;
	}



}
