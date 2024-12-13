package com.learning.dsa_backend_app.response;

import java.util.List;

public class ContentResponse {

	private List<String> folders;
	private List<String> files;
	private String fileContent;

	public List<String> getFolders() {
		return folders;
	}

	public void setFolders(List<String> folders) {
		this.folders = folders;
	}

	public List<String> getFiles() {
		return files;
	}

	public void setFiles(List<String> files) {
		this.files = files;
	}

	public String getFileContent() {
		return fileContent;
	}

	public void setFileContent(String fileContent) {
		this.fileContent = fileContent;
	}

	@Override
	public String toString() {
		return "ContentResponse [folders=" + folders + ", files=" + files + ", fileContent=" + fileContent + "]";
	}

}
