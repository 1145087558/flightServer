package com.example.flight.model.flight;

import java.io.Serializable;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain=true)
@TableName(value="flight_data")
public class FlightData implements Serializable{
	 
	private static final long serialVersionUID = 1L;

	@TableId(value="id",type=IdType.AUTO)
	private Integer id;

	@TableField(value="day_time")
	private Date dayTime;

	@TableField(value="start_time")
	@JsonFormat(pattern="yyyy-MM-dd hh:mm:ss",timezone = "GMT+8")
	private Date startTime;

	@TableField(value="end_time")
	@JsonFormat(pattern="yyyy-MM-dd hh:mm:ss",timezone = "GMT+8")
	private Date endTime;

	@TableField(value="departure")
	private String departure;

	@TableField(value="destination")
	private String destination;

	@TableField(value="flight_id")
	private String flightId;

	@TableField(exist = false)
	private FlightInfo flightInfo;

}
