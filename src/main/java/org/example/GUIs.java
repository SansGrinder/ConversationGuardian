package org.example;
import javax.swing.JOptionPane;
public class GUIs { // Sorted by order of execution
    public static void programCrashed(Exception e){
        msgPopUp("The program has crashed due to an error!\nA crash report will be saved to your Downloads folder! Send Gordon the crash log so he can fix it!","Oh No...","error");
        CrashExport.generateCrashReport(Runner.programRunProgress,e);
        Runner.programEndedExpectedly =true;
        System.exit(0);
    } // For crashing with exception
    public static void programCrashed(){
        msgPopUp("Oh no! The program has crashed due to an unknown error!\nA crash report will be saved to your Downloads folder! Let's hope Gordon can fix it!","Oh No...","error");
        Runner.programEndedExpectedly =true;
        System.exit(0);
    } // For crashing WITHOUT exceptions
    public static void expectedProgramCrash(Exception e){
        CrashExport.generateCrashReport(Runner.programRunProgress, e);
        Runner.programEndedExpectedly=true;
    } // For silent crash reports

    // Below this point are JOptionPane "shortcut" methods

    public static void msgPopUp(String text, String title, String msgType){
        switch (msgType) {
            case "text" -> JOptionPane.showMessageDialog(null, text, title, JOptionPane.INFORMATION_MESSAGE);
            case "error" -> JOptionPane.showMessageDialog(null, text, title, JOptionPane.ERROR_MESSAGE);
            case "warning" -> JOptionPane.showMessageDialog(null, text, title, JOptionPane.WARNING_MESSAGE);
            default ->  //plain text
                    JOptionPane.showMessageDialog(null, text, title, JOptionPane.PLAIN_MESSAGE);
        }
    }
    public static int optionPopUp(String text, String title, String[] options){
        return JOptionPane.showOptionDialog(null, text, title, JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, "");
    }
    public static int optionPopUp(String text,String title){
        return JOptionPane.showOptionDialog(null,text,title, JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE,null,null,"");
    }
    public static String textPopUp(String text, String title){
        return JOptionPane.showInputDialog(null,text, title,JOptionPane.INFORMATION_MESSAGE);
    }
    public static String textPopUp(String text, Object placeHolderText){
        return JOptionPane.showInputDialog(text,placeHolderText);
    }
}
