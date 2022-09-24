package com.bo.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.bo.service.cache.UserApiLimitterDataLru;
import com.bo.domain.rule.model.UserApiRateLimit;
import com.bo.domain.rule.model.UserRateLimitRuleData;
import com.bo.service.cache.RateLimitRedisCacheService;

@Service
public class RateLimitRuleFetchServiceImpl  implements RateLimitRuleFetchService{


	@Autowired
	RateLimitRedisCacheService rateLimitRedisCacheService;

	@Autowired
	MongoTemplate mongoTemplate;
	static Map<String,UserApiRateLimit> userApiRateLimitMap = new HashMap<>();

	@Override
	public UserApiRateLimit getUserRateLimitData(String userId, String apiId) {

		String userRateLimitKey = getKey(userId,apiId);
		UserApiLimitterDataLru userApiRulelru = new UserApiLimitterDataLru();
		//String isCacheEnable = System.getProperty("enableSystemCatch");

		UserApiRateLimit userApiRateLimit = userApiRulelru.getElementFromCache(userRateLimitKey);
		if(userApiRateLimit != null) {
			return userApiRateLimit;
		}
		UserRateLimitRuleData userApiRateLimitData = rateLimitRedisCacheService.getApiUserRateLimitRule(userId, apiId);
		return fetchAndstoreDataInMap(userApiRateLimitData,userId,apiId,userApiRulelru);

	}

	private UserApiRateLimit fetchAndstoreDataInMap(UserRateLimitRuleData userApiRateLimitData,String userId,String apiId, UserApiLimitterDataLru lru) {
		UserApiRateLimit userApiRateLimit = null;
		String userRateLimitKey = getKey(userId,apiId);
		userApiRateLimit = new UserApiRateLimit(userApiRateLimitData.getRatePerSec(),userApiRateLimitData.getRatePerMin());
		lru.putElementInCache(userRateLimitKey, userApiRateLimit);
		return userApiRateLimit;
	}

	private String getKey(String userId, String apiName) {
		StringBuilder key = new StringBuilder();
		key.append(userId);
		key.append("_");
		key.append(apiName);
		return key.toString();
	}


}
