package com.bo.strategy;


import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bo.common.RateLimitConstant;
import com.bo.domain.rule.model.TokenBucket;
import com.bo.domain.rule.model.UserApiRateLimit;
import com.bo.util.RateLimitUtils;
import com.bo.service.cache.RateLimitRedisCacheService;

@Service
public class TokenBucketStrategy implements RateLimitStrategy {

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
		
		Long curruntWindowInMillies = RateLimitUtils.getRoundedInCurruntUnitMillis(RateLimitConstant.MINUTE);
        TokenBucket existingTokenBucket = rateLimitRedisCacheService.fetchAvailableTokensFromBucket(RateLimitUtils.generateKey(RateLimitConstant.MINUTE_BUCKET, apiId, userId),userApiRateLimit,RateLimitConstant.MINUTE);

        if(Objects.equals(curruntWindowInMillies, existingTokenBucket.getWindowStartingTime()) ) {
        	Long availableToken =existingTokenBucket.getBucketAvailableCount();
        	if(availableToken >0) {
        		rateLimitRedisCacheService.setTokenBucketInCache(RateLimitUtils.generateKey(RateLimitConstant.MINUTE_BUCKET, apiId, userId),availableToken-1,RateLimitConstant.MINUTE);
        		return true;
        	}else {
        		return false;
        	}
        }else{
        	rateLimitRedisCacheService.setTokenBucketInCache(RateLimitUtils.generateKey(RateLimitConstant.MINUTE_BUCKET, apiId, userId), userApiRateLimit.getLimitPerminute()-1, RateLimitConstant.MINUTE);
            return true;
        }
         
	}

	 
}
