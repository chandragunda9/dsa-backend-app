package com.learning.dsa_backend_app.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.learning.dsa_backend_app.response.ContentResponse;

//@Service
public class ContentProviderService {

	@Value("${main.package.path}")
	String mainPackagePath;

	Logger logger = LoggerFactory.getLogger(getClass());

	public ContentResponse getContent(String strPath) {

		ContentResponse content = new ContentResponse();

		List<String> folders = new ArrayList<>();
		List<String> files = new ArrayList<>();
		String codeContent = "";

		logger.info("Full path is {}", mainPackagePath + strPath);
		Path path = Paths.get(mainPackagePath + strPath); // Path to the package

		try {
			logger.info("Files.exists: {}", Files.exists(path));
			logger.info("Files.isDirectory: {}", Files.isDirectory(path));
			if (Files.exists(path)) {
				if (Files.isDirectory(path)) {

					fetchFolderContent(path, folders, files);

				} else {

					codeContent = Files.readString(path);

				}
			}

		} catch (Exception e) {
			logger.info("getContent Exception occurred: {} ", e.getMessage());
			e.printStackTrace();
		}

		content.setFiles(files);
		content.setFolders(folders);
		content.setFileContent(codeContent);
		logger.info("content: {}", content);
		return content;
	}

	private void fetchFolderContent(Path path, List<String> folders, List<String> files) {
		try (Stream<Path> paths = Files.walk(path, 1)) { // Walk through the directory tree
			paths.filter(Files::exists) // Make sure to include only existing files/folders
					.filter(p -> !p.equals(path)).forEach(p -> {
						if (Files.isDirectory(p)) {
							folders.add(p.getFileName().toString());
						} else {
							files.add(p.getFileName().toString());
						}
					});
		} catch (IOException e) {
			logger.info("fetchFolderContent Exception occurred: {} ", e.getMessage());
			e.printStackTrace();
		}

	}

//	public static void main(String[] args) {
////		String path = "codes/";
//		String path = "codes/Sample.java";
//		ContentProviderService service = new ContentProviderService();
//		System.out.println(service.getContent(path));
//	}
}
