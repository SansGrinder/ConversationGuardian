package org.example;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
public class usefulFunctions {
    public static String[] readSavedData(String filePath) {
        List<String> dataLines = new ArrayList<>();
        File file = new File(filePath);
        if (file.exists()) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String line;
                while ((line = reader.readLine()) != null) {
                    dataLines.add(line);
                }
                reader.close();
            } catch (IOException e) {
                System.out.println("An error occurred while reading the file: " + e.getMessage());
            }
        } else {
            System.out.println("File does not exist!");
        }

        if (!dataLines.isEmpty()) {
            String[] dataArray = new String[dataLines.size()];
            return dataLines.toArray(dataArray);
        } else {
            return null;
        }
    }
    public static void overrideFile(String filePath, String content) {
        File file = new File(filePath);
        try {
            FileWriter writer = new FileWriter(file);
            writer.write(content);
            writer.close();
            System.out.println("File overridden successfully!");
        } catch (IOException e) {
            System.out.println("An error occurred while overriding the file: " + e.getMessage());
        }
    }
    public void clearScreen(int amount) {
        for (int i = 0; i < amount; i++) {
            System.out.println();
        }
    }
    public void waitAMilliSecond(int milliSeconds) {
        try {
            TimeUnit.MILLISECONDS.sleep(milliSeconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public int randNumber(int min, int max) {
        return (int) (Math.random() * (max - min + 1)) + min;
    }
    public void msgPopUp(String text,String title,String msgType, String iconDirectory){
        if (iconDirectory==null){
            msgPopUp(text,title,msgType);
        }
        switch (msgType) {
            case "text" ->
                    JOptionPane.showMessageDialog(null, text, title, JOptionPane.INFORMATION_MESSAGE, new ImageIcon(usefulFunctions.class.getResource(iconDirectory)));
            case "error" ->
                    JOptionPane.showMessageDialog(null, text, title, JOptionPane.ERROR_MESSAGE, new ImageIcon(usefulFunctions.class.getResource(iconDirectory)));
            case "warning" ->
                    JOptionPane.showMessageDialog(null, text, title, JOptionPane.WARNING_MESSAGE, new ImageIcon(usefulFunctions.class.getResource(iconDirectory)));
            default ->  //plain text
                    JOptionPane.showMessageDialog(null, text, title, JOptionPane.PLAIN_MESSAGE, new ImageIcon(usefulFunctions.class.getResource(iconDirectory)));
        }
    }
    public void msgPopUp(String text, String title, String msgType){
        switch (msgType) {
            case "text" -> JOptionPane.showMessageDialog(null, text, title, JOptionPane.INFORMATION_MESSAGE);
            case "error" -> JOptionPane.showMessageDialog(null, text, title, JOptionPane.ERROR_MESSAGE);
            case "warning" -> JOptionPane.showMessageDialog(null, text, title, JOptionPane.WARNING_MESSAGE);
            default ->  //plain text
                    JOptionPane.showMessageDialog(null, text, title, JOptionPane.PLAIN_MESSAGE);
        }
    }
    public int confirmPopUp(String text, String title, String iconDirectory){
        return JOptionPane.showConfirmDialog(null, text, title, 0,0,new ImageIcon(Objects.requireNonNull(usefulFunctions.class.getResource(iconDirectory))));
    }
    public int confirmPopUp(String text, String title){
        return JOptionPane.showConfirmDialog(null, text, title, 0,0,null);
    }
    public int optionPopUp(String text, String title, String iconDirectory, String[] options){
        return JOptionPane.showOptionDialog(null, text, title, 0, 0, new ImageIcon(Objects.requireNonNull(usefulFunctions.class.getResource(iconDirectory))), options, "");
    }
    public int optionPopUp(String text, String title, String[] options){
        return JOptionPane.showOptionDialog(null, text, title, 0, 0, null, options, "");
    }
    public String textPopUp(String text, String title){
        return JOptionPane.showInputDialog(null,text, title,0);
    }
    public String textPopUp(String text, String title, String placeHolder){
        return JOptionPane.showInputDialog(text, placeHolder);
    }
    public String pullDownMenu(String text,String title,String[] options,String iconDirectory){return (String)JOptionPane.showInputDialog(null,text,title,0,new ImageIcon(Objects.requireNonNull(usefulFunctions.class.getResource(iconDirectory))),options,"Yes!");}
    public String pullDownMenu(String text,String title,String[] options){ return (String)JOptionPane.showInputDialog(null,text,title,0,null,options,"Yes!");}
    public static void generateCrashReport(int programRunState, Exception e){

    }
}