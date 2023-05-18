package com.iemr.tm.data.schedule;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.google.gson.annotations.Expose;
import com.iemr.tm.utils.StringListConverter;
import com.iemr.tm.utils.mapper.OutputMapper;

import lombok.Data;

@Data
@Entity
@Table(name = "t_SpecialistAvailabilityDetail")
public class SpecialistAvailabilityDetail {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Expose
	@Column(name = "SpecialistAvailabilityDetailID")
	private Long specialistAvailabilityDetailID;

	@Expose
	@Column(name = "UserID")
	private Long userID;

	@Temporal(TemporalType.DATE)
	@Expose
	@Column(name = "ConfiguredFromDate")
	private Date configuredFromDate;

	@Temporal(TemporalType.DATE)
	@Expose
	@Column(name = "ConfiguredToDate")
	private Date configuredToDate;

	@Expose
	@Column(name = "ConfiguredFromTime")
	private Timestamp configuredFromTime;

	@Expose
	@Column(name = "ConfiguredToTime")
	private Timestamp configuredToTime;

	@Expose
	@Column(name = "IsAvailability")
	private Boolean isAvailability;
	
	@Expose
//	@Transient
	@Column(name = "Excluded_Days")
	@Convert(converter = StringListConverter.class)
	private List<Integer> ExcludeDays;

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
	


	@Transient
	private OutputMapper outputMapper = new OutputMapper();

	@Override
	public String toString() {
		return outputMapper.gson().toJson(this);
	}

}
