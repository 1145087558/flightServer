package com.example.flight.model.flight;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain=true)
@TableName("flight_cabin")
public class FlightCabin implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@TableId(type=IdType.AUTO)
	private Integer id;
	@TableField(value="shipping_space")
	private String shippingSpace;
	@TableField(value="adult_price")
	private double adultPrice;
	@TableField(value="children_price")
	private double childrenPrice;
	@TableField(value="infant_price")
	private double infantPrice;
	@TableField(value="insurance_price")
	private double insurancePrice;
	@TableField(value="discount")
	private double discount;
	@TableField(value="seat_number")
	private Integer seatNumber;
	@TableField(value="flight_id")
	private String flightId;
	
}
