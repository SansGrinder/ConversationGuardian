package org.example;
public class Runner {
    public static int programRunProgress=0;
    // This program run progress variable is used for crash report generating

    public static boolean programEndedExpectedly = false;
    // This boolean values determines if a program ended as expected

    public static boolean userClosedWindow = false;
    // This value would be set to true if the user closed the window,
    // in which case the program should exit normally.

    public static void main(String[] args){
        // Adding a shutdown hook to the program for unexpected crash
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (!programEndedExpectedly){
                // Shutdown Hook for abnormal quits without exceptions
                System.out.println("Emergency shutdown hook executed!");
                // Requests the GUIs Class to show and handle the alert
                GUIs.programCrashed();
                programEndedExpectedly=true;
            }
        }));
        do {
            runApp();
            resetInstanceVariables();
        } while (!userClosedWindow);
    }
    private static void runApp(){
        try {
            MainBranch.launch(); // The main program
        } catch (ExpectedException exp){
            // Doesn't generate a local crash log
            GUIs.expectedProgramCrash(exp);
        } catch (FatalError fte){
            // Passing the exception to the GUIs Class for error logging
            GUIs.programCrashed(fte);
            programEndedExpectedly = true;
            System.exit(0);
        } catch (Exception e){ // This catches any other unexpected exceptions
            // Passing the exception to the GUIs Class for error logging
            GUIs.programCrashed(new FatalError(e));
            programEndedExpectedly = true;
            System.exit(0);
        }
    }
    private static void resetInstanceVariables(){
        programRunProgress=0;
        programEndedExpectedly=false;
        userClosedWindow=false;
    }
}
