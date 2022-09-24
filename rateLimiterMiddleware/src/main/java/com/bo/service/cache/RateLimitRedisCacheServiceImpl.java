package com.bo.service.cache;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bo.dao.RateLimiterDataBaseService;
import com.bo.domain.rule.model.TokenBucket;
import com.bo.domain.rule.model.UserApiRateLimit;
import com.bo.domain.rule.model.UserRateLimitRuleData;
import com.bo.util.RateLimitUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Service
public class RateLimitRedisCacheServiceImpl implements RateLimitRedisCacheService {

	@Autowired
	private JedisPool jedisPool;

	@Value("${redis.sessiondata.ttl}")
	private int sessiondataTTL;

	@Autowired
	RateLimiterDataBaseService rateLimiterDataBaseService;

	private Jedis acquireJedisInstance() {
		return jedisPool.getResource();
	}
	@Override
	public UserRateLimitRuleData getApiUserRateLimitRule(String userId, String apiId) {

		Jedis jedis = null;
		UserRateLimitRuleData userRateLimitRuleData = null;
		try {
			jedis = acquireJedisInstance();
			String key = RateLimitUtils.generateKey(apiId, userId);
			String apiUserRateLimitJson = jedis.get(key);
			if(apiUserRateLimitJson != null) {
				ObjectMapper mapper = new ObjectMapper();
				try {
					userRateLimitRuleData= mapper.readValue(apiUserRateLimitJson,UserRateLimitRuleData.class);
				} catch (JsonProcessingException e) {
					e.printStackTrace();
				}
			}else {
				userRateLimitRuleData=rateLimiterDataBaseService.fetchRateLimitRulesData(apiId,userId);
				jedis.set(key, userRateLimitRuleData.toString());
			}
		} catch (Exception e) {
			releaseJedisInstance(jedis);
			throw new RuntimeException(e);

		} finally {
			releaseJedisInstance(jedis);
		}



		return userRateLimitRuleData;
	}


	@Override
	public void logRequestApiUserRequest(String userId, String apiId) {
		Jedis jedis = null;
		try {
			jedis = acquireJedisInstance();
			Timestamp instant= Timestamp.from(Instant.now());  
			String key = RateLimitUtils.generateKey("request", apiId, userId);
			String timeString = String.valueOf(instant.getTime());
			jedis.lpush(key,timeString);
			jedis.expire(key, sessiondataTTL);
		} catch (Exception e) {
			releaseJedisInstance(jedis);
			throw new RuntimeException(e);

		} finally {
			releaseJedisInstance(jedis);
		}


	}
	@Override
	public List<String> fetchLogRequestFromCache(String key, Long range) {
		Jedis jedis = null;
		List<String> requestList = null;
		try {
			jedis = acquireJedisInstance();
			requestList = jedis.lrange(key, 0, range-1);
		} catch (Exception e) {
			releaseJedisInstance(jedis);
			throw new RuntimeException(e);

		} finally {
			releaseJedisInstance(jedis);
		}
		return requestList;
	}
	private void releaseJedisInstance(Jedis jedis) {

		if (jedis != null) {
			jedis.close();
			jedis = null;
		}
	}
	@Override
	public TokenBucket fetchAvailableTokensFromBucket(String key, UserApiRateLimit userApiRateLimit, String unit) {

		Jedis jedis = null;
		TokenBucket tokenBucket = null;
		try {
			jedis = acquireJedisInstance();
			String token = jedis.get(key);
			if(token != null) {
				ObjectMapper mapper = new ObjectMapper();
				try {
					tokenBucket= mapper.readValue(token, new TypeReference<TokenBucket>(){});
				} catch (JsonProcessingException e) {
					e.printStackTrace();
				}
			}else {
				setTokenBucketInCache(key,RateLimitUtils.getRoundedInCurruntUnitMillis(unit),unit);
				tokenBucket = new TokenBucket(RateLimitUtils.getRoundedInCurruntUnitMillis(unit), userApiRateLimit.getLimitPerminute());
			}
		} catch (Exception e) {
			releaseJedisInstance(jedis);
			throw new RuntimeException(e);

		} finally {
			releaseJedisInstance(jedis);
		}
		return tokenBucket;
	}

	@Override
	public void setTokenBucketInCache(String key,Long tokenCount,String unit ) {
		Jedis jedis = null;
		TokenBucket tokenBucket = null;
		try {
			jedis = acquireJedisInstance();
			Long curruntUnitRoundedMillies = RateLimitUtils.getRoundedInCurruntUnitMillis(unit);
			tokenBucket = new TokenBucket(curruntUnitRoundedMillies,tokenCount);
			jedis.set(key, tokenBucket.toString());
			jedis.expire(key, sessiondataTTL);
		} catch (Exception e) {
			releaseJedisInstance(jedis);
			throw new RuntimeException(e);

		} finally {
			releaseJedisInstance(jedis);
		}
	}

}
