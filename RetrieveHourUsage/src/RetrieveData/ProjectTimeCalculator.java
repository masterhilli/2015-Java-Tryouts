package RetrieveData;

import java.util.List;
import java.util.Calendar;
import java.util.HashMap;

public class ProjectTimeCalculator {
	Calendar calendarOfProject = null;
	TimeSpanOfWork workingTimeOfDay = new TimeSpanOfWork();
	HashMap<String, TimeSpanOfWork> timePerProject;
	HashMap<String, List<OpenWindowInformation>> windowsPerProjects;
	WorkTimePerProject workTimePerProject = null;

	public ProjectTimeCalculator(String filePathToWindowFile, String overallTimeSpent) {
		workingTimeOfDay.setTimeSpan("1981/08/31", overallTimeSpent);
		generateProjectTime(filePathToWindowFile);
		createTimePerProject();
		workTimePerProject = new WorkTimePerProject(workingTimeOfDay, timePerProject);
	}

	private void generateProjectTime(String filePathToWindowFile) {
		WorkingHourReader workHourReader = new WorkingHourReader(filePathToWindowFile);
		if (!workHourReader.isInitialized())
			return;

		calendarOfProject = workHourReader.getDateInformationFromFile();
		WindowInfoToPrjAssigner prjAssigner = WindowInfoToPrjAssigner.getInstance();
		this.windowsPerProjects = prjAssigner
				.assignOpenWindowToProject(workHourReader.getTimeSpanInfoAboutOpendWindows());

		createTimePerProject();
	}

	private void createTimePerProject() {
		timePerProject = new HashMap<String, TimeSpanOfWork>();
		if (windowsPerProjects != null) {
			for (String key : windowsPerProjects.keySet()) {
				TimeSpanOfWork timeSpent = new TimeSpanOfWork();
				for (OpenWindowInformation windowInfo : windowsPerProjects.get(key)) {
					timeSpent.add(windowInfo.getTimeSpan());
				}

				timePerProject.put(key, timeSpent);
			}
		}
	}

	@Override
	public String toString() {
		String toStringReturnValue = "";
		if (calendarOfProject != null) {
			toStringReturnValue = "Date: " + calendarOfProject.get(Calendar.DAY_OF_MONTH) + "."
					+ (calendarOfProject.get(Calendar.MONTH) + 1) + "." + calendarOfProject.get(Calendar.YEAR) + "\n";
		} else {
			toStringReturnValue = "No Date available: \n";
		}
		if (workTimePerProject != null)
			toStringReturnValue += workTimePerProject.toString();

		if (timePerProject != null) {
			for (String key : timePerProject.keySet()) {
				toStringReturnValue += key + ": " + timePerProject.get(key).toString() + "\n";
			}
		} else {
			toStringReturnValue += "No time per Project available \n";
		}

		toStringReturnValue += "\n********************************************\n";

		if (windowsPerProjects != null) {
			if (windowsPerProjects.containsKey(WindowInfoToPrjAssigner.UNKNOWN)) {
				toStringReturnValue += "All not identified windows: \n";
				for (OpenWindowInformation value : windowsPerProjects.get(WindowInfoToPrjAssigner.UNKNOWN)) {
					toStringReturnValue += value.getTimeSpan().toString() + "\t" + value.getWindowTitle() + "\n";
				}
			}
		} else {
			toStringReturnValue += "No OpenWindos Info available!";
		}

		return toStringReturnValue;
	}

}
