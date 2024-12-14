package com.learning.dsa_backend_app.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.learning.dsa_backend_app.dto.S3ObjectDTO;
import com.learning.dsa_backend_app.response.ContentResponse;
import com.learning.dsa_backend_app.service.ContentService;
import com.learning.dsa_backend_app.service.S3Service;

@Service
public class ContentServiceImpl implements ContentService {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private S3Service s3Service;

	@Value("${aws.codes-folder.prefix}")
	private String codesFolderPrefix;

	private List<S3ObjectDTO> dsaCodesFolderData = null;

	public void initCodesFolderData() {
		dsaCodesFolderData = s3Service.getS3CodesFolderContent(codesFolderPrefix + "/");
	}

	@Override
	public ContentResponse getFolderContent(String inpPath) {

		logger.info("Entered getFolderContent method");
		logger.info("getFolderContent inpPath {}", inpPath);

		String fullPath = codesFolderPrefix + inpPath;

		if (!s3Service.doesFolderExist(fullPath)) {
			logger.info("getFolderContent fullPath {} does not exist", fullPath);
			return new ContentResponse();
		}

		logger.info("getFolderContent fullPath {} exists", fullPath);

		// initializing codes folder content
		initCodesFolderData();

		logger.info("dsaCodesFolderData {}", dsaCodesFolderData);

		// storing all paths
		List<String> keys = dsaCodesFolderData.stream().map(dto -> dto.getKey()).toList();
		logger.info("keys: {}", keys);

		// map response
		return getFolderContentResponse(keys, fullPath);
	}

	private ContentResponse getFolderContentResponse(List<String> keys, String basePath) {
		Set<String> folders = new HashSet<>();
		List<String> files = new ArrayList<>();

		keys.stream().forEach(path -> {
			if (path.length() > basePath.length() && path.contains(basePath)) {
				int startInd = basePath.length();
				int endInd = path.indexOf("/", basePath.length());

				if (endInd == -1) {
					// it is a file
					files.add(path.substring(startInd, path.length()));
				}
				if (startInd <= endInd) {
					// it is a folder
					folders.add(path.substring(startInd, endInd));
				}
			}
		});

		return new ContentResponse(folders.stream().sorted().toList(), files);
	}

	@Override
	public ContentResponse getFileContent(String inpPath) {
		logger.info("Entered getFileContent method");
		logger.info("getFileContent inpPath {}", inpPath);

		String fullPath = codesFolderPrefix + inpPath;

		if (!s3Service.doesPathExist(fullPath)) {
			logger.info("getFileContent fullPath {} does not exist", fullPath);
			return new ContentResponse();
		}

		logger.info("getFileContent fullPath {} exists", fullPath);

		return new ContentResponse(s3Service.getS3FileContent(fullPath));
	}

}
