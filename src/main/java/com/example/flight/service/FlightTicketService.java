package com.example.flight.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.example.flight.entity.FlightTicket;

public interface FlightTicketService {

	public FlightTicket placeOrder(FlightTicket flightTicket,int cabinId,int userId);

	public FlightTicket getTicketById(int id);

	public void pay(int ticketId, HttpServletResponse response) throws IOException;

	public void paySuccess(int flightId);

	public  void alipayRefundRequest(String out_trade_no,String trade_no,double refund_amount);

	public List<FlightTicket> getTicketByUserId(Integer userId);

	public void deletOrder(int ticketId);

}
