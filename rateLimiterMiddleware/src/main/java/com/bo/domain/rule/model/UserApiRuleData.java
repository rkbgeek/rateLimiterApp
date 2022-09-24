package com.bo.domain.rule.model;

import java.util.Map;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class UserApiRuleData {
	private Map<String,UserRateLimitRuleData> map ;

	public Map<String, UserRateLimitRuleData> getMap() {
		return map;
	}

	public void setMap(Map<String, UserRateLimitRuleData> map) {
		this.map = map;
	}

	public UserApiRuleData() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserApiRuleData(Map<String, UserRateLimitRuleData> map) {
		super();
		this.map = map;
	}
	
	
}
