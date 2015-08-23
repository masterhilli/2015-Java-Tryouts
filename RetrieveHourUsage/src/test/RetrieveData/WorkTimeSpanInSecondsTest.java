package test.RetrieveData;

import static org.junit.Assert.*;

import org.junit.Test;

import RetrieveData.TimeSpanOfWork;

public class WorkTimeSpanInSecondsTest {
	
	private static final String TestDate = "2015/08/20"; 
	private static final String TestTime = "1:22:10";
	private static final String TestTime2 = "00:33:11";

	@Test
	public void testSetTimeSpan() {
		TimeSpanOfWork workTimeSpan = new TimeSpanOfWork();
		
		workTimeSpan.setTimeSpan(TestDate, TestTime);


		TimeSpanOfWork expected = new TimeSpanOfWork(1, 22, 10);
		assertEquals(expected, workTimeSpan);
	}
	
	@Test
	public void testAddingTime() {
		TimeSpanOfWork workTimeSpan = new TimeSpanOfWork();
		TimeSpanOfWork workTimeSpan2 = new TimeSpanOfWork();
		
		workTimeSpan.setTimeSpan(TestDate, TestTime);
		workTimeSpan2.setTimeSpan(TestDate, TestTime2);
		
		workTimeSpan.add(workTimeSpan2);
		
		TimeSpanOfWork expected = new TimeSpanOfWork(1, 55, 21);
		assertEquals(expected, workTimeSpan);
	}

}
