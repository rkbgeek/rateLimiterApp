package com.bo.dao;

import java.util.List;


import com.bo.domain.rule.model.RateLimitRuleData;
import com.bo.domain.rule.model.UserRateLimitRuleData;

public interface RateLimiterDataBaseService {

	UserRateLimitRuleData fetchRateLimitRulesData(String string, String userId);

}
