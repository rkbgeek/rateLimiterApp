package com.bo.strategy;

import org.springframework.stereotype.Service;

import com.bo.domain.rule.model.UserApiRateLimit;

@Service
public class RateLimitStrategyContext {
	 
	RateLimitStrategy rateLimitStrategy;
	
	public void setRateLimitStrategy(RateLimitStrategy rateLimitStrategy) {
		this.rateLimitStrategy = rateLimitStrategy;
	}

	public boolean checkIfRateLimitExceed(String apiId, String userId,UserApiRateLimit userApiRateLimit ) {
		return rateLimitStrategy.checkIfRateLimitExceed(apiId, userId,userApiRateLimit);
	}

}
