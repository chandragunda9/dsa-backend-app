package com.learning.dsa_backend_app.service;

import java.util.List;

import com.learning.dsa_backend_app.dto.S3ObjectDTO;

public interface S3Service {

	public List<S3ObjectDTO> getS3CodesFolderContent(String codes);

	public String getS3FileContent(String fullPath);

	public boolean doesPathExist(String path);

	public boolean doesFolderExist(String folderPath);

}
