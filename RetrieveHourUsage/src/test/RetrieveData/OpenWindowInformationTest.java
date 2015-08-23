package test.RetrieveData;

import static org.junit.Assert.*;


import org.junit.Before;
import org.junit.Test;

import RetrieveData.OpenWindowInformation;
import RetrieveData.TimeSpanOfWork;

public class OpenWindowInformationTest {
	private static final String TIME_SPAN = "1:22:10";
	private static final String MARTIN_WINDOW_TITLE_1 = "Martin Window Title 1";
	OpenWindowInformation checkDateParsedCorrectly = null;

	@Before
	public void setUp() {		
		checkDateParsedCorrectly= new OpenWindowInformation(MARTIN_WINDOW_TITLE_1, "2015/08/20", TIME_SPAN);
	}
	
	@Test
	public void testWindowTitleEquals() {
		assertEquals(MARTIN_WINDOW_TITLE_1, checkDateParsedCorrectly.getWindowTitle());
	}
	
	
	@Test
	public void testWorkTimeSpan () {
		TimeSpanOfWork expected = new TimeSpanOfWork(1,22,10);
		
		assertEquals(expected, checkDateParsedCorrectly.getTimeSpan());
	}
}
