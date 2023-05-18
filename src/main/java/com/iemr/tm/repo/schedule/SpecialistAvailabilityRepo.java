package com.iemr.tm.repo.schedule;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.iemr.tm.data.schedule.SpecialistAvailability;

@Repository
@RestResource(exported = false)
public interface SpecialistAvailabilityRepo extends CrudRepository<SpecialistAvailability, Integer> {

	List<SpecialistAvailability> findByConfiguredDateBetweenAndUserID(Date configuredFromDate, Date configuredToDate,
			Long userID);

	SpecialistAvailability findOneByConfiguredDateAndUserID(Date configuredFromDate, Long userID);

	@Query("select u from SpecialistAvailability u "
			+ "where u.userID=:userID "
			+ "and (year(u.configuredDate)=:year1) "
			+ "and (:month1 is null or :month1=0 or month(  u.configuredDate)=:month1) "
			+ "and (:day1 is null or :day1=0 or day(u.configuredDate)=:day1 ) ")
	List<SpecialistAvailability> findByMonthAndUserID(@Param("day1")Integer day,@Param("month1")Integer month, @Param("year1")Integer year, @Param("userID")Long userID);

	List<SpecialistAvailability> findByConfiguredDateAndUserIDIn(Date date, List<Long> userids);

}
