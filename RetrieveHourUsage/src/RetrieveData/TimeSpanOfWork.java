package RetrieveData;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public 	class TimeSpanOfWork {
	private static final String FORMAT_DATE = "yyyy/MM/dd";
	private static final DateFormat DateParser = new SimpleDateFormat(FORMAT_DATE);
	private static final DateFormat TimeParser = new SimpleDateFormat(FORMAT_DATE + " HH:mm:ss");
	private long secondsOfWorkDone; 
	
	public TimeSpanOfWork() {
		secondsOfWorkDone = 0;
	}
	
	public TimeSpanOfWork(int hours, int minutes, int seconds) {
		secondsOfWorkDone = ((hours*60)+minutes)*60 + seconds;
	}
	
	public void setTimeSpan(String date, String timeInHHmmss) {
		try {
			secondsOfWorkDone = (int) (TimeParser.parse(date + " " + timeInHHmmss).getTime() - setDay(date))/1000;
		}
		catch (ParseException e) {
			secondsOfWorkDone = -1;
		}
	}
	
	private long setDay(String date){
		try {
			return DateParser.parse(date).getTime();
		} catch (ParseException e) {
			return 0;
		}
	}
	
	public long getHours() {
		return (secondsOfWorkDone / 60 ) / 60;
	}
	
	public long getMinutes () {
		return (secondsOfWorkDone / 60) % 60;
	}
	
	public long getSeconds () {
		return secondsOfWorkDone % 60;
	}
	
	public long getOverallSeconds() {
		return secondsOfWorkDone;
	}
	
	protected void setOverallSeconds(long newSecondsOfWorkDone) {
		secondsOfWorkDone = newSecondsOfWorkDone;
	}

	public void add (TimeSpanOfWork addingSeconds) {
		this.secondsOfWorkDone += addingSeconds.secondsOfWorkDone;
	}
	
	@Override
	public boolean equals(Object obj) {
		
		
		TimeSpanOfWork compareTimeSpan = (TimeSpanOfWork) obj;
		
		if (compareTimeSpan == null)
			return false;
		
		return compareTimeSpan.secondsOfWorkDone == this.secondsOfWorkDone && this.hashCode() == compareTimeSpan.hashCode();
	}
	
	@Override
	public int hashCode() {
		return (int)secondsOfWorkDone;
	}
	
	@Override
	public String toString() {
		return new String(""+ getHours() + ":" + getMinutes() + ":" + getSeconds());
	}
}