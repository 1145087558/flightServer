package com.example.flight.service;

import java.util.Date;
import java.util.List;

import com.example.flight.model.flight.FlightCabin;
import com.example.flight.model.flight.FlightData;
import com.example.flight.model.flight.FlightInfo;

public interface FlightInfoService {

	public List<FlightInfo> index();
	
	public List<String> getDateToWeek(Date date);

	public List<String> getWeekString(Date date);

	public List<FlightData> searchByWeek(String week);

	public FlightData searchById(int flightId);

	public FlightCabin searchByCabinId(int cabinId);

	public List<FlightData> searchList(FlightInfo info, FlightData data);
}
