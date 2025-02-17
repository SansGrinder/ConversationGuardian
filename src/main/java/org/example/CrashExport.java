package org.example;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CrashExport {
    public static String formattedTime="";
    public static void generateCrashReport(int programRunState, FatalError e){
        System.out.println("generateCrashReport has been called, with a programRunState of "
                +programRunState+" and the exception being "+e
        );
        String stackTrace;
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        stackTrace = sw.toString();
        String programRunPhase = switch (programRunState) {
            case 0 -> "Before mode selection";
            case 1 -> "Encryption";
            case 2 -> "After Encryption";
            case 3 -> "PDF Encryption";
            case 4 -> "After PDF Encryption";
            case 5 -> "Decryption";
            case 6 -> "After decryption";
            default -> "Unable to fetch program run state";
        };
        saveCrashLog(System.getProperty("user.home")+
                "/Downloads","Program run state: "+
                programRunPhase+"\nException:\n"+stackTrace);

        CrashEmail email=new CrashEmail(
                System.getProperty("user.home").split("/")[2]+" got an "+e+"!",
                "Program run state: "+programRunPhase+"\nException:\n"+stackTrace,false
        );

        email.sendAnEmail();
    }
    public static void generateCrashReport(int programRunState, ExpectedException e){
        System.out.println("generateCrashReport has been called, with a programRunState of "
                +programRunState+" and the exception being "+e);
        String stackTrace;
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        stackTrace = sw.toString();
        String programRunPhase = switch (programRunState) {
            case 0 -> "Before mode selection";
            case 1 -> "Encryption";
            case 2 -> "After Encryption";
            case 3 -> "PDF Encryption";
            case 4 -> "After PDF Encryption";
            case 5 -> "Decryption";
            case 6 -> "After decryption";
            default -> "Unable to fetch program run state";
        };
        CrashEmail email=new CrashEmail(
                System.getProperty("user.home").split("/")[2]+" got an "+e+"!",
                "Program run state: "+programRunPhase+"\nException:\n"+stackTrace,false
        );
        email.sendAnEmail();
    }
    private static void saveCrashLog(String filePath, String content) {
        // Get the current time with seconds
        LocalDateTime currentTime = LocalDateTime.now();
        // Define a custom date and time format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
        // Format the current time using the defined format
        formattedTime = currentTime.format(formatter);
        // Create a File
        File file = new File(filePath, "crash-log-at-"+formattedTime+".log");
        try {
            if (file.createNewFile()) {
                System.out.println("File created successfully!");
            } else {
                System.out.println("Failed to create the file!");
            }
        } catch (IOException e) {
            System.out.println("An error occurred while creating the file: " + e.getMessage());
        }
        // Now we write the content of the file
        try (FileWriter writer = new FileWriter(file)){
            writer.write(content);
            System.out.println("File written successfully!");
        } catch (IOException e) {
            System.out.println("An error occurred while overriding the file: " + e.getMessage());
        }
    }
}