package com.iemr.tm.utils.mapper;

import java.lang.reflect.Type;
import java.time.LocalTime;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;


public class LocalTimeDeAdapter implements JsonDeserializer<LocalTime> {


	@Override
	public LocalTime deserialize(JsonElement arg0, Type arg1, JsonDeserializationContext arg2)
			throws JsonParseException {
		// TODO Auto-generated method stub
		String[]  str=arg0.getAsString().split(":");
		return LocalTime.of(Integer.parseInt(str[0]), Integer.parseInt(str[1])) ;
	}
	
	

//    public JsonElement serialize(LocalDate date, Type typeOfSrc, JsonSerializationContext context) {
//       // "yyyy-mm-dd"
//    }

}