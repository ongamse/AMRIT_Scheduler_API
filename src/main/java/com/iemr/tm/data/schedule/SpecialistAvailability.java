package com.iemr.tm.data.schedule;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.google.gson.annotations.Expose;
import com.iemr.tm.utils.mapper.OutputMapper;

import lombok.Data;

@Data
@Entity
@Table(name = "t_SpecialistAvailability")
public class SpecialistAvailability {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Expose
	@Column(name = "SpecialistAvailabilityID")
	private Long specialistAvailabilityID;

	@Expose
	@Column(name = "UserID")
	private Long userID;

	@Temporal(TemporalType.DATE)
	@Expose
	@Column(name = "ConfiguredDate")
	private Date configuredDate;

	@Expose
	@Column(name = "TimeSlot")
	private String timeSlot;

	@Expose
	@Column(name = "Deleted",insertable=false,updatable=true)
	private Boolean deleted;

	@Expose
	@Column(name = "Processed",insertable=false,updatable=true)
	private String processed;

	@Expose
	@Column(name = "CreatedBy")
	private String createdBy;

	@Expose
	@Column(name = "CreatedDate",insertable=false,updatable=false)
	private Timestamp createdDate;

	@Expose
	@Column(name = "ModifiedBy")
	private String modifiedBy;

	@Expose
	@Column(name = "LastModDate",insertable=false,updatable=false)
	private Timestamp lastModDate;
	
	@Expose
	@Transient
	private List<Slot> slots;

	@Transient
	private OutputMapper outputMapper = new OutputMapper();

	@Override
	public String toString() {
		return outputMapper.gson().toJson(this);
	}

	public SpecialistAvailability(){}
	
	public SpecialistAvailability(Long userID2, Date date, ArrayList<Slot> arrayList) {
		// TODO Auto-generated constructor stub
		this.userID=userID2;
		this.configuredDate=date;
		this.slots=arrayList;
	}

}
