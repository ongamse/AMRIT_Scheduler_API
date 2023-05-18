package com.iemr.tm.repo.schedule;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.iemr.tm.data.schedule.SpecialistAvailabilityDetail;

@Repository	
@RestResource(exported = false)
public interface SpecialistAvailabilityDetailRepo  extends CrudRepository<SpecialistAvailabilityDetail, Integer> {

}
