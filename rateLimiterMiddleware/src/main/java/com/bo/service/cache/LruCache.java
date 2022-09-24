package com.bo.service.cache;


import com.bo.domain.rule.model.UserApiRateLimit;  
class LruCache   
{  
	String key;               
	UserApiRateLimit userApiRateLimit;           
	LruCache(String key, UserApiRateLimit userApiRateLimit) {  
		this.key = key;  
		this.userApiRateLimit = userApiRateLimit;  
	}  
}  