package org.example;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.io.IOException;
public class OnLaunch {
    public final static long time=System.currentTimeMillis();
    public final static char[] dictionary={'#','y','F','7','Z','L','r','X','j','0','s',(char)92,')','a','+','~','f','h','`','>','t','%','8','K','9','k','l','$','i','A','*','J','T','S','6','m','@','}','o','M','C','_',(char)39,'z','E','&','G','b','R','W','g','I','^','H','|','=','P','O','{','N','Y','2','.','v','u','5','D','"',']','?','[',':','(','V','<','n','c','U','!','B','-','x',' ',';','q','w','p',',','e','Q','d','3','1','4','/'};
    public static void main(String[] args) throws IOException {
        usefulFunctions f = new usefulFunctions();
        f.clearScreen(100);
        System.out.println(time);
        int a=Integer.parseInt(String.valueOf((int)recurring(time)*1000000000*10));
        a%=26;
        if (a<0){
            a=Math.abs(a);
        } else if (a==0){
            a=2;
        }
        System.out.println(a);
        int response=-1;
        while (response==-1){
            response=f.optionPopUp("Choose a mode: ", "Select an option: ",new String[]{"Decrypt", "Select a file", "Encrypt"});
        }
        if (response==2){
            String testString=null;
            while (testString == null){
                testString=f.textPopUp("Enter your text here: ",null,"(Don't include Chinese Characters/punctuations or weird symbols!)");
            }
            try {
                System.out.println(encrypt(testString,a));
            } catch (NullPointerException e){
                f.msgPopUp("Invalid Characters found in your input!\nDo not include Chinese Characters/Punctuations or any non-English symbol! Please run the code again if you want to try again. ","BAD INPUT","plain text",null);
                System.exit(0);
            }
            f.msgPopUp("Your encrypted text is now copied to your clipboard!","Done!","plain text",null);
            StringSelection stringSelection = new StringSelection(time+"\n"+encrypt(testString,a));
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(stringSelection, null);
        } else if (response==1){
            boolean doneWithGUI=false;
            boolean doneWithInput=false;
            String directory="";
            while (!(doneWithGUI||doneWithInput)){
                while (!doneWithGUI){
                    directory=selectFile();
                    if (directory==null){
                        a=f.optionPopUp("You didn't select a file, or an error has occurred!","Failed to get file!",new String[]{"Manually Input Directory","Try Again"});
                        if (a!=1){
                            break;
                        }
                    } else {
                        boolean txt=isTxtFile(directory);
                        if (!txt){
                            int p=f.optionPopUp("Your selected file was not in .txt format!","Wrong file?",new String[]{"Create a .txt file at my directory","Select another file"});
                            if (p!=1){
                                String[] help=directory.split("/");
                                String[] helper=new String[help.length-1];
                                System.arraycopy(help,0,helper,0,help.length-1);
                                StringBuilder parent= new StringBuilder();
                                int in=0;
                                for (String S:helper){
                                    parent.append("/");
                                    parent.append(helper[in]);
                                    in++;
                                }
                                File file = new File(String.valueOf(parent), "Plain Text File.txt");
                                if (!file.exists()){
                                    file.createNewFile();
                                } else {
                                    usefulFunctions.overrideFile(String.valueOf(parent),"");
                                }
                                f.msgPopUp("DO NOT CLOSE THIS WINDOW\nAn empty .txt file has been created at your selected directory\nPlease now open and paste the text to be decrypted into the .txt file, without renaming it or moving it to another directory\nClosing or pressing OK on this window will assume you finished","DO NOT CLOSE THIS WINDOW","warning",null);
                                doneWithGUI=true;
                            }
                        } else {
                            doneWithGUI=true;
                        }
                    }
                }
                while (!doneWithInput&&!doneWithGUI){
                    String t;
                    do {
                        t = f.textPopUp("Input your directory below: ", "Directory Input", "Example: /Users/jiahua.zhang/Downloads/thisIsYourFileName.txt");
                    } while (t == null);
                    if (isTxtFile(t)){
                        directory=t;
                        doneWithInput=true;
                    } else {
                        a=f.optionPopUp("You didn't select a txt file, or an error has occurred!","Failed to get file!",new String[]{"Select a file","Try Again"});
                        if (a!=1){
                            break;
                        }
                    }
                }
            }
            String[] data=usefulFunctions.readSavedData(directory);
            String[] content = new String[0];
            String[] decryptedArray = new String[0];
            try{
                content=new String[data.length-1];
                decryptedArray=new String[data.length-1];
            } catch (NullPointerException ignored){
                f.msgPopUp("An Error Has Occurred: Couldn't read anything from your selected file! Is the file empty?","No Contents Read","warning",null);
                System.exit(0);
            }
            double time=0;
            try{
                time=Double.parseDouble(data[0]);
            } catch (Exception ignored){
                f.msgPopUp("Wrong Input Format! Launch the program again to try again","Bad Input!","error",null);
                System.exit(0);
            }
            System.arraycopy(data,1,content,0,data.length-1);
            int index=Integer.parseInt(String.valueOf((int) recurring(time)*1000000000*10));
            index%=26;
            if (index<0){
                index=Math.abs(index);
            }
            boolean containsIllegalCharacters=false;
            int count=0;
            for (String s:content){
                try {
                    decryptedArray[count]=decrypt(s, index);
                } catch (NullPointerException ignored){
                    containsIllegalCharacters=true;
                    break;
                }
                count++;
            }
            if (containsIllegalCharacters){
                f.msgPopUp("Why are you trying to hack the system?","=(","error",null);
                System.exit(0);
            } else {
                StringBuilder returnValue=new StringBuilder();
                for (String st:decryptedArray){
                    returnValue.append(st).append("\n");
                }
                usefulFunctions.overrideFile(directory,String.valueOf(returnValue));
            }
        } else {
            String input=f.textPopUp("Enter your input here: ","Input");
            if (input.length()<15){
                f.msgPopUp("Wrong Input Format! Launch the program again to try again","Bad Input!","error",null);
                System.exit(0);
            }
            StringBuilder subString= new StringBuilder();
            StringBuilder temp= new StringBuilder();
            for (int i=0;i<13;i++){
                temp.append(input.charAt(i));
            }
            long time=Long.parseLong(temp.toString());
            int index=Integer.parseInt(String.valueOf((int) recurring(time)*1000000000*10));
            for (int i=14;i<input.length();i++){
                subString.append(input.charAt(i));
            }
            index%=26;
            if (index<0){
                index=Math.abs(index);
            }
            String decrypted=decrypt(subString.toString(),index);
            System.out.println(decrypted);
            StringSelection stringSelection = new StringSelection(decrypted);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(stringSelection, null);
            f.msgPopUp("Your result is\n"+(char)34+decrypted+(char)34+"\n(full text copied to clipboard)","Result","plain text",null);
        }
    }
    public static double recurring(double a) {
        if (a > 1145) {
            return a / 1145;
        } else {
            a *= 3;
            a -= 5;
            a *= 5;
            a += 3;
            a /= 7.1;
            return recurring(a * 11.45141919810);
        }
    }
    public static String encrypt(String s, int index) {
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
                throw new NullPointerException("=("); // Return null if character is not found in the dictionary
            }
        }
        return new String(encrypted);
    }
    public static String decrypt(String s, int index) {
        char[] decryptedArray = new char[s.length()];
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
                int decryptedIndex = (n - index + dictionary.length) % dictionary.length;
                char decryptedChar = dictionary[decryptedIndex];
                decryptedArray[i] = decryptedChar;
            } else {
                throw new NullPointerException("=("); // Return null if character is not found in the dictionary
            }
        }
        return new String(decryptedArray);
    }
    public static boolean isTxtFile(String filePath) {
        File file = new File(filePath);
        if (file.exists() && file.isFile()) {
            String fileName = file.getName();
            String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1);
            return fileExtension.equalsIgnoreCase("txt");
        }
        return false;
    }
    public static String selectFile() {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", "txt");
        fileChooser.setFileFilter(filter);

        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            return selectedFile.getPath();
        }
        return null;
    }
}