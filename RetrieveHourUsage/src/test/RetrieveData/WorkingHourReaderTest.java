package test.RetrieveData;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import RetrieveData.OpenWindowInformation;
import RetrieveData.WorkingHourReader;

public class WorkingHourReaderTest {
	private List<OpenWindowInformation> fromTestFile = null;
	private final String pathToPreparedTstFile = "C:\\catalysts\\dev\\2015_JavaTryouts\\RetrieveHourUsage\\tst\\preparedTestFileForReadInObject.csv";
	
	@Before
	public void setUp () {
		WorkingHourReader workingHourReader = new WorkingHourReader(pathToPreparedTstFile);
		fromTestFile = workingHourReader.getTimeSpanInfoAboutOpendWindows();
	}
	
	@Test
	public void testParseCommandLineArgsToObjectIfLastIsSame() {
		
		OpenWindowInformation actual = fromTestFile.get(fromTestFile.size()-1);
		OpenWindowInformation expected = new OpenWindowInformation("\"Martin Window Title 4\"", "2015/08/20", "0:34:43");
		assertEquals(expected.getWindowTitle(), actual.getWindowTitle());
	}
	
	@Test
	public void testParseCommandLineArgsToObjectIfFirstIsSame() {
		
		OpenWindowInformation actual = fromTestFile.get(0);
		OpenWindowInformation expected = new OpenWindowInformation("Martin Window Title 1", "2015/08/20", "1:00:10");
		assertEquals(expected.getTimeSpan(), actual.getTimeSpan());
	}
	
	@Test
	public void testOpenFileWithInvalidPathReturnsNULL () {
		WorkingHourReader workingHourReader = new WorkingHourReader("C:\\test.xml");
		
		assertFalse(workingHourReader.isInitialized());
	}

}
