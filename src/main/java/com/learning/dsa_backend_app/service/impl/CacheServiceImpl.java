package com.learning.dsa_backend_app.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import com.learning.dsa_backend_app.service.CacheService;

@Service
public class CacheServiceImpl implements CacheService {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private CacheManager cacheManager;

	@Override
	public void clearFullCache(String cacheName) {
		// clearing cache
		if (cacheManager.getCache(cacheName) != null) {
			cacheManager.getCache(cacheName).clear();
			logger.info("{} cleared", cacheName);
		}
	}

	@Override
	public void clearSpecificKeyInCache(String cacheName, String cacheKey) {
		// removing specific key in cache
		if (cacheManager.getCache(cacheName) != null) {
			cacheManager.getCache(cacheName).evict(cacheKey);
			logger.info("{} cleared in {}", cacheKey, cacheName);
		}
	}

}
