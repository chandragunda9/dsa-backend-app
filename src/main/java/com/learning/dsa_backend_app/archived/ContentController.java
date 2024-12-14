package com.learning.dsa_backend_app.archived;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.learning.dsa_backend_app.response.ContentResponse;

//@RestController
public class ContentController {

	@Autowired
	ContentProviderService contentProviderService;

	Logger logger = LoggerFactory.getLogger(getClass());

	@GetMapping("/fetch/content")
	ContentResponse getFolderContent(@RequestParam String path) {
		logger.info("Request Path is: {}", path);
		ContentResponse content = contentProviderService.getContent(path);
		return content;
	}

	@GetMapping("/")
	public String welcome() {
		return "Welcome to DSA Repository";
	}

}
