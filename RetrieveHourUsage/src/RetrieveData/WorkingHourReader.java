package RetrieveData;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.StringTokenizer;

public final class WorkingHourReader extends CSVFileReader {
	private String filePath = "";
	private Calendar dateInformationFromFile = null;
	private List<String> commaSeparatedInformationArray  = null;

	private List<OpenWindowInformation> timeSpanInfoAboutOpendWindows= null;
	
	public WorkingHourReader(String filePath) {
		this.filePath = filePath;
		retrieveWindowInformation();
		retrieveDate();
	}

	private void retrieveWindowInformation() {
		initCommaSeperatedArray();
		if (commaSeparatedInformationArray == null || commaSeparatedInformationArray.isEmpty())
			return;
		
		timeSpanInfoAboutOpendWindows = createListWithOpenWindowInformation();
		
	}

	private void initCommaSeperatedArray() {
		if (commaSeparatedInformationArray == null)
			commaSeparatedInformationArray = readCSVFileToStringList(filePath);
	}
	
	private void retrieveDate() {
		initCommaSeperatedArray();
		for (String commaSeparatedInformation : commaSeparatedInformationArray) {
			StringTokenizer seperateLineByComma = new StringTokenizer(commaSeparatedInformation, COMMA_DELIMETER);
			if (seperateLineByComma.countTokens() >= 6) {
				initializeDate(seperateLineByComma.nextToken());
			}
			
			if (dateInformationFromFile != null)
				return;
		}
	}

	private List<OpenWindowInformation> createListWithOpenWindowInformation() {
		List<OpenWindowInformation> windowInformationArray = new ArrayList<OpenWindowInformation>();
		
		
		for (String commaSeparatedInformation : commaSeparatedInformationArray) {
			StringTokenizer seperateLineByComma = new StringTokenizer(commaSeparatedInformation, COMMA_DELIMETER);
			if (seperateLineByComma.countTokens() >= 6) {
				OpenWindowInformation toCheckIfTimeIsBiggerThan60sec = createOpenWindowInformation(seperateLineByComma);
				if (toCheckIfTimeIsBiggerThan60sec.getTimeSpan().getOverallSeconds() > 59) {
					windowInformationArray.add(toCheckIfTimeIsBiggerThan60sec);
				}
			}
		}
		return windowInformationArray;
	}

	private OpenWindowInformation createOpenWindowInformation(StringTokenizer seperateLineByComma) {
		List<String> seperatedValues = new ArrayList<String>();

		while (seperateLineByComma.hasMoreTokens()){	
			seperatedValues.add(seperateLineByComma.nextToken().trim());
		}

		return new OpenWindowInformation(seperatedValues.get(2), 
										 seperatedValues.get(0), 
										 seperatedValues.get(3));
	}

	private void initializeDate(String dateString) {
		if (dateInformationFromFile == null) {
			DateFormat dateFormater = new SimpleDateFormat("yyyy/MM/dd");
			dateInformationFromFile = Calendar.getInstance();
			
			try {
				dateInformationFromFile.setTime(dateFormater.parse(dateString));
			} catch (ParseException e) {
				// nothing todo, then we do not have a Date, not that big of a problem!
			}
		}
	}

	public List<OpenWindowInformation> getTimeSpanInfoAboutOpendWindows() {
		return timeSpanInfoAboutOpendWindows;
	}

	
	public Calendar getDateInformationFromFile() {
		return dateInformationFromFile;
	}

	public boolean isInitialized() {
		return timeSpanInfoAboutOpendWindows != null;
	}	
}
