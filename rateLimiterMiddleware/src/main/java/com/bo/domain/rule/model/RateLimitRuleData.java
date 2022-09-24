package com.bo.domain.rule.model;

import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document("rate_limit_rule")
public class RateLimitRuleData {

	@Id
	private String id;
	public RateLimitRuleData() {
	}
	

	public RateLimitRuleData(String id, Map<String, Map<String, UserApiRateLimit>> apiUserDataMap) {
		super();
		this.id = id;
		this.apiUserDataMap = apiUserDataMap;
	}


	private Map<String,Map<String,UserApiRateLimit>> apiUserDataMap;

	public Map<String,Map<String,UserApiRateLimit>> getApiUserDataMap() {
		return apiUserDataMap;
	}


}
