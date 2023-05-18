package com.iemr.tm.utils.mapper;

import java.time.LocalTime;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class OutputMapper {
	static GsonBuilder builder;

	public OutputMapper() {
		if (builder == null) {
			builder = new GsonBuilder();
			builder.setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
			builder.excludeFieldsWithoutExposeAnnotation();
			builder.registerTypeAdapter(LocalTime.class, new LocalTimeAdapter());
			builder.serializeNulls();
		}
	}

	public static Gson gson() {
		return builder.create();
	}
}