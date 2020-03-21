package com.example.flight.dao;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.flight.model.flight.FlightPeople;

@Mapper
public interface FlightPeopleDao  extends BaseMapper<FlightPeople>{

}
