package org.example;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
import org.apache.pdfbox.pdmodel.encryption.StandardProtectionPolicy;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.io.IOException;

public class MainBranch {
    // Gets and stores the difference between the current time and 1970 January 1st 0:00 in milliseconds
    private final static long currentTime=System.currentTimeMillis();
    // final char[] that stores the most common characters you can see on a keyboard
    private final static char[] dictionary={'#','y','F','7','Z','L','r','X','j','0','s',(char)92,')','a','+','~','f','h','`','>','t','%','8','K','Ø','9','k','l','i','A','*','J','T','S','6','m','@','}','o','M','C','_',(char)39,'z','E','&','G','b','R','W','g','I','^','H','|','=','P','O','{','N','Y','2','.','v','u','5','D','"','，','。','？','！','《','》','（','）','￥',']','?','[','$',':','(','V','<','n','c','U','!','π','B','-','x','；','：','“','”','‘','’',' ',';','q','、','w','p',',','e','Q','d','3','1','4','/','','¿'};
    // final JFileChooser for PDFBox.
    private static final JFileChooser fileChooser = new JFileChooser();
    public static void launch() throws Exception {
        int response = GUIs.optionPopUp("Choose a mode: ", "Select an option: ", new String[]{"Decrypt", "Select a PDF", "Encrypt"});
        switch (response){
            case 2->{ // Encrypt Chosen
                Runner.programRunProgress=1;
                // 1 for Encryption
                encryptionMethod();
            }
            case 1-> PDFBranch(); // User want to select a PDF
            case 0->{ // Decrypt Chosen
                Runner.programRunProgress=5;
                // 5 is for Before Decryption
                decryptionMethod();
            }
            case -1->{
                Runner.userClosedWindow=true;
                Runner.programEndedExpectedly=true;
                System.exit(0);
            }
            default->{
                // Logically impossible to reach
                // Generating silent crash report
                GUIs.msgPopUp("Error! Could not read your response!\nRestarting the program...","ERROR","error");
                throw new ExpectedException("Impossible user response ("+response+") in OnLaunch - Main Method - switch_case block", new IllegalStateException());
            }
        }
    }
    private static int getIndex(String input){
        long id=Long.parseLong(input);
        id%=dictionary.length;
        if (id==0){ // If index is 0, the String will not be encrypted
            id=2;
        } else if (id<0){ // Index being negative works alright, but we want to keep things consistent.
            id=Math.abs(id);
        }
        return (int) id;
    }
    private static void encryptionMethod() throws Exception {
        String textString=GUIs.textPopUp("Enter your text here: ",(Object)"(Don't include Chinese Characters/punctuations or weird symbols!)");
        // Although the dictionary contains most characters you can see on a keyboard, not all characters could be included. The user will be prompted that the input is invalid if so.
        if (textString == null){
            Runner.userClosedWindow=true;
            Runner.programEndedExpectedly=true;
            System.exit(0);
        }
        try { // To see if I can encrypt the text without errors
            System.out.println(encryptString(textString,String.valueOf(currentTime)));
            // I can also ignore the return value of encryptString() but printing it out makes debugging easier
            if (textString.isEmpty()){
                // Does not accept empty inputs.
                throw new NullPointerException("Empty input");
            }
        } catch (NullPointerException e){
            // A NullPointerException could be caused by 3 reasons: empty input, character not found, or unexpected input.
            String errorMessage=e.getMessage().contains("Empty input")?
                    "No input found!\n" +
                    "Relaunch the program to try again, or contact the developer if you believe this is a mistake.":
                    "Invalid characters found in your input!\n" +
                            "Do not include Chinese Characters/Punctuations or any non-English symbol! " +
                            "Please run the code again if you want to try again. ";
            // This ensures we print out the correct error no matter what has caused it.
            GUIs.msgPopUp(errorMessage,"BAD INPUT","plain text");
            Runner.programEndedExpectedly=true;
            throw new ExpectedException(errorMessage,e);
        }
        // Encrypting for the first time: (adding index to the front)
        String firstEncryption= currentTime +"Ø"+ encryptString(textString,String.valueOf(currentTime));
        long newIndex=0;
        for (int i=0;i<firstEncryption.length();i++){
            // Adding characters' Unicode index to make new index
            // E.g. int a='a'+'¿'; a=288 ('a'=97,'¿'=191)
            char c=firstEncryption.charAt(i);
            newIndex+=c;
        }
        // Encrypting for the second time
        String finalText = newIndex+"Ø"+ encryptString(firstEncryption, String.valueOf(newIndex));
        // Prompt the user whether to copy the output to their clipboard
        // I mean chances are they will choose to copy but what if they had something important in their clipboard?
        if (GUIs.optionPopUp("Do you want to save the result to clipboard?\nThis will override your clipboard!","Copy to clipboard?")==0){
            StringSelection stringSelection = new StringSelection(finalText);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(stringSelection, null);
            GUIs.msgPopUp("Your encrypted text is now copied to your clipboard!","Done!","plain text");
        } else {
            GUIs.msgPopUp("Your encrypted text is:\n"+finalText,"Done!","plain text");
        }
        Runner.programRunProgress=2;
        // 2 for after encryption
        Runner.programEndedExpectedly=true;
    }
    private static String encryptString(String s, String enteredIndex) throws Exception{
        // This is a generalized method that accepts any String with an integer
        int index=getIndex(enteredIndex); // Parses the enteredIndex to make sure it is within acceptable range

        char[] encrypted = new char[s.length()]; // Since we're doing it character by character, we'll be using a char[] for output
        // Iterating through the dictionary to search for the character
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            int n = -1;
            for (int j = 0; j < dictionary.length; j++) {
                if (dictionary[j] == c) {
                    n = j;
                    break;
                }
            }
            // If index found, encrypt it; else inform the user that characters is not accepted
            if (n != -1) {
                int encryptedIndex = (n + index) % dictionary.length;
                char encryptedChar = dictionary[encryptedIndex];
                encrypted[i] = encryptedChar;
            } else {
                String errorMessage= "Character '" +c+ "' not found within dictionary!";
                GUIs.msgPopUp(errorMessage+"\nRestarting the program...","Invalid Input!\nRestarting the program...","text");
                throw new ExpectedException(errorMessage, new IllegalStateException()); // throw new Exception if character is not found in the dictionary
            }
        }
        return new String(encrypted);
    }
    private static void decryptionMethod() throws Exception {
        String input=GUIs.textPopUp("Enter your input here: ","Input");
        if (input == null){
            Runner.userClosedWindow=true;
            Runner.programEndedExpectedly=true;
            System.exit(0);
        }
        String[] separatedInput=input.split("Ø");
        if (separatedInput.length<2){
            GUIs.msgPopUp("Your input is invalid!\nPlease restart the program. ","Bad Input!","error");
            Runner.programEndedExpectedly=true;
            System.exit(0);
        }
        try {
            System.out.println(Integer.parseInt(separatedInput[0]));
        } catch (NumberFormatException e){
            GUIs.msgPopUp("Your input is invalid!\nPlease restart the program. ","Bad Input!","error");
            throw new ExpectedException("Invalid input (Decrypt Section)", e);
        }
        StringBuilder subString=new StringBuilder();
        for (int i=separatedInput[0].length()+1;i<input.length();i++){
            subString.append(input.charAt(i));
        }
        // Decrypting first layer:
        String decryptedText= decryptString(subString.toString(), Long.parseLong(separatedInput[0]));
        // Decrypting second layer:
        String[] separatedDecryptedText=decryptedText.split("Ø");
        try {
            System.out.println(Long.parseLong(separatedDecryptedText[0]));
        } catch (NumberFormatException e){
            GUIs.msgPopUp("Your input is invalid!\nRestarting the program...","Bad Input!","error");
            throw new ExpectedException("Invalid input (Decrypt Section)", e);
        }
        StringBuilder alsoSubString=new StringBuilder();
        for (int i=separatedDecryptedText[0].length()+1;i<decryptedText.length();i++){
            alsoSubString.append(decryptedText.charAt(i));
        }
        String unencryptedText= decryptString(alsoSubString.toString(),Long.parseLong(separatedDecryptedText[0]));

        // An if-else statement that asks the user whether to copy to their clipboard
        if (GUIs.optionPopUp("Do you want to save the result to clipboard?\nThis will override your clipboard!","Copy to clipboard?")==0){
            StringSelection stringSelection = new StringSelection(unencryptedText);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(stringSelection, null);
            GUIs.msgPopUp("Your result is: \n"+(char)34+unencryptedText+(char)34+"\n(full text copied to clipboard)","Result","plain text");
        } else {
            GUIs.msgPopUp("Your result is: \n"+(char)34+unencryptedText+(char)34,"Result","plain text");
        }
        Runner.programRunProgress=6;
        // 6 is for after decryption
        Runner.programEndedExpectedly=true;
    }
    private static String decryptString (String s, long index) {
        // Generalized method that accepts any String and int.
        index=getIndex(String.valueOf(index));
        char[] decryptedArray = new char[s.length()];
        for (int i = 0; i < s.length(); i++) {// Iterate through each character of the string
            char c = s.charAt(i);
            int n = -1;
            for (int j = 0; j < dictionary.length; j++) { // Iterates through each element of the array
                if (dictionary[j] == c) { // if found, n=array_index of the element
                    n = j;
                    break;
                }
            }
            if (n != -1) {
                // This branch means found the character, now replacing with new character controlled by index
                int decryptedIndex = (int) ((n - index + dictionary.length) % dictionary.length);
                char decryptedChar = dictionary[decryptedIndex];
                decryptedArray[i] = decryptedChar;
            } else {
                throw new NullPointerException("Character ("+c+") is not found in the dictionary!");
                // Throws a NullPointerException if the character is not found in the dictionary
                // Only allows characters within the dictionary within input
            }
        }
        // Return type is String, so we return a new String().
        return new String(decryptedArray);
    }
    private static void encryptPDF(String filePath, String password) throws Exception {
        try (PDDocument document = Loader.loadPDF(new File(filePath))) {
            AccessPermission ap = new AccessPermission();
            ap.setCanPrint(false);
            ap.setCanModifyAnnotations(false);
            ap.setCanExtractContent(false);
            ap.setCanModifyAnnotations(false);
            ap.setCanFillInForm(false);
            ap.setCanExtractForAccessibility(false);
            ap.setCanAssembleDocument(true);

            StandardProtectionPolicy spp = new StandardProtectionPolicy(password, password, ap);
            document.protect(spp);
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Select a location to save to");
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int result = fileChooser.showSaveDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                File outputFile = fileChooser.getSelectedFile();
                try {
                    document.save(outputFile+".pdf");
                } catch (IOException e) {
                    GUIs.msgPopUp("Oh No! The program failed to write your PDF!\nAn email will be sent to the developer along with the error logs. ","Unexpected Program Crash!","error");
                    throw new FatalError(new RuntimeException(e));
                }
                JOptionPane.showMessageDialog(null, "PDF Encrypted Successfully!");
            } else {
                Runner.userClosedWindow=true;
            }
            Runner.programEndedExpectedly=true;
            System.exit(0);
        } catch (IOException e){
            GUIs.msgPopUp("Oh No! The program failed to write your PDF!\nAn email will be sent to the developer along with the error logs. ","Unexpected Program Crash!","error");
            throw new FatalError(e);
        }
    }
    private static void PDFBranch() throws Exception {
        Runner.programRunProgress=3;
        // 3 is for before PDF encryption
        // Add a default file filter to only accept PDF files
        fileChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
            @Override
            public boolean accept(File f) {
                if (f.isDirectory()) {
                    return true;
                }
                return f.getName().toLowerCase().endsWith(".pdf");
            }
            @Override
            public String getDescription() {
                return "PDF Files (*.pdf)";
            }
        });
        // Asks the user to select a .pdf file
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String filePath = selectedFile.getAbsolutePath();
            // Encrypting the PDF
            String password = GUIs.textPopUp("Please enter password for the PDF: ","Set a password");
            if (password==null){
                Runner.userClosedWindow=true;
                Runner.programEndedExpectedly=true;
                System.exit(0);
            }
            encryptPDF(filePath, password);
            Runner.programRunProgress=4;
            // 4 for after PDF Encryption
        } else {
            Runner.programEndedExpectedly =true;
            System.exit(0);
        }
    }
}