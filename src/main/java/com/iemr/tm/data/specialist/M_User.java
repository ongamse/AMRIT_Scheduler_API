package com.iemr.tm.data.specialist;

import javax.persistence.Transient;

import com.google.gson.annotations.Expose;
import com.iemr.tm.utils.mapper.OutputMapper;

import lombok.Data;

@Data
public class M_User {

	@Expose
	private Long userID;
	@Expose
	private Integer titleID;
	@Expose
	private String titleName;
	@Expose
	private String firstName;
	@Expose
	private String middleName;
	@Expose
	private String lastName;

	@Expose
	private String gender;
	@Expose
	private String specializationID;
	@Expose
	private String specialization;
	@Expose
	private String emailID;
	@Expose
	private String contactNumber;

	@Transient
	private OutputMapper outputMapper = new OutputMapper();

	@Override
	public String toString() {
		return outputMapper.gson().toJson(this);
	}

}
