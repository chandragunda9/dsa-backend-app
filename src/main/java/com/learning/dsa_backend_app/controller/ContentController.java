package com.learning.dsa_backend_app.controller;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.learning.dsa_backend_app.response.ContentResponse;
import com.learning.dsa_backend_app.service.ContentService;

@RestController
public class ContentController {

	@Autowired
	ContentService contentService;

	Logger logger = LoggerFactory.getLogger(getClass());

	@GetMapping("/fetch/content")
	public ContentResponse getContent(@RequestParam String path) {
		logger.info("Request Path is: {}", path);
		if (StringUtils.isBlank(path) || !path.startsWith("/")) {
			return new ContentResponse();
		}

		ContentResponse content = null;
		if (path.endsWith("/")) {
			content = contentService.getFolderContent(path);
		} else {
			content = contentService.getFileContent(path);
		}
		return content;
	}

	@GetMapping("/")
	public String welcome() {
		return "Welcome to DSA Repository";
	}

}
