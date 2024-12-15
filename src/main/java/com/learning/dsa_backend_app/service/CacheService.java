package com.learning.dsa_backend_app.service;

public interface CacheService {

	public void clearFullCache(String cacheName);

	public void clearSpecificKeyInCache(String cacheName, String cacheKey);
}
