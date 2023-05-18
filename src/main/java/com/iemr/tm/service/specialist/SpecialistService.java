package com.iemr.tm.service.specialist;

import java.util.List;

import com.iemr.tm.data.specialist.M_User;
import com.iemr.tm.data.specialist.Specialist;
import com.iemr.tm.data.specialist.Specialization;
import com.iemr.tm.utils.exception.TMException;

public interface SpecialistService {

	List<Specialization> getspecialization();

	List<Specialist> getspecialistUser(Long providerservicemapID, Long specializationID, Long userID)
			throws TMException;

	M_User getinfo(Long userID);

	public List<Specialist> getAllSpecialist(Long providerservicemapID);

}
