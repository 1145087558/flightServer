package com.example.flight.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.flight.entity.FlightInfo;

@Mapper
public interface FlightInfoDao extends BaseMapper<FlightInfo>{

	public List<FlightInfo> searchList();
}
