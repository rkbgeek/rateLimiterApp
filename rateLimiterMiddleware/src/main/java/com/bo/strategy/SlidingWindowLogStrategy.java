package com.bo.strategy;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bo.common.RateLimitConstant;
import com.bo.domain.rule.model.UserApiRateLimit;
import com.bo.util.RateLimitUtils;
import com.bo.service.cache.RateLimitRedisCacheService;

@Service
public class SlidingWindowLogStrategy implements RateLimitStrategy {

	@Autowired
	RateLimitRedisCacheService rateLimitRedisCacheService;
	
	@Override
	public boolean checkIfRateLimitExceed(String apiId, String userId,UserApiRateLimit userApiRateLimit) {
		
		if(!checkRequestBasedOnMinute(apiId, userId, userApiRateLimit)) {
			return false;
		}
		return true;
	}

	private boolean checkRequestBasedOnMinute(String apiId, String userId,UserApiRateLimit userApiRateLimit) {
		
		boolean ifRequestAllowed = false;
        List<String> requestList = rateLimitRedisCacheService.fetchLogRequestFromCache(RateLimitUtils.generateKey(RateLimitConstant.REQUEST, apiId, userId),userApiRateLimit.getLimitPerminute());
        if(requestList.size() >0) {
        	 ifRequestAllowed = checkIfRequestExceedsCount(requestList,userApiRateLimit.getLimitPerminute(),RateLimitConstant.MINUTE);
        }else {
        	return true;
        }
        return ifRequestAllowed;
	}

	private boolean checkIfRequestExceedsCount(List<String> requestList,Long limit,String unit) {
		Long curruntTimeInMillies = RateLimitUtils.getCurrentInMillies();
		Long windowStartMillies = RateLimitUtils.getWindowStartMillies(unit);
		List<String> requestInCurrentFrame =  requestList.stream().filter(req-> Long.valueOf(req) >windowStartMillies && Long.valueOf(req)< curruntTimeInMillies).toList();
		if(requestInCurrentFrame.size() <limit ) {
			return true;
		}
	   return false;
	   
	   
	  
	   
	}

}
