package com.bo.service;



public interface ApiUserRateLimitService {
	
	boolean checkIfLimitExceedForApi(String apiId,String userId);

}
