package com.bo.domain.rule.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.bo.domain.rule.model.RateLimitRuleData;

@Repository
public interface ApiRateLimitRuleRepository extends MongoRepository<RateLimitRuleData, String> {
	

}
