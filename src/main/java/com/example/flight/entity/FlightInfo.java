package com.example.flight.entity;

import java.io.Serializable;
import java.util.List;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain=true)
@TableName(value="flight_info")
public class FlightInfo implements Serializable{
	 
	private static final long serialVersionUID = 1L;
	@TableId(value="flight_number",type=IdType.INPUT)
	private String flightNumber;
	@TableField(value="range_type")
	private String rangeType;
	@TableField(value="model_type")
	private String modelType;
	@TableField(value="airline")
	private String airline;
	@TableField(exist = false)
	private List<FlightCabin> cabins;
	

}
