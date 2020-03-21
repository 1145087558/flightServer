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
@TableName(value="flight_people")
public class FlightPeople implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@TableId(value="id",type= IdType.AUTO)
	private Integer id;
	@TableField(value="user_realname")
	private String realName;
	@TableField(value="user_idcard")
	private String idCard;
	@TableField(value="user_type")
	private String type;
	@TableField(value="user_price")
	private double price;
	@TableField(value="insurance_price")
	private double insurancePrice;
	@TableField(value="ticket_id")
	private Integer ticketId;
	
}
