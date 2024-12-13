package com.learning.dsa_backend_app.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.learning.dsa_backend_app.response.ContentResponse;

import software.amazon.awssdk.services.s3.model.S3Object;

@Service
public interface S3ContentService {

	public ContentResponse getContent(String path);

	public ContentResponse getFileContent(String path);

	public List<S3Object> getCodesContent();

}
