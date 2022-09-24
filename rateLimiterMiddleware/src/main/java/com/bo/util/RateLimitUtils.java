package com.bo.util;

import java.sql.Timestamp;
import java.time.Instant;

public class RateLimitUtils {

	public static final String KEY_SEPERATOR = "_";

	public static String generateKey(String type,String firstAppender, String secondAppender) {

		StringBuilder keyBuilder = new StringBuilder();
		if(type != null) {
			keyBuilder.append(type);
			keyBuilder.append(KEY_SEPERATOR);
		}
		if(firstAppender != null) {
			keyBuilder.append(firstAppender);
			keyBuilder.append(KEY_SEPERATOR);
		}
		if(secondAppender != null) {
			keyBuilder.append(secondAppender);
		}

		return keyBuilder.toString();

	}

	
	public static String generateKey(String type, String appender) {

		StringBuilder keyBuilder = new StringBuilder();
		if(type != null) {
			keyBuilder.append(type);
			keyBuilder.append(KEY_SEPERATOR);
		}
		 
		if(appender != null) {
			keyBuilder.append(appender);
		}

		return keyBuilder.toString();

	}

	public static Long getRoundedInCurruntUnitMillis(String unit) {
		if("minute".equalsIgnoreCase(unit)) {
			Long currentTime= Timestamp.from(Instant.now()).getTime(); 
			return (currentTime /60000) *60000;
		}else if("second".equalsIgnoreCase(unit)) {
			Long currentTime= Timestamp.from(Instant.now()).getTime(); 
			return (currentTime /1000) *1000;
		}
		return null;
	}

	public static Long getCurrentInMillies() {

		return Timestamp.from(Instant.now()).getTime(); 
	}


	public static Long getWindowStartMillies(String unit) {
		if("minute".equalsIgnoreCase(unit)) {
			Long currentTime= Timestamp.from(Instant.now()).getTime(); 
			return currentTime -60000;
		}else if("second".equalsIgnoreCase(unit)) {
			Long currentTime= Timestamp.from(Instant.now()).getTime(); 
			return currentTime -1000;
		}
		return null;
	}


	public static String getCollectionName(String apiId) {
		StringBuilder keyBuilder = new StringBuilder();
		keyBuilder.append("api");
		keyBuilder.append(KEY_SEPERATOR);
		keyBuilder.append(apiId);
		keyBuilder.append(KEY_SEPERATOR);
		keyBuilder.append("rule");
		return keyBuilder.toString();
	}

}
