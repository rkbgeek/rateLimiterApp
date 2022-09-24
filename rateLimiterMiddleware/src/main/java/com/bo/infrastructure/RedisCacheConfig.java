package com.bo.infrastructure;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class RedisCacheConfig {

 	@Value("${redis.host}")
	private String redisHost;

	@Value("${redis.port}")
	private Integer redisPort;

	@Value("${redis.timeout}")
	private Integer redisTimeout;
	
	@Value("${redis.maximumActiveConnectionCount}")
	private Integer redisMaximumActiveConnectionCount;

	@Bean
	public JedisPool jedisPool() {
		JedisPoolConfig poolConfig = new JedisPoolConfig();
		poolConfig.setMaxTotal(redisMaximumActiveConnectionCount);
		JedisPool jedisPool = new JedisPool(poolConfig, redisHost, redisPort, redisTimeout);
		return jedisPool;
	}
}
