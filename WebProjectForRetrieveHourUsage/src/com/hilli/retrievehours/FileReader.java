package com.hilli.retrievehours;

public class FileReader {

}


/*

public void listFilesForFolder(final File folder) {
    for (final File fileEntry : folder.listFiles()) {
        if (fileEntry.isDirectory()) {
            listFilesForFolder(fileEntry);
        } else {
            System.out.println(fileEntry.getName());
        }
    }
}

final File folder = new File("/home/you/Desktop");
listFilesForFolder(folder);
Edit: This API is now available from Java 8.

Files.walk(Paths.get("/home/you/Desktop")).forEach(filePath -> {
    if (Files.isRegularFile(filePath)) {
        System.out.println(filePath);
    }
});




*/