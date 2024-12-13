package com.learning.dsa_backend_app.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.learning.dsa_backend_app.response.ContentResponse;
import com.learning.dsa_backend_app.service.S3ContentService;

import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Response;
import software.amazon.awssdk.services.s3.model.S3Object;

@Service
public class S3ContentServiceImpl implements S3ContentService {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private S3Client s3Client;

	@Value("${aws.bucket.name}")
	String bucketName;

	@Value("${aws.codes-folder.prefix}")
	String prefixFolderName;

	@Override
	public ContentResponse getContent(String path) {
		List<S3Object> codesContent = getCodesContent();
		logger.info("codes folder data {}", codesContent);
		return new ContentResponse();
	}

	@Override
	@Cacheable(value = "codesContent", key = "'codes'")
	public List<S3Object> getCodesContent() {
		
		logger.info("Entered getCodesContent method");
		String codesPrefix = prefixFolderName;
		logger.info("codesPrefix {}", codesPrefix);

		ListObjectsV2Request request = ListObjectsV2Request.builder().bucket(bucketName).prefix(codesPrefix) // Folder
				.build();

		ListObjectsV2Response response = s3Client.listObjectsV2(request);

		return response.contents(); // Returns subfiles and objects
	}

	@Override
	public ContentResponse getFileContent(String path) {
		// TODO Auto-generated method stub
		return null;
	}

}
