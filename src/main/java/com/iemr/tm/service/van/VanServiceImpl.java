package com.iemr.tm.service.van;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iemr.tm.data.van.M_Van;
import com.iemr.tm.repo.van.VanRepo;

@Service
public class VanServiceImpl implements VanService {

	@Autowired
	VanRepo vanRepo;

	@Override
	public M_Van getvan(Integer id) {
		// TODO Auto-generated method stub		
		return vanRepo.findOne(id);
	}

	

}
