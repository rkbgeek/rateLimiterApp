package com.bo.service.cache;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import com.bo.domain.rule.model.UserApiRateLimit;


public class UserApiLimitterDataLru {  
	static Deque<String> queue = new LinkedList<>();   
	static Map<String, LruCache> map = new HashMap<>();  
	int CACHE_CAPACITY =500;  
	public UserApiRateLimit getElementFromCache(String key)   
	{  
		if(map.containsKey(key))   
		{  
			LruCache current = map.get(key);  
			queue.remove(current.key);  
			queue.addFirst(current.key);  
			return current.userApiRateLimit;  
		}   
		return null;  
	}  
	public void putElementInCache(String key, UserApiRateLimit value)   
	{  
		if(map.containsKey(key))   
		{  
			LruCache curr = map.get(key);      
			queue.remove(curr.key);                   
		}  
		else   
		{  
			if(queue.size() == CACHE_CAPACITY)   
			{  
				String temp = queue.removeLast();    
				map.remove(temp);  
			}  
		}  
		LruCache newItem = new LruCache(key, value);  
		queue.addFirst(newItem.key);     
		map.put(key, newItem);  
	}
	public static void clearQueue() {
		UserApiLimitterDataLru.queue.clear();
	}
	public static void clearMap() {
		UserApiLimitterDataLru.map.clear();
	}
 
	
}