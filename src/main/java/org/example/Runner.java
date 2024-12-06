package org.example;
public class Runner {
    public static int programRunProgress=0;
    // This program run progress variable is used for crash report generating
    public static boolean programEndedExpectedly = false;
    // This boolean values determines if a program ended as expected
    public static boolean requestSilenceCrashReport = false;
    // This boolean determines whether the error is impacting user experience, false for fatal, true for minor
    public static void main(String[] args){
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (!programEndedExpectedly){
                // Shutdown Hook for abnormal quits without exceptions
                System.out.println("Emergency shutdown hook executed!");
                GUIs.programCrashed(); // Requests the GUIs Class to show the alert, then error logging
                programEndedExpectedly=true;
            }
        }));
        try {
            OnLaunch.launch(); // The main program
        } catch (Exception e){
            if (requestSilenceCrashReport) {
                GUIs.expectedProgramCrash(e);
            } else {
                GUIs.programCrashed(e); // Passing the GUIs Class the exception to be used for error logging
                programEndedExpectedly = true;
                System.exit(0);
            }
        }
    }
}
