package com.mlb.homework.web;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Schedules;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.mlb.homework.api.StatsApi;
import com.mlb.homework.response.model.mlb.Coordinates;
import com.mlb.homework.response.model.mlb.Game;
import com.mlb.homework.response.model.mlb.GameDate;
import com.mlb.homework.response.model.mlb.Schedule;
import com.mlb.homework.response.model.mlb.Venue;
import com.mlb.homework.response.model.weather.Forecast;
import com.mlb.homework.response.model.weather.ForecastProps;
import com.mlb.homework.response.model.weather.Period;
import com.mlb.homework.response.model.weather.Points;
import com.mlb.homework.response.model.weather.PointsProps;
import com.mlb.homework.service.StatsService;
import com.mlb.homework.service.WeatherService;
import com.mlb.homework.utils.TimeUtils;

@Controller
public class SimpleController {
	private static Logger logger = LoggerFactory.getLogger(SimpleController.class);
	@Autowired
	StatsService statsService;
	@Autowired
	WeatherService weatherService;
	@Value("#{new Boolean('${current.forecast.enabled}')}")
	Boolean currentForecastEnabled;

	@GetMapping("/")
	public String query(Venue venue, com.mlb.homework.web.form.Schedule schedule) {
		venue.setFormId("680");
		schedule.setTeamIds("121");
		schedule.setStartDate("2022-04-07");
		return "home";
	}

	@PostMapping(value = "/findvenue")
	public String findVenue(Venue form, Model model) {
		if (StringUtils.isNumeric(form.getFormId())) {
			int id = Integer.parseInt(form.getFormId());
			Venue venue = statsService.getVenue(id);
			if (venue != null) {
				model.addAttribute("venue", venue);
				Coordinates c = venue.getLocation().getDefaultCoordinates();
				Points points = weatherService.getPoints(c.getLatitude(), c.getLongitude());
				if (points != null) {
					PointsProps p = points.getProperties();
					Forecast f = weatherService.getForecast(p.getGridId(), p.getGridX(), p.getGridY());
					if (f != null) {
						model.addAttribute("forecast", f.getProperties().getPeriods().get(0));
					}
				}
			} else {
				model.addAttribute("venue", null);
				model.addAttribute("venueID", form.getFormId());
			}
		} else {
			model.addAttribute("venue", null);
			model.addAttribute("venueID", form.getFormId());
		}

		return "display-venue";
	}

	@PostMapping(value = "/findschedule")
	public String findSchedule(com.mlb.homework.web.form.Schedule form, Model model) {
		Schedule schedule = null;
		try {
			TimeUtils.parseToISOLocalDate(form.getStartDate());
			form.setEndDate(form.getStartDate());
			schedule = statsService.getSchedule(form.getTeamIds(), form.getStartDate(), form.getEndDate());
		} catch (Exception e) {
			logger.warn(e.getMessage());
			model.addAttribute("invalidDate", form.getStartDate());
		}

		if (schedule != null) {
			for (GameDate gameDate : schedule.getGameDates()) {
				for (Game game : gameDate.getGames()) {
					Venue v = statsService.getVenue(game.getVenue().getId());
					Coordinates c = v.getLocation().getDefaultCoordinates();
					Points points = weatherService.getPoints(c.getLatitude(), c.getLongitude());
					PointsProps p = points.getProperties();
					if (p != null) {
						Forecast f = weatherService.getForecast(p.getGridId(), p.getGridX(), p.getGridY());
						if (f != null) {
							v.setForecast(f);
							ForecastProps props = f.getProperties();
							if (currentForecastEnabled) {
								v.setForecastDescription(props.getPeriods().get(0).getDetailedForecast());
							} else {
								LocalDate isoLocalDate = TimeUtils.parseToISOLocalDate(form.getStartDate());
								LocalDateTime isoLocalDateTime = isoLocalDate.atStartOfDay();
								// set n/a, overridden if one of the periods below is within scope
								v.setForecastDescription("Not available. Date given beyond forecast periods.");
								for (Period period : props.getPeriods()) {
									Date pStartTime = period.getStartTime();
									LocalDateTime pStartLocalDateTime = TimeUtils
											.convertDateToLocalDateTime(pStartTime);
									Date endDateTime = period.getStartTime();
									LocalDateTime pEndLocalDateTime = TimeUtils.convertDateToLocalDateTime(endDateTime);

									if (isoLocalDateTime.isAfter(pStartLocalDateTime)
											&& isoLocalDateTime.isBefore(pEndLocalDateTime)) {
										v.setForecastDescription(period.getDetailedForecast());
										break;
									}
								}
							}
						} else {
							v.setForecastDescription("Not available. Unsupported Region or Unexpected Problem occured.");
						}
					}
					game.setVenue(v);

				}
			}
			model.addAttribute("schedule", schedule);
		} else {
			model.addAttribute("schedule", null);
		}
		return "display-schedule";
	}

	@GetMapping(value = "/about")
	public String about() {
		return "about";
	}
}
