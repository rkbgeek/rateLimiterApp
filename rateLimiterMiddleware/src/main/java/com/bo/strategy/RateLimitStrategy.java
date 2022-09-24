package com.bo.strategy;

import com.bo.domain.rule.model.UserApiRateLimit;

public interface RateLimitStrategy {

      boolean checkIfRateLimitExceed(String apiId, String userId, UserApiRateLimit userApiRateLimit);
}
