package com.example.flight.converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import org.springframework.format.Formatter;

public class DateFormatter implements Formatter<Date>{

	private final SimpleDateFormat smf = new SimpleDateFormat("yyyy-MM-dd");
	
	
	@Override
	public String print(Date object, Locale locale) {
		if(object == null)
			return null;
		return smf.format(object);
	}

	@Override
	public Date parse(String text, Locale locale) throws ParseException {
		Date target = null;
		if ("".equals(text) || null == text) {
			return null;
		}
		try {
			target = smf.parse(text);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return target;
	}

}
