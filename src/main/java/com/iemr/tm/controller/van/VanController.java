package com.iemr.tm.controller.van;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.iemr.tm.data.van.M_Van;
import com.iemr.tm.service.van.VanService;
import com.iemr.tm.utils.response.OutputResponse;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/van", headers = "Authorization")
public class VanController {
	private Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	@Autowired
	private VanService vanService;

	@CrossOrigin()
	@ApiOperation(value = "fetch all specialization", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = "getvan/{vanid}", method = RequestMethod.GET)
	public String markavailability(@PathVariable("vanid") Integer vanid) {
		OutputResponse response = new OutputResponse();
		try {
			M_Van van = vanService.getvan(vanid);
			if (van == null) {
				van = new M_Van();
			}
			response.setResponse(van.toString());

		} catch (Exception e) {
			logger.error("exception occured while fetching specialization", e);
			response.setError(e);
		}
		return response.toString();
	}

}
