package com.iemr.tm.utils.mapper;

import java.lang.reflect.Type;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;


public class LocalTimeAdapter implements JsonSerializer<LocalTime> {

	@Override
	public JsonElement serialize(LocalTime time, Type arg1, JsonSerializationContext arg2) {
		// TODO Auto-generated method stub
		return new JsonPrimitive(time.format(DateTimeFormatter. ISO_TIME));
		
	}
	
	

//    public JsonElement serialize(LocalDate date, Type typeOfSrc, JsonSerializationContext context) {
//       // "yyyy-mm-dd"
//    }

}