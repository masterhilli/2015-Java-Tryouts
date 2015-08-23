package RetrieveData;

public class TimeSpanInPercentOfWork extends TimeSpanOfWork {	
	private double percentOfWork = 0;

	public TimeSpanInPercentOfWork(long overallSecondsForAllWindowsOpened, TimeSpanOfWork overallTime,
			TimeSpanOfWork timeSpanOfWork) {
		
		percentOfWork = (1.0 /  overallSecondsForAllWindowsOpened) * timeSpanOfWork.getOverallSeconds();
		setOverallSeconds((long) ( percentOfWork * overallTime.getOverallSeconds()));
		
	}
	
	public long getPercentOfWork () {
		return (long) (percentOfWork * 100.0);
	}

}
