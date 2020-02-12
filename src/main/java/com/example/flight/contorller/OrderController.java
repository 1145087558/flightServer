package com.example.flight.contorller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.example.flight.entity.FlightData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.flight.entity.FlightTicket;
import com.example.flight.entity.User;
import com.example.flight.service.FlightTicketService;

@RestController
public class OrderController {

	@Autowired
	private FlightTicketService service;
	
	
	@PostMapping("placeOrder.action")
	public int placeOrder(FlightTicket flightTicket,int cabinId,HttpServletRequest request) {
		User user = (User)request.getSession().getAttribute("user");
		flightTicket = service.placeOrder(flightTicket,cabinId,user.getId());
		return flightTicket.getId();
	}

	@GetMapping("searchOrder.action/ticketId/{ticketId}")
	public Map<String,Object> searchOrder(@PathVariable int ticketId){

		Map<String,Object> result = new HashMap<>();
		result.put("flightInfo",service.getTicketById(ticketId));
		return result;
	}
	
	@GetMapping("zifubaoPay.action/{ticketId}")
	public void zifubaoPay(@PathVariable int ticketId,HttpServletRequest request,HttpServletResponse response) throws IOException {
		service.pay(ticketId,request,response);
	}
	
	@PutMapping("paysuccess.action")
	public void paySuccess(HttpServletRequest request) {
		service.paySuccess(request);
	}

	@GetMapping("searchTicketStatus.action/{ticketId}")
	public String searchTicketStatus(@PathVariable int ticketId) {

		FlightTicket flightTicket = service.getTicketById(ticketId);
		return flightTicket.getOrderStatus();
	}

	@GetMapping("orderList.action")
	public Map<String,Object> orderList(HttpServletRequest request) {
		User user = (User)request.getSession().getAttribute("user");
		Map<String,Object> result = new HashMap<>();
		result.put("flightInfo",service.getTicketByUserId(user.getId()));
		return result;
	}
	
	@DeleteMapping("deleteOrder.action/{ticketId}")
	public void deleteOrder(@PathVariable int ticketId) {
		System.out.println(ticketId);
		//service.deletOrder(ticketId);
	}

	@PutMapping("alipayRefundRequest.action/out_trade_no/{out_trade_no}/refund_amount/{refund_amount}")
	public String alipayRefundRequest(@PathVariable("out_trade_no") String out_trade_no,@PathVariable("refund_amount") double refund_amount) {
		System.out.println(out_trade_no);
		return service.alipayRefundRequest(out_trade_no,"", refund_amount);
	}


	
}
