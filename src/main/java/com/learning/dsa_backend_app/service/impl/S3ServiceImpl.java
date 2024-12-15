package com.learning.dsa_backend_app.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.learning.dsa_backend_app.dto.S3ObjectDTO;
import com.learning.dsa_backend_app.service.S3Service;

import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.HeadObjectRequest;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Response;
import software.amazon.awssdk.services.s3.model.S3Exception;

@Service
public class S3ServiceImpl implements S3Service {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private S3Client s3Client;

	@Value("${aws.bucket.name}")
	String bucketName;

	String className = getClass().getSimpleName();

	@Override
	@Cacheable(cacheNames = "folderContentCache", key = "#codesFolderFullPath")
	public List<S3ObjectDTO> getS3CodesFolderContent(String codesFolderFullPath) {

		logger.info("Entered {}: getS3CodesFolderContent method", className);
		logger.info("{}: codesFolderFullPath {}", className, codesFolderFullPath);

		ListObjectsV2Request request = ListObjectsV2Request.builder().bucket(bucketName).prefix(codesFolderFullPath) // Folder
				.build();

		ListObjectsV2Response response = s3Client.listObjectsV2(request);

		return response.contents().stream()
				.map(s3Object -> new S3ObjectDTO(s3Object.key(), s3Object.size(), s3Object.lastModified()))
				.collect(Collectors.toList());
	}

	@Override
	public boolean doesPathExist(String path) {
		try {
			// Make a HeadObjectRequest for the given bucket and path
			HeadObjectRequest headObjectRequest = HeadObjectRequest.builder().bucket(bucketName).key(path).build();

			// If the object exists, this will not throw an exception
			s3Client.headObject(headObjectRequest);
			return true; // The path exists
		} catch (S3Exception e) {
			e.printStackTrace();
			if (e.statusCode() == 404) {
				return false; // Path does not exist
			}
			return false;
		}
	}

	@Override
	public boolean doesFolderExist(String folderPath) {

		try {
			// Build the request
			ListObjectsV2Request request = ListObjectsV2Request.builder().bucket(bucketName).prefix(folderPath) // Folder
																												// path
					.maxKeys(1) // Check for at most one object
					.build();

			// Execute the request
			ListObjectsV2Response response = s3Client.listObjectsV2(request);

			// If any objects are found, the folder exists
			return !response.contents().isEmpty();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	@Cacheable(cacheNames = "fileContentCache", key = "#fileFullPath")
	public String getS3FileContent(String fileFullPath) {
		
		logger.info("Entered {}: getS3FileContent method", className);
		logger.info("{}: fileFullPath {}", className, fileFullPath);
		
		try {
			// Build a GetObjectRequest for the specific file
			GetObjectRequest getObjectRequest = GetObjectRequest.builder().bucket(bucketName).key(fileFullPath).build();

			// Retrieve the object as an InputStream
			ResponseInputStream<?> responseInputStream = s3Client.getObject(getObjectRequest);

			// Convert InputStream to String
			try (BufferedReader reader = new BufferedReader(new InputStreamReader(responseInputStream))) {
				return reader.lines().collect(Collectors.joining("\n"));
			} catch (IOException e) {
				e.printStackTrace();
				logger.info("Failed to read file content: " + e.getMessage());
			}
		} catch (S3Exception e) {
			e.printStackTrace();
			logger.info("Failed to fetch file content: " + e.getMessage());
		}
		return "";
	}

}
