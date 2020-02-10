package com.example.flight.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@Accessors(chain=true)
@TableName(value="flight_ticket")
public class FlightTicket implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@TableId(value="id",type=IdType.AUTO)
	private Integer id;
	@TableField(value="order_number")
	private String orderNumber;
	@TableField(value="person_name")
	private String personName;
	@TableField(value="person_phone")
	private String personPhone;
	@TableField(value="person_number")
	private int personNumber;
	@TableField(value="shipping_space")
	private String shippingSpace;
	@TableField(value="airline")
	private String airline;
	@TableField(value="departure")
	private String departure;
	@TableField(value="destination")
	private String destination;
	@TableField(value="start_time")
	@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date startTime;
	@TableField(value="end_time")
	@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date endTime;
	@TableField(value="price")
	private String price;
	@TableField(value="order_time")
	private Date orderTime;
	@TableField(value="order_status")
	private String orderStatus;
	@TableField(value="flight_number")
	private String flightNumber;
	@TableField(value="user_id")
	private Integer userId;
	@TableField(exist = false)
	private List<FlightPeople> flightPeoples;
}
