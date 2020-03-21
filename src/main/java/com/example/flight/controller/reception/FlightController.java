package com.example.flight.controller.reception;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.flight.model.flight.FlightInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.flight.model.flight.FlightData;
import com.example.flight.service.FlightInfoService;

@RestController
public class FlightController {

	@Autowired
	private FlightInfoService service;


	@PostMapping("getFlightInfo.action")
	public List<FlightData> getFlightInfo() {

		String week = service.getDateToWeek(new Date()).get(0);
		return service.searchByWeek(week);
	}

	@PostMapping("getDateWeek.action")
	public Map<String, Object> getDateWeek() {
		Map<String,Object> result = new HashMap<>();
		result.put("dateWeek",service.getDateToWeek(new Date()));
		result.put("weeks",service.getWeekString(new Date()));
		return result;
	}

	@PostMapping("getWeeks.action")
	public List<String> getWeeks() {
		return  service.getWeekString(new Date());
	}

	@GetMapping(value="searchByWeek.action/{week}")
	public Map<String, Object> searchByWeek(@PathVariable("week")String week) {

		Map<String,Object> result = new HashMap<>();
		result.put("dateWeek",service.getDateToWeek(new Date()));
		result.put("weeks",service.getWeekString(new Date()));
		result.put("flightData",service.searchByWeek(week));
		return result;
	}

	@RequestMapping("searchList.action")
	public Map<String, Object> searchList(FlightInfo info, FlightData data) {

		Map<String,Object> result = new HashMap<>();
		result.put("flightData",service.searchList(info,data));
		result.put("dateWeek",service.getDateToWeek(new Date()));
		result.put("weeks",service.getWeekString(new Date()));
		return result;
	}

	@GetMapping("subscribeInfo.action/{flightId}/{cabinId}")
	public Map<String, Object> subscribeInfo(@PathVariable("flightId")int flightId,@PathVariable("cabinId")int cabinId) {
		FlightData data = service.searchById(flightId);
		Map<String,Object> result = new HashMap<>();
		result.put("flight",data);
		result.put("cabin",service.searchByCabinId(cabinId));
		result.put("weeks",service.getWeekString(data.getDayTime()).get(0));
		return result;
	}


}
