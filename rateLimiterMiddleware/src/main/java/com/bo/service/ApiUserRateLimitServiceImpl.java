package com.bo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bo.domain.rule.model.UserApiRateLimit;
import com.bo.service.cache.RateLimitRedisCacheService;
import com.bo.strategy.RateLimitStrategy;
import com.bo.strategy.RateLimitStrategyContext;
import com.bo.strategy.RateLimitStrategyFactory;

@Service
public class ApiUserRateLimitServiceImpl implements ApiUserRateLimitService {

	@Autowired
	RateLimitRuleFetchService rateLimitRuleFetchService;

	@Autowired
	RateLimitStrategyContext limitStrategyContext;
	
	@Autowired
	RateLimitRedisCacheService rateLimitRedisCacheService;
	
	@Autowired
	RateLimitStrategyFactory limitStrategyFactory;
	
	@Override
	public boolean checkIfLimitExceedForApi(String apiId, String userId) {
		UserApiRateLimit apiRateLimit = getRateLimitData(userId, apiId);
		setStrategyContext(limitStrategyContext);
		boolean isAllowed = limitStrategyContext.checkIfRateLimitExceed(apiId, userId,apiRateLimit);
		if(isAllowed) {
			rateLimitRedisCacheService.logRequestApiUserRequest(userId, apiId);
		}
		return isAllowed;
	}

	private UserApiRateLimit getRateLimitData(String userId, String apiId) {

		UserApiRateLimit userApiRateLimit= rateLimitRuleFetchService.getUserRateLimitData(userId, apiId);
		return userApiRateLimit;	
	}
	private void setStrategyContext(RateLimitStrategyContext rateLimitStrategyContext) {
		RateLimitStrategy rateLimitStrategy = limitStrategyFactory.getStrategy("tokenBucket");
		limitStrategyContext.setRateLimitStrategy(rateLimitStrategy);
	}
}
