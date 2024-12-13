package com.learning.dsa_backend_app.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.learning.dsa_backend_app.response.ContentResponse;
import com.learning.dsa_backend_app.service.S3ContentService;

@RestController
public class S3ContentController {

	@Autowired
	S3ContentService s3ContentService;

	Logger logger = LoggerFactory.getLogger(getClass());

	@GetMapping("/fetch/content")
	ContentResponse getContent(@RequestParam String path) {
		logger.info("Request Path is: {}", path);
		ContentResponse content = s3ContentService.getContent(path);
		return content;
	}

	@GetMapping("/")
	public String welcome() {
		return "Welcome to DSA Repository";
	}

}
