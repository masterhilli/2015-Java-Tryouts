package test.RetrieveData;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;

import org.junit.Test;

import RetrieveData.OpenWindowInformation;
import RetrieveData.WindowInfoToPrjAssigner;
import RetrieveData.WorkingHourReader;

public class WindowInfoToPrjAssignerTest {
	private final String pathToPreparedTstFile = "C:\\catalysts\\dev\\2015_JavaTryouts\\RetrieveHourUsage\\tst\\preparedTestFileForReadInObject.csv";
	private final String pathToPreparedTstFileWithPrj = "C:\\catalysts\\dev\\2015_JavaTryouts\\RetrieveHourUsage\\tst\\preparedTestFileForReadInObjectWithprojectData.csv";
	private final String pathToUmlaute = "C:\\catalysts\\dev\\2015_JavaTryouts\\RetrieveHourUsage\\tst\\WithUmlaute.csv";
	private final String pathToUmlauteInAnsi = "C:\\catalysts\\dev\\2015_JavaTryouts\\RetrieveHourUsage\\tst\\WithUmlauteInAnsi.csv";
	// 
	@Test
	public void testAssignOpenWindowToProjectAndHitNoPrj() {
		WorkingHourReader workHourReader = new WorkingHourReader(pathToPreparedTstFile);		
		List<OpenWindowInformation> openWindows =  workHourReader.getTimeSpanInfoAboutOpendWindows();
		
		WindowInfoToPrjAssigner assignedWindowInfoToPrjParser = WindowInfoToPrjAssigner.getInstance();
		
		HashMap<String, List<OpenWindowInformation>> assignedWindowsToPrjs = assignedWindowInfoToPrjParser.assignOpenWindowToProject(openWindows);
		
		assertEquals(WindowInfoToPrjAssigner.UNKNOWN, assignedWindowsToPrjs.keySet().toArray()[0].toString());
	}
	
	@Test
	public void testAssignOpenWindowToProjectAndHit3RedBull() {
		WorkingHourReader workHourReader = new WorkingHourReader(pathToPreparedTstFileWithPrj);		
		List<OpenWindowInformation> openWindows =  workHourReader.getTimeSpanInfoAboutOpendWindows();
		
		WindowInfoToPrjAssigner assignedWindowInfoToPrjParser = WindowInfoToPrjAssigner.getInstance();
		
		HashMap<String, List<OpenWindowInformation>> assignedWindowsToPrjs = assignedWindowInfoToPrjParser.assignOpenWindowToProject(openWindows);
		
		assertEquals(3, assignedWindowsToPrjs.get("REDBULL").size());
	}
	
	@Test
	public void testAssignOpenWindowToProjectAndHit2BMW() {
		WorkingHourReader workHourReader = new WorkingHourReader(pathToPreparedTstFileWithPrj);		
		List<OpenWindowInformation> openWindows =  workHourReader.getTimeSpanInfoAboutOpendWindows();
		
		WindowInfoToPrjAssigner assignedWindowInfoToPrjParser =WindowInfoToPrjAssigner.getInstance();
		
		HashMap<String, List<OpenWindowInformation>> assignedWindowsToPrjs = assignedWindowInfoToPrjParser.assignOpenWindowToProject(openWindows);
		
		assertEquals(2, assignedWindowsToPrjs.get("BMW").size());
	}
	
	@Test
	public void testProblematicWithUmlaute() {
		WorkingHourReader workHourReader = new WorkingHourReader(pathToUmlaute);		
		List<OpenWindowInformation> openWindows =  workHourReader.getTimeSpanInfoAboutOpendWindows();
		
		WindowInfoToPrjAssigner assignedWindowInfoToPrjParser =WindowInfoToPrjAssigner.getInstance();
		
		HashMap<String, List<OpenWindowInformation>> assignedWindowsToPrjs = assignedWindowInfoToPrjParser.assignOpenWindowToProject(openWindows);
		
		assertEquals(3, assignedWindowsToPrjs.get("BMW").size());
	}
	
	@Test
	public void testProblematicWithUmlauteInAnsi() {
		WorkingHourReader workHourReader = new WorkingHourReader(pathToUmlauteInAnsi);		
		List<OpenWindowInformation> openWindows =  workHourReader.getTimeSpanInfoAboutOpendWindows();
		
		WindowInfoToPrjAssigner assignedWindowInfoToPrjParser =WindowInfoToPrjAssigner.getInstance();
		
		HashMap<String, List<OpenWindowInformation>> assignedWindowsToPrjs = assignedWindowInfoToPrjParser.assignOpenWindowToProject(openWindows);
		
		assertEquals(3, assignedWindowsToPrjs.get("BMW").size());
	}

}
