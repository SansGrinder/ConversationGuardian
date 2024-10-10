package org.example;
public class Runner {
    public static int programRunProgress=0;
    public static void main(String[] args){
        try {
            OnLaunch.main(args);
        } catch (Exception e){
            usefulFunctions.generateCrashReport(programRunProgress,e);
        }
    }
}
