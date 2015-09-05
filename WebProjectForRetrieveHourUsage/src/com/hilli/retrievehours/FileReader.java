package com.hilli.retrievehours;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileReader {
	public static List<String> listFilesForFolder(final String folder) {
		List<String> filesInFolder = new ArrayList<String>();
		try {
			Path folderPath = Paths.get(folder);
			Files.walk(folderPath, 1).forEach(filePath -> {
				if (!folderPath.equals(filePath)) {
					if (Files.isRegularFile(filePath)) {
						System.out.println(filePath.getFileName().toString());
						filesInFolder.add(filePath.getFileName().toString());
					}
					else if (Files.isDirectory(filePath)){
						System.out.println(filePath.getFileName().toString());
						filesInFolder.add(filePath.getFileName().toString());
					}
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return filesInFolder;
	}
}