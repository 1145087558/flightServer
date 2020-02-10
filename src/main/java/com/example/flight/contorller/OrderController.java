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
	public int placeOrder(FlightTicket flightTicket,int cabinId) {
		//User user = (User)request.getSession().getAttribute("user");
		User user = new User().setId(1);
		flightTicket = service.placeOrder(flightTicket,cabinId,user.getId());

		return flightTicket.getId();
	}

	@GetMapping("searchOrder.action/{orderId}")
	public Map<String,Object> searchOrder(@PathVariable int orderId){

		Map<String,Object> result = new HashMap<>();
		result.put("flightInfo",service.getTicketById(orderId));
		return result;
	}
	
	@GetMapping("zifubaoPay.action/{ticketId}")
	public void zifubaoPay(@PathVariable int ticketId,HttpServletResponse response) throws IOException {
		service.pay(ticketId,response);
	}
	
	@GetMapping("paysuccess.action/{ticketId}")
	public void paySuccess(@PathVariable int ticketId) {
		service.paySuccess(ticketId);
	}

	@GetMapping("searchTicketStatus.action/{ticketId}")
	public String searchTicketStatus(@PathVariable int ticketId) {

		FlightTicket flightTicket = service.getTicketById(ticketId);
		return flightTicket.getOrderStatus();
	}

	@GetMapping("orderList.action")
	public Map<String,Object> orderList(HttpServletRequest request) {
		//User user = (User)request.getSession().getAttribute("user");
		User user = new User().setId(1);
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
	public void alipayRefundRequest(@PathVariable("out_trade_no") String out_trade_no,@PathVariable("refund_amount") double refund_amount) {
		service.alipayRefundRequest(out_trade_no,"", refund_amount);
	}


	
}
