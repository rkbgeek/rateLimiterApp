package com.bo.service.cache;

import java.util.List;

import com.bo.domain.rule.model.TokenBucket;
import com.bo.domain.rule.model.UserApiRateLimit;
import com.bo.domain.rule.model.UserRateLimitRuleData;

public interface RateLimitRedisCacheService {
	
	public UserRateLimitRuleData getApiUserRateLimitRule(String userId, String apiId);
	
	public void logRequestApiUserRequest(String userId,String apiId);
	
	public List<String> fetchLogRequestFromCache(String key, Long limitPerminute);
	
	public TokenBucket fetchAvailableTokensFromBucket(String key, UserApiRateLimit userApiRateLimit, String string);

	public void setTokenBucketInCache(String key,Long tokenCount,String unit);

}
