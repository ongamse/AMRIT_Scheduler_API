package com.iemr.tm.repo.van;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.iemr.tm.data.van.M_Van;

@Repository
@RestResource(exported = false)
public interface VanRepo extends JpaRepository<M_Van, Integer> {

}
