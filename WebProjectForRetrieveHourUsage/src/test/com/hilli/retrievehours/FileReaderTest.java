package test.com.hilli.retrievehours;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import com.hilli.retrievehours.FileReader;

public class FileReaderTest {

	@Test
	public void testListFilesForFolder() {
		List<String> actual = FileReader.listFilesForFolder("D:\\dev\\2015_javatryouts");
		
		assertTrue(null != actual);
	}

}
