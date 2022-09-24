package com.bo.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RateLimitStrategyFactory {

	@Autowired
	TokenBucketStrategy tokenBucketStrategy;

	@Autowired
	SlidingWindowLogStrategy slidingWindowLogStrategy;

	public  RateLimitStrategy getStrategy(String strategyName) {
    
		if("slidingWindowLog".equalsIgnoreCase(strategyName)) {
			return slidingWindowLogStrategy;
		}else {
			return tokenBucketStrategy;
		}

	}

}

