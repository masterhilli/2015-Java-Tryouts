package RetrieveData;

public class OpenWindowInformation {
	private String windowTitle = "";
	private TimeSpanOfWork timeSpan = new TimeSpanOfWork();
	
	public OpenWindowInformation(String windowTitle, String date, String timeAsString) {
			setWindowTitle(windowTitle);
			timeSpan.setTimeSpan(date, timeAsString); 
	}

	public String getWindowTitle() {
		return windowTitle;
	}

	public void setWindowTitle(String windowTitle) {
		this.windowTitle = windowTitle;
	}

	public TimeSpanOfWork getTimeSpan() {
		return timeSpan;
	}

	public void setTimeSpan(TimeSpanOfWork timeSpan) {
		this.timeSpan = timeSpan;
	}
	

}
