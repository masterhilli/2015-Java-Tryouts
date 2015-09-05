package com.hilli.retrievehours;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileReader {
	public static List<String> listFilesForFolder(final String folder) {
		List<String> filesInFolder = new ArrayList<String>();
		try {
			Files.walk(Paths.get(folder)).forEach(filePath -> {
			    if (Files.isRegularFile(filePath)) {
			        System.out.println(filePath);
			        filesInFolder.add(filePath.getFileName().toString());
			    }
			});
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return filesInFolder;
	}
}