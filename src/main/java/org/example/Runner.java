package org.example;
public class Runner {
    public static int programRunProgress=0;
    // This program run progress variable is used for crash report generating
    public static boolean programEndedExpectedly = false;
    // This boolean values determines if a program ended as expected
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
        do {
            runApp();
            resetInstanceVariables();
        } while (!userClosedWindow);
    }
    private static void runApp(){
        try {
            MainBranch.launch(); // The main program
        } catch (ExpectedException exp){
            GUIs.expectedProgramCrash(exp); // Doesn't generate a local crash log
        } catch (FatalError fte){
            GUIs.programCrashed(fte); // Passing the exception to the GUIs Class for error logging
            programEndedExpectedly = true;
            System.exit(0);
        } catch (Exception e){ // This catches any other unexpected exceptions thrown in the main program
            GUIs.programCrashed(new FatalError(e)); // Passing the exception to the GUIs Class for error logging
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
