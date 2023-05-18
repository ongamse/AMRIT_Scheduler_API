package com.iemr.tm.data.schedule;

import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Transient;

import com.google.gson.annotations.Expose;
import com.iemr.tm.utils.mapper.OutputMapper;

import lombok.Data;

@Data
public class SpecialistInput {

	@Expose
	private Date fromDate;

	@Expose
	private Date toDate;

	@Expose
	private Timestamp fromTime;

	@Expose
	private Timestamp toTime;


	@Transient
	private OutputMapper outputMapper = new OutputMapper();

	@Override
	public String toString() {
		return outputMapper.gson().toJson(this);
	}

}
