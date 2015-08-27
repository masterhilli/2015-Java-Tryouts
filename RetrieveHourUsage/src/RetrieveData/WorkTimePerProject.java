package RetrieveData;

import java.util.HashMap;

public class WorkTimePerProject {
	TimeSpanInPercentOfWork overallTime;
	HashMap<String, TimeSpanInPercentOfWork> timeSpanInPercentPerProject;

	public WorkTimePerProject(TimeSpanOfWork timeWorked, HashMap<String, TimeSpanOfWork> timeSpanPerProject) {
		overallTime = new TimeSpanInPercentOfWork(timeWorked.getOverallSeconds(), timeWorked, timeWorked);
		timeSpanInPercentPerProject = new HashMap<String, TimeSpanInPercentOfWork> ();
		
		long overallSecondsFromWindows = retrieveSumOfSecondsOfWindows(timeSpanPerProject);
		for (String key : timeSpanPerProject.keySet()) {
			if (!key.equals(WindowInfoToPrjAssigner.UNKNOWN)) {
				timeSpanInPercentPerProject.put(key, new TimeSpanInPercentOfWork(overallSecondsFromWindows, overallTime,
						timeSpanPerProject.get(key)));
			}
		}
	}

	private long retrieveSumOfSecondsOfWindows(HashMap<String, TimeSpanOfWork> timeSpanPerProject) {
		long overallSecondsWithoutUnknown = 0;
		for (String key : timeSpanPerProject.keySet()) {
			if (!key.equals(WindowInfoToPrjAssigner.UNKNOWN)) {
				overallSecondsWithoutUnknown += timeSpanPerProject.get(key).getOverallSeconds();
			}
		}
		return overallSecondsWithoutUnknown;
	}

	@Override
	public String toString() {
		String returnValue = "Book the following time to your projects: \n-----------------------------------------------\n";
		returnValue += formatOutput("Overall Time spent at work", this.overallTime);
		if (timeSpanInPercentPerProject != null) {
			for (String key : timeSpanInPercentPerProject.keySet()) {
				returnValue += formatOutput(key, timeSpanInPercentPerProject.get(key));
			}
		} else {
			returnValue += "No data available!";
		}
		returnValue += "\n-----------------------------------------------\n";

		return returnValue;
	}

	private String formatOutput(String key, TimeSpanInPercentOfWork value) {
		if (value != null) {
			return (key + ":\t" + value.getHours() + ":" + value.getMinutes() + "\t" + value.getPercentOfWork()
					+ "%\t" +  value.getHours() +"." + ((long)((100.0/60.0) *value.getMinutes())) + "\n");
		} else {
			return key + ": No data available";
		}
	}

}
