package org.example;
public class Runner {
    public static int programRunProgress=0;
    // This program run progress variable is used for crash report generating
    public static boolean programEndedExpectedly = false;
    // This boolean values determines if a program ended as expected
    public static boolean requestSilenceCrashReport = false;
    // This boolean determines whether the error is impacting user experience, false for fatal, true for minor
    public static boolean userClosedWindow = false;
    public static void main(String[] args){
        // Adding a shutdown hook to the program for unexpected crash
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (!programEndedExpectedly){
                // Shutdown Hook for abnormal quits without exceptions
                System.out.println("Emergency shutdown hook executed!");
                GUIs.programCrashed(); // Requests the GUIs Class to show the alert, then error logging
                programEndedExpectedly=true;
            }
        }));
        while (!userClosedWindow){
            runApp();
            resetInstanceVariables();
        }
    }
    private static void runApp(){
        try {
            MainBranch.launch(); // The main program
        } catch (Exception e){ // This catches any exceptions thrown in the main program
            if (requestSilenceCrashReport) {
                GUIs.expectedProgramCrash(e); // Doesn't generate a local crash log
            } else {
                GUIs.programCrashed(e); // Passing the GUIs Class the exception to be used for error logging
                programEndedExpectedly = true;
                System.exit(0);
            }
        }
    }
    private static void resetInstanceVariables(){
        programRunProgress=0;
        programEndedExpectedly=false;
        requestSilenceCrashReport=false;
        userClosedWindow=false;
    }
}
