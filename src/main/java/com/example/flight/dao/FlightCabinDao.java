package com.example.flight.dao;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.flight.entity.FlightCabin;

@Mapper
public interface FlightCabinDao extends BaseMapper<FlightCabin>{

}
