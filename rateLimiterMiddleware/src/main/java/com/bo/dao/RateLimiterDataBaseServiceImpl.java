package com.bo.dao;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.bo.domain.rule.model.UserRateLimitRuleData;
import com.bo.domain.rule.repositories.ApiRateLimitRuleRepository;
import com.bo.util.RateLimitUtils;


@Repository
public class RateLimiterDataBaseServiceImpl implements RateLimiterDataBaseService{

	@Autowired
	MongoTemplate mongoTemplate;
	
	 
	@Autowired
	ApiRateLimitRuleRepository apiRateLimitRuleRepository;
	
	@Override
	public UserRateLimitRuleData fetchRateLimitRulesData(String apiId,String userId) {
		 Query query=new Query(Criteria.where("userId").is(userId));
		 String collectionName = RateLimitUtils.getCollectionName(apiId);
		 UserRateLimitRuleData limitRuleData =mongoTemplate.findOne(query, UserRateLimitRuleData.class,collectionName);
		 return limitRuleData;
	}
	 
}
