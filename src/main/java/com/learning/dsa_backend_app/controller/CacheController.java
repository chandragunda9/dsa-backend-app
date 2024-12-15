package com.learning.dsa_backend_app.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.learning.dsa_backend_app.service.CacheService;

@RestController
public class CacheController {

	@Autowired
	private CacheService cacheService;

	@DeleteMapping("/clear-cache")
	public ResponseEntity<Object> clearCache(@RequestParam String cacheName, @RequestParam String cacheKey) {

		if (StringUtils.isBlank(cacheName)) {
			return ResponseEntity.badRequest().build();
		}

		if (!StringUtils.isBlank(cacheKey)) {
			cacheService.clearSpecificKeyInCache(cacheName, cacheKey);
		} else {
			cacheService.clearFullCache(cacheName);
		}

		return ResponseEntity.ok().build();
	}

}
