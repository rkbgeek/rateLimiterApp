package com.bo.rateLimiterMiddleware;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.bo.service.cache.UserApiLimitterDataLru;

@EnableWebMvc
@SpringBootApplication(scanBasePackages = {"com.bo"})
@EnableMongoRepositories({"com.bo.domain.rule.repositories"})
public class RateLimiterMiddlewareApplication {

	public static void main(String[] args) {
		SpringApplication.run(RateLimiterMiddlewareApplication.class, args);
		
		PeriodicSystemCacheResetWorker.startPeriodicSystemCacheWorker();
	}

}
