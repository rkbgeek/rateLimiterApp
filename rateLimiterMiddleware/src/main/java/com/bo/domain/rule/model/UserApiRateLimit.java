package com.bo.domain.rule.model;


public class UserApiRateLimit {
	private Long limitPerSecond;
	private Long limitPerminute;


	public UserApiRateLimit(Long limitPerSecond, Long limitPerminute) {
		this.limitPerSecond = limitPerSecond;
		this.limitPerminute = limitPerminute;
	}

	public Long getLimitPerSecond() {
		return limitPerSecond;
	}

	public Long getLimitPerminute() {
		return limitPerminute;
	}



}
