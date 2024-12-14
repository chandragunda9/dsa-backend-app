package com.learning.dsa_backend_app.service;

import com.learning.dsa_backend_app.response.ContentResponse;

public interface ContentService {

	public ContentResponse getFolderContent(String path);

	public ContentResponse getFileContent(String path);

}
