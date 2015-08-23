package RetrieveData;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.mozilla.universalchardet.UniversalDetector;

public class CSVFileReader {	
	protected static final String COMMA_DELIMETER = ",";
	
	public List<String> readCSVFileToStringList(String filepath) {
		Scanner fileScanner = null;
		List<String> linesInFile = new ArrayList<String>();
		fileScanner = openFileScanner(filepath, fileScanner);
		if (fileScanner != null)
		{
			while (fileScanner.hasNextLine()) {
				linesInFile.add(fileScanner.nextLine());
			}
		
			fileScanner.close();
		}
		return linesInFile;
	}

	protected Scanner openFileScanner(String filepath, Scanner fileScanner) {
		try {
			File input = new File(filepath);
			fileScanner = new Scanner ( input, retrieveEncodingOfFile(filepath));
		} catch (FileNotFoundException e) {
			return null;
		}
		return fileScanner;
	}
	
	private String retrieveEncodingOfFile (String filepath) {
		 	byte[] buf = new byte[4096];
		 	String encoding = "UTF-8";
		    java.io.FileInputStream fis;
			try {
				fis = new java.io.FileInputStream(filepath);
			} catch (FileNotFoundException e) {
				return encoding;
			}

		    // (1)
		    UniversalDetector detector = new UniversalDetector(null);

		    // (2)
		    int nread;
		    try {
				while ((nread = fis.read(buf)) > 0 && !detector.isDone()) {
				  detector.handleData(buf, 0, nread);
				}
			} catch (IOException e) {
				// it might be that fis.read threw an exception, but perhaps we have already read enough bytes
			}
		    // (3)
		    detector.dataEnd();

		    // (4)
		    
		    if (detector.getDetectedCharset() != null) {
		    	encoding = detector.getDetectedCharset();
		    }

		    // (5)
		    try {
				fis.close();
			} catch (IOException e) {}
		    detector.reset();
		    
		    return encoding;
	}
	
}
