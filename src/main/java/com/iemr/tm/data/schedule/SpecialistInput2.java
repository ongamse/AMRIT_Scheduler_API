package com.iemr.tm.data.schedule;

import java.time.LocalTime;
import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.google.gson.annotations.Expose;
import com.iemr.tm.utils.mapper.OutputMapper;

import lombok.Data;

@Data
public class SpecialistInput2 {

	@Expose
	private Long userID;

	@Temporal(TemporalType.DATE)
	@Expose
	private Date date;
	
	@Expose
	private Long providerServiceMapID;
	
	@Expose
	private String modifiedBy;
	
	@Expose
	private Long specializationID;
	
	@Expose
	private LocalTime fromTime;
	
	@Expose
	private LocalTime toTime;
	
	@Expose
	private Integer duration;
	
	@Expose
	private Integer month;

	@Transient
	private OutputMapper outputMapper = new OutputMapper();

	@Override
	public String toString() {
		return outputMapper.gson().toJson(this);
	}

}
