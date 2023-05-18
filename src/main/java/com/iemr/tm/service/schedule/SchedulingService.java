package com.iemr.tm.service.schedule;

import java.util.List;

import com.iemr.tm.data.schedule.SpecialistAvailability;
import com.iemr.tm.data.schedule.SpecialistAvailabilityDetail;
import com.iemr.tm.data.schedule.SpecialistInput2;
import com.iemr.tm.data.specialist.Specialist;
import com.iemr.tm.utils.exception.TMException;

public interface SchedulingService {

	SpecialistAvailabilityDetail markAvailability(SpecialistAvailabilityDetail specialistInput) throws TMException;

	SpecialistAvailabilityDetail markUnavailability(SpecialistAvailabilityDetail specialistInput) throws TMException;

	SpecialistAvailability fetchavailability(SpecialistInput2 specialistInput);

	String bookSlot(SpecialistInput2 specialistInput,char status) throws TMException;

	List<SpecialistAvailability> fetchmonthavailability(SpecialistInput2 specialistInput,Integer year,Integer month,Integer day);

	List<Specialist> fetchAllAvailability(SpecialistInput2 specialistInput) throws TMException;

}
