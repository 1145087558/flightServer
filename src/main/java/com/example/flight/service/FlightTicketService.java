package com.example.flight.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.flight.model.order.FlightTicket;

public interface FlightTicketService {

	public FlightTicket placeOrder(FlightTicket flightTicket,int cabinId,int userId);

	public FlightTicket getTicketById(int id);

	public void pay(int ticketId, HttpServletRequest request, HttpServletResponse response) throws IOException;

	public void paySuccess(HttpServletRequest request);

	public  String alipayRefundRequest(String out_trade_no,String trade_no,double refund_amount);

	public List<FlightTicket> getTicketByUserId(Integer userId);

	public void deletOrder(int ticketId);

}
