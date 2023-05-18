package com.iemr.tm.data.schedule;

import java.time.LocalTime;

import javax.persistence.Transient;

import com.google.gson.annotations.Expose;
import com.iemr.tm.utils.mapper.OutputMapper;

import lombok.Data;

@Data
public class Slot {
	
	@Expose
	private LocalTime fromTime;

	
	@Expose
	private LocalTime toTime;
	
	@Expose
	private String slot;
	
	@Expose
	private String status;

	@Transient
	private OutputMapper outputMapper = new OutputMapper();

	@Override
	public String toString() {
		return outputMapper.gson().toJson(this);
	}

	public Slot(){
		
	}

	public Slot(int i, Integer size, String slot,String actual,String booked) {
		// TODO Auto-generated constructor stub
		fromTime=LocalTime.ofSecondOfDay(i*5*60L); 
		Integer y=(i+size)*5*60;
		if(y>=86400){
			y=86399;
		}
		toTime=LocalTime.ofSecondOfDay(y); 
		this.slot=slot;
		
		if(slot.equals(actual)){
			this.status="Available";
		}else if(slot.equals(booked)){
			this.status="Booked";
		}else{
			this.status="Unavailable";
		}
	}

	public Slot(int start, int end, String group) {
		// TODO Auto-generated constructor stub
		fromTime=LocalTime.ofSecondOfDay(start*5*60L); 
		Integer y=end*5*60;
		if(y>=86400){
			y=86399;
		}
		toTime=LocalTime.ofSecondOfDay(y); 
		this.slot=group;
		char slod=group.charAt(0);
		if(slod=='A'){
			this.status="Available";
		}else if(slod=='B'){
			this.status="Booked";
		}else{
			this.status="Unavailable";
		}
		
	}

}
