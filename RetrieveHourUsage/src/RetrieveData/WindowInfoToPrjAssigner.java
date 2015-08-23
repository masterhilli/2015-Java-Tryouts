package RetrieveData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

public class WindowInfoToPrjAssigner extends CSVFileReader{
	private static WindowInfoToPrjAssigner myInstance= new WindowInfoToPrjAssigner();
	public static final String UNKNOWN = "unknown";
		
	private HashMap<String, String[]> searchValueMap = new HashMap<String, String[]>();
	
	private WindowInfoToPrjAssigner() {
		List<String> prjList = readCSVFileToStringList(".\\prjConfig.csv"); // for future use, to read In project Info from config file!
		
		parseInValuesForPrj(prjList);
	}

	private void parseInValuesForPrj(List<String> prjList) {
		for (String commaSeperatedList : prjList) {
			StringTokenizer seperator = new StringTokenizer(commaSeperatedList, COMMA_DELIMETER);
			if (seperator.countTokens() >= 1) {
				List<String> valuesToCheck = new ArrayList<String>();
				while (seperator.hasMoreTokens()) {
					valuesToCheck.add(seperator.nextToken().trim());
				}
				String[] valueArray = new String[valuesToCheck.size()]; 
				valueArray = valuesToCheck.toArray(valueArray);
				
				searchValueMap.put(valueArray[0], valueArray);
			}
		}
	}
	
	public HashMap<String, List<OpenWindowInformation>> assignOpenWindowToProject(List<OpenWindowInformation> openWindowsInformation) {
		HashMap<String, List<OpenWindowInformation>> openWindowPerProjectList = new HashMap<String, List<OpenWindowInformation>>();
		
		for (OpenWindowInformation openWindowInformation : openWindowsInformation) {
			String prjName = parseForKeyWords(openWindowInformation.getWindowTitle());
			if (!openWindowPerProjectList.containsKey(prjName)) {
				openWindowPerProjectList.put(prjName, new ArrayList<OpenWindowInformation>());
			}
			openWindowPerProjectList.get(prjName).add(openWindowInformation);
		}
		
		return openWindowPerProjectList;
	}

	private String parseForKeyWords(String windowTitle) {
		for (String key : searchValueMap.keySet()) {
			String[] valuesToSearchFor = searchValueMap.get(key);
			for (int i = 0; i < valuesToSearchFor.length; i++) {
				if (windowTitle.toLowerCase().contains(valuesToSearchFor[i].toLowerCase()))
					return key;
			}
		}
		return UNKNOWN;
	}

	public static WindowInfoToPrjAssigner getInstance() {
		return myInstance;
	}
	
	
	
}
