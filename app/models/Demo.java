package models;

public class Demo {
    public static void main(String[] args) {

    	String filePath;
    	if (args.length != 2) {
    		System.err.println("Invalid number of arguments");
    		return;
    	}

    	if (args[0].equals("-i")) {
    		filePath = args[1];
            Project1Scheduler scheduler = new Project1Scheduler();

            // scheduler.calculateSchedule(filePath);
    	} else {
    		System.err.println("Illegal flag " + args[1]);
    		return;
    	}

    }
}
