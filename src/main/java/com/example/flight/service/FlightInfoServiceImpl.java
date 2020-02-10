package com.example.flight.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.flight.dao.FlightCabinDao;
import com.example.flight.dao.FlightDataDao;
import com.example.flight.dao.FlightInfoDao;
import com.example.flight.entity.FlightCabin;
import com.example.flight.entity.FlightData;
import com.example.flight.entity.FlightInfo;

@Service
public class FlightInfoServiceImpl implements FlightInfoService {

	@Autowired
	private FlightInfoDao dao;

	@Autowired
	private FlightCabinDao cabinDao;

	@Autowired
	private FlightDataDao dataDao;

	@Override
	public List<FlightInfo> index() {
		QueryWrapper<FlightInfo> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("day_time", new Date());
		queryWrapper.eq("departure", "上海");
		queryWrapper.eq("destination", "北京");
		List<FlightInfo> infolist = dao.selectList(queryWrapper);
		if (infolist != null) {
			QueryWrapper<FlightCabin> queryWrapper2;
			for (int i = 0; i < infolist.size(); i++) {
				queryWrapper2 = new QueryWrapper<>();
				queryWrapper2.eq("flight_id", infolist.get(i).getFlightNumber());
				infolist.get(i).setCabins(cabinDao.selectList(queryWrapper2));
			}
		}
		return infolist;
	}

	@Override
	public List<FlightData> searchByWeek(String week) {
		week = "2020" + week;
		SimpleDateFormat format = new SimpleDateFormat("yyyyMM月dd日");
		Date date = null;
		try {
			date = format.parse(week);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		QueryWrapper<FlightData> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("day_time", date);
		queryWrapper.orderByAsc("start_time");
		List<FlightData> infolist = dataDao.selectList(queryWrapper);
		if (infolist != null) {
			QueryWrapper<FlightInfo> queryWrapper2;
			QueryWrapper<FlightCabin> queryWrapper3;
			for (int i = 0; i < infolist.size(); i++) {
				queryWrapper2 = new QueryWrapper<>();
				queryWrapper2.eq("flight_number", infolist.get(i).getFlightId());
				infolist.get(i).setFlightInfo(dao.selectOne(queryWrapper2));
				queryWrapper3 = new QueryWrapper<>();
				queryWrapper3.eq("flight_id", infolist.get(i).getFlightId());
				infolist.get(i).getFlightInfo().setCabins(cabinDao.selectList(queryWrapper3));
			}
		}
		return infolist;
	}

	@Override
	public FlightData searchById(int flightId) {
		FlightData infolist = dataDao.selectById(flightId);
		if (infolist != null) {
			QueryWrapper<FlightInfo> queryWrapper2;
			queryWrapper2 = new QueryWrapper<>();
			queryWrapper2.eq("flight_number", infolist.getFlightId());
			infolist.setFlightInfo(dao.selectOne(queryWrapper2));
			QueryWrapper<FlightCabin> queryWrapper3 = new QueryWrapper<>();
			queryWrapper3.eq("flight_id", infolist.getFlightInfo().getFlightNumber());
			infolist.getFlightInfo().setCabins(cabinDao.selectList(queryWrapper3));
		}
		return infolist;
	}

	@Override
	public FlightCabin searchByCabinId(int cabinId) {

		FlightCabin cabin = cabinDao.selectById(cabinId);
		return cabin;
	}

	@Override
	public List<String> getDateToWeek(java.util.Date date) {
		List<String> dateWeekList = new ArrayList<String>();
		SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
		String time = "";
		for (int i = 0; i < 7; i++) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			// 根据循环。当天与上周星期天和本周一到周五相差的天数
			cal.add(Calendar.DATE, i);
			// 转化格式
			time = sdf.format(cal.getTime());
			// 存入list
			dateWeekList.add(time);
		}
		return dateWeekList;
	}

	@Override
	public List<String> getWeekString(java.util.Date date) {
		List<String> dateWeekList = new ArrayList<String>();
		String[] weekDays = { "周日", "周一", "周二", "周三", "周四", "周五", "周六" };
		int flag = 0;
		for (int i = 0; i < 7; i++) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			flag = cal.get(Calendar.DAY_OF_WEEK);
			int w = (flag + i - 1) % 7;
			dateWeekList.add(weekDays[w]);
		}
		return dateWeekList;
	}

	@Override
	public List<FlightData> searchList(FlightInfo info, FlightData data) {
		SimpleDateFormat smf = new SimpleDateFormat("yyyy-MM-dd");
		QueryWrapper<FlightData> queryWrapper1 = new QueryWrapper<>();
		QueryWrapper<FlightInfo> queryWrapper2 = null;
		QueryWrapper<FlightCabin> queryWrapper3 = null;
		//可以使用date_format格式化数据库日期只比较年月日，还可以使用Date函数返回日期部分
		queryWrapper1.apply("date_format(start_time,'%Y-%m-%d') = '"+smf.format(data.getStartTime())+"'");
		queryWrapper1.apply("date_format(end_time,'%Y-%m-%d') = '"+smf.format(data.getEndTime())+"'");
		System.out.println(data);
		queryWrapper1.eq("departure", data.getDeparture());
		queryWrapper1.eq("destination", data.getDestination());
		List<FlightData> flightDatas = dataDao.selectList(queryWrapper1);
		System.out.println(flightDatas);
		List<FlightData> datas = new LinkedList<>();
		for (int i = 0; i < flightDatas.size(); i++) {
			queryWrapper2 = new QueryWrapper<>();
			queryWrapper2.eq("flight_number", flightDatas.get(i).getFlightId());
			FlightInfo flightInfo = dao.selectOne(queryWrapper2);
			
			if(info.getAirline().equals("全部航司")||flightInfo.getAirline().equals(info.getAirline())) {
				if(flightInfo.getRangeType().equals(info.getRangeType())) {
					queryWrapper3 = new QueryWrapper<>();
					queryWrapper3.eq("flight_id", flightInfo.getFlightNumber());
					flightInfo.setCabins(cabinDao.selectList(queryWrapper3));
					flightDatas.get(i).setFlightInfo(flightInfo);
					datas.add(flightDatas.get(i));
				}
			}	
		}
		return datas;
	}

	//在原来基础上增加一个月
	@SuppressWarnings("unused")
	private Date subMonth(Date date) {
		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(date);
		rightNow.add(Calendar.MONTH, 1);
		return rightNow.getTime();
	}

	// 在原来日期上增减一天(减为-1)
	@SuppressWarnings("unused")
	private Date subDay(Date date,int day){
		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(date);
		rightNow.add(Calendar.DAY_OF_MONTH, day);
		System.out.println(rightNow.getTime());
		return rightNow.getTime();
	}

}
