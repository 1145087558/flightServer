package com.example.flight.dao;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.flight.model.order.FlightTicket;

@Mapper
public interface FlightTicketDao extends BaseMapper<FlightTicket>{

}
