package com.bo.service;

import com.bo.domain.rule.model.UserApiRateLimit;

public interface RateLimitRuleFetchService {

	UserApiRateLimit getUserRateLimitData(String userId, String apiId);
}
