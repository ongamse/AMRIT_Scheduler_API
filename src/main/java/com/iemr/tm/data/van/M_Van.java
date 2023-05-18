package com.iemr.tm.data.van;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.google.gson.annotations.Expose;
import com.iemr.tm.utils.mapper.OutputMapper;

import lombok.Data;

@Entity
@Table(name = "m_van")
@Data
public class M_Van {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Expose
	@Column(name = "VanID")
	private Integer vanID;

	@Expose
	@Column(name = "VanName")
	private String vanName;
	@Expose
	@Column(name = "VehicalNo")
	private String vehicalNo;
	@Expose
	@Column(name = "VanTypeID")
	private Integer vanTypeID;

	@Expose
	@Column(name = "SwymedDomain")
	private String swymedDomain;

	@Expose
	@Column(name = "SwymedID")
	private String swymedID;

	@Expose
	@Column(name = "SwymedEmailID")
	private String swymedEmail;

	@Expose
	@Transient
	String vanType;

	@Expose
	@Column(name = "ProviderServiceMapID")
	private Integer providerServiceMapID;

	@Expose
	@Transient
	private Integer serviceProviderID;

	@Expose
	@Column(name = "CountryID")
	private Integer countryID;

	@Expose
	@Transient
	String countryName;

	@Expose
	@Column(name = "StateID")
	private Integer stateID;

	@Expose
	@Transient
	String stateName;

	@Expose
	@Column(name = "ParkingPlaceID")
	private Integer parkingPlaceID;

	@Expose
	@Transient
	String parkingPlaceName;

	@Expose
	@Column(name = "IsFacility")
	private Boolean isFacility;

	@Expose
	@Transient
	private Integer districtBlockID;

	@Expose
	@Column(name = "FacilityID")
	private Integer facilityID;

	@Expose
	@Column(name = "Deleted", insertable = false, updatable = true)
	private Boolean deleted;
	@Expose
	@Column(name = "Processed", insertable = false, updatable = true)
	private String processed;
	@Expose
	@Column(name = "CreatedBy")
	private String createdBy;
	@Expose
	@Column(name = "CreatedDate", insertable = false, updatable = false)
	private Date createdDate;
	@Expose
	@Column(name = "ModifiedBy")
	private String modifiedBy;
	@Expose
	@Column(name = "LastModDate", insertable = false, updatable = false)
	private Date lastModDate;

	@Transient
	private OutputMapper outputMapper = new OutputMapper();

	@Override
	public String toString() {
		return outputMapper.gson().toJson(this);
	}

}
