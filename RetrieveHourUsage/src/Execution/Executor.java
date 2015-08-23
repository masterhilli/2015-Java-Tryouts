package Execution;

import RetrieveData.ProjectTimeCalculator;

public class Executor {
	//"C:\\catalysts\\dev\\2015_JavaTryouts\\RetrieveHourUsage\\tst\\20150820.csv"
	public static void main(String[] args) {
		if (args.length == 2) {
			ProjectTimeCalculator prjTimeCalculator = new ProjectTimeCalculator(args[0], args[1] +":00");
//			ProjectTimeCalculator prjTimeCalculator = new ProjectTimeCalculator("C:\\catalysts\\dev\\2015_JavaTryouts\\RetrieveHourUsage\\tst\\20150820.csv");
		
		
			System.out.println(prjTimeCalculator.toString());
		} else {
			System.out.println ("please call the program as follows: \n\t java -jar GenerateHours.jar [filepath to csv] [hours:minutes Worked]\nhours:mins worked in format:\t hh:mm ");
		}
	}

}
