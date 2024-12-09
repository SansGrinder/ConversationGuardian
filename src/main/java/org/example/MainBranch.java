package org.example;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
public class MainBranch {
    // Gets and stores the difference between the current time and 1970 January 1st 0:00 in milliseconds
    public final static long currentTime=System.currentTimeMillis();
    // final char[] that stores the most common characters you can see on a keyboard
    public final static char[] dictionary={'#','y','F','7','Z','L','r','X','j','0','s',(char)92,')','a','+','~','f','h','`','>','t','%','8','K','Ø','9','k','l','i','A','*','J','T','S','6','m','@','}','o','M','C','_',(char)39,'z','E','&','G','b','R','W','g','I','^','H','|','=','P','O','{','N','Y','2','.','v','u','5','D','"','，','。','？','！','《','》','（','）','￥',']','?','[','$',':','(','V','<','n','c','U','!','π','B','-','x','；','：','“','”','‘','’',' ',';','q','、','w','p',',','e','Q','d','3','1','4','/','','¿'};
    public static void launch() throws Exception {
        int response=GUIs.optionPopUp("Choose a mode: ", "Select an option: ",new String[]{"Decrypt", "Select a PDF", "Encrypt"});
        if (response==-1){
            Runner.userClosedWindow=true;
            Runner.programEndedExpectedly=true;
            System.exit(0);
        }
        switch (response){
            case 2->{ // Encrypt Chosen
                Runner.programRunProgress=1;
                // 1 for Encryption
                encryptionMethod();
            }
            case 1-> PDFEncryptDecrypt.start(); // User want to select a PDF
            case 0->{ // Decrypt Chosen
                Runner.programRunProgress=5;
                // 5 is for Before Decryption
                decryptionMethod();
            }
            default->{
                // Logically impossible to reach
                // Generating silent crash report
                GUIs.msgPopUp("Error! Could not read your response!","ERROR","error");
                Runner.requestSilenceCrashReport=true;
                throw new Exception("Impossible user response ("+response+") in OnLaunch - Main Method - switch_case block");
            }
        }
    }
    private static int getIndex(String input){
        long id=Long.parseLong(input);
        id%=dictionary.length;
        if (id==0){
            id=2;
        } else if (id<0){
            id=Math.abs(id);
        }
        return (int) id;
    }
    private static void encryptionMethod(){
        String textString=GUIs.textPopUp("Enter your text here: ",(Object)"(Don't include Chinese Characters/punctuations or weird symbols!)");
        if (textString == null){
            Runner.userClosedWindow=true;
            Runner.programEndedExpectedly=true;
            System.exit(0);
        }
        try { // To see if I can encrypt the text without errors
            System.out.println(encryptString(textString,String.valueOf(currentTime)));
            // I can also not System.out.println() the message but adding so makes debugging easier
        } catch (NullPointerException e){
            GUIs.msgPopUp("Invalid Characters found in your input!\nDo not include Chinese Characters/Punctuations or any non-English symbol! Please run the code again if you want to try again. ","BAD INPUT","plain text");
            Runner.programEndedExpectedly =true;
            System.exit(0);
        }
        // Encrypting for the first time:
        String firstEncryption= currentTime +"Ø"+ encryptString(textString,String.valueOf(currentTime));
        long newIndex=0;
        for (int i=0;i<firstEncryption.length();i++){
            // Adding characters' Unicode index to make new index
            // E.g. int a='a'+'¿'; a=288 ('a'=97,'¿'=191)
            newIndex+=firstEncryption.charAt(i);
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
    private static String encryptString(String s, String enteredIndex) {
        int index=getIndex(enteredIndex);
        char[] encrypted = new char[s.length()];
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            int n = -1;
            for (int j = 0; j < dictionary.length; j++) {
                if (dictionary[j] == c) {
                    n = j;
                    break;
                }
            }
            if (n != -1) {
                int encryptedIndex = (n + index) % dictionary.length;
                char encryptedChar = dictionary[encryptedIndex];
                encrypted[i] = encryptedChar;
            } else {
                throw new NullPointerException("Character ("+c+") not found within dictionary!"); // throw new Exception if character is not found in the dictionary
            }
        }
        return new String(encrypted);
    }
    private static void decryptionMethod(){
        String input=GUIs.textPopUp("Enter your input here: ","Input");
        if (input == null){
            Runner.userClosedWindow=true;
            Runner.programEndedExpectedly=true;
            System.exit(0);
        }
        String[] separatedInput=input.split("Ø");
        if (separatedInput.length<2){
            GUIs.msgPopUp("Your input is invalid!","Bad Input!","error");
            Runner.programEndedExpectedly=true;
            System.exit(0);
        }
        try {
            System.out.println(Integer.parseInt(separatedInput[0]));
        } catch (NumberFormatException e){
            GUIs.msgPopUp("Your input is invalid!","Bad Input!","error");
            Runner.requestSilenceCrashReport=true;
            throw e;
        }
        StringBuilder subString=new StringBuilder();
        for (int i=1;i<separatedInput.length;i++){
            subString.append(separatedInput[i]);
        }
        // Decrypting first layer:
        String decryptedText= decryptString(subString.toString(), Long.parseLong(separatedInput[0]));
        System.out.println(decryptedText);
        // Decrypting second layer:
        String[] separatedDecryptedText=decryptedText.split("Ø");
        try {
            System.out.println(Long.parseLong(separatedDecryptedText[0]));
        } catch (NumberFormatException e){
            GUIs.msgPopUp("Your input is invalid!","Bad Input!","error");
            Runner.requestSilenceCrashReport=true;
            throw e;
        }
        StringBuilder alsoSubString=new StringBuilder();
        for (int i=1;i<separatedDecryptedText.length;i++){
            alsoSubString.append(separatedDecryptedText[i]);
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
}