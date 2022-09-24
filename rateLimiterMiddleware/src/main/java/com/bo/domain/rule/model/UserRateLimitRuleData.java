package com.bo.domain.rule.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class UserRateLimitRuleData {

	private Long ratePerSec;
	private Long ratePerMin;
	
	
	public UserRateLimitRuleData() {
		 
	}

	public UserRateLimitRuleData(Long ratePerSec, Long ratePerMin) {
		this.ratePerSec = ratePerSec;
		this.ratePerMin = ratePerMin;
	}
	
	public Long getRatePerSec() {
		return ratePerSec;
	}
	public void setRatePerSec(Long ratePerSec) {
		this.ratePerSec = ratePerSec;
	}
	public Long getRatePerMin() {
		return ratePerMin;
	}
	public void setRatePerMin(Long ratePerMin) {
		this.ratePerMin = ratePerMin;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
        builder.append("{\"ratePerSec\" :");
        builder.append(ratePerSec);
        builder.append(", \"ratePerMin\" :");
        builder.append(ratePerMin);
        builder.append("}");
    return builder.toString();
	}
}
