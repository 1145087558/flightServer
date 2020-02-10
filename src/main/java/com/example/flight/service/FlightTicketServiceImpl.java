package com.example.flight.service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeRefundResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.flight.dao.FlightCabinDao;
import com.example.flight.dao.FlightPeopleDao;
import com.example.flight.dao.FlightTicketDao;
import com.example.flight.entity.FlightCabin;
import com.example.flight.entity.FlightPeople;
import com.example.flight.entity.FlightTicket;
import com.example.flight.util.AlipayConfig;

@Service
public class FlightTicketServiceImpl implements FlightTicketService{

	@Autowired
	private FlightTicketDao dao;
	
	@Autowired
	private FlightPeopleDao peopelDao;
	
	@Autowired
	private FlightCabinDao cabinDao;

	@Override
	public FlightTicket placeOrder(FlightTicket flightTicket,int cabinId,int userId) {
		flightTicket.setOrderStatus("未支付");
		flightTicket.setPersonNumber(flightTicket.getFlightPeoples().size());
		flightTicket.setUserId(userId);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
        String orderSn = simpleDateFormat.format(date.getTime());
        flightTicket.setOrderNumber(orderSn);
        flightTicket.setOrderTime(date);
		dao.insert(flightTicket);
		FlightPeople flightPeople =null;
		FlightCabin cabin = cabinDao.selectById(cabinId);
		for(int i=0;i<flightTicket.getFlightPeoples().size();i++) {
			flightPeople = flightTicket.getFlightPeoples().get(i);
			flightPeople.setTicketId(flightTicket.getId());
			String type = flightPeople.getType();
			if(type.equals("成人")) {
				flightPeople.setPrice(cabin.getAdultPrice());
			}else if(type.equals("儿童")) {
				flightPeople.setPrice(cabin.getChildrenPrice());
			}else if(type.equals("婴儿")) {
				flightPeople.setPrice(cabin.getInfantPrice());
			}
			flightPeople.setInsurancePrice(cabin.getInsurancePrice());
			peopelDao.insert(flightPeople);
		}
		cabin.setSeatNumber(cabin.getSeatNumber()-flightTicket.getFlightPeoples().size());
		cabinDao.updateById(cabin);
		return flightTicket;
	}

	@Override
	public FlightTicket getTicketById(int id) {
		FlightTicket flightTicket = dao.selectById(id);
		QueryWrapper<FlightPeople> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("ticket_id",id);
		flightTicket.setFlightPeoples(peopelDao.selectList(queryWrapper));
		return flightTicket;
	}

	@Override
	public void pay(int ticketId, HttpServletResponse response) throws IOException {

		FlightTicket ticket = dao.selectById(ticketId);
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String orderSn = ticket.getOrderNumber();
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id,
                AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key,
                AlipayConfig.sign_type);
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl("http://localhost:8080/paysuccess.action/"+ticket.getId());
        alipayRequest.setNotifyUrl(AlipayConfig.notify_url);
        String body = ticket.getDeparture()+"--- "+ticket.getDestination()+"\r\n"+
        simpleDateFormat.format(ticket.getStartTime())+"---"+simpleDateFormat.format(ticket.getEndTime());
        alipayRequest.setBizContent("{\"out_trade_no\":\"" + orderSn + "\"," + "\"total_amount\":\""
                + ticket.getPrice() + "\"," + "\"subject\":\"" + "支付宝支付" + "\"," + "\"body\":\"" + body + "\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
        AlipayTradePagePayResponse alipayResponse = null;
        try {
            alipayResponse=alipayClient.pageExecute(alipayRequest);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
		response.setContentType("text/html;charset=UTF-8");
        response.getWriter().write(alipayResponse.getBody());
	}

	@Override
	public void paySuccess(int flightId) {
		FlightTicket ticket = dao.selectById(flightId);
		ticket.setOrderStatus("已支付");
		dao.updateById(ticket);
	}

	@Override
	public void alipayRefundRequest(String out_trade_no, String trade_no, double refund_amount) {

		//out_trade_no 订单支付时传入的商户订单号,不能和支付宝交易号（trade_no）同时为空
		//out_request_no 标识一次退款请求,如需部分退款,则此参数必传

		String out_request_no="HZ01RF001";
		try {
			AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id,
					AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key,
					AlipayConfig.sign_type);
			AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
			request.setBizContent("{" +
					"\"out_trade_no\":\"" + out_trade_no + "\"," +
					"\"trade_no\":\"" + trade_no + "\"," +
					"\"refund_amount\":\"" + refund_amount + "\"," +

					"\"out_request_no\":\"" + out_request_no+ "\"," +
					"\"refund_reason\":\"正常退款\"" +
					" }");
			AlipayTradeRefundResponse response;
			response = alipayClient.execute(request);
			if (response.isSuccess()) {
				System.out.println("支付宝退款成功");
			} else {
				//response.getSubMsg();//失败会返回错误信息
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<FlightTicket> getTicketByUserId(Integer userId) {
		QueryWrapper<FlightTicket> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("user_id", userId);
		List<FlightTicket> flights = dao.selectList(queryWrapper);
		QueryWrapper<FlightPeople> queryWrapper2=null;
		for (int i = 0; i < flights.size(); i++) {
			queryWrapper2 = new QueryWrapper<>();
			queryWrapper2.eq("ticket_id", flights.get(i).getId());
			flights.get(i).setFlightPeoples(peopelDao.selectList(queryWrapper2));
		}
		return flights;
	}

	@Override
	public void deletOrder(int ticketId) {
		dao.deleteById(ticketId);
	}
}
