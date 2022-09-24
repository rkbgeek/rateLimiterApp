package com.bo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bo.service.ApiUserRateLimitService;

@RestController
public class ApiUserRateLimitController {


	@Autowired 
	ApiUserRateLimitService apiUserRateLimitService;

	@RequestMapping(value = "/{apiId}/{userId}", method = RequestMethod.GET)
	public ResponseEntity<String> checkLimitForUser(@PathVariable("apiId") String apiId,@PathVariable("userId") String userId) { 
		boolean isAllowed = apiUserRateLimitService.checkIfLimitExceedForApi(apiId,userId);
		
		if(isAllowed) {
			return new ResponseEntity<>("Request is allowed", HttpStatus.OK); 
		}
		return new ResponseEntity<>("Too many request for the user", HttpStatus.OK);
	}


}
