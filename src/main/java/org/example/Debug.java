package org.example;
public class Debug {
    public static void main(String[] args){
        // Index is ranged from 0 to 114, but 0 will be parsed to 2
        for (int index=0;index<=228;index++){
            String originalText="Hello, qwertyuiopasdfghjklzxcvbnm";
            if (!originalText.equals(decryptString(encryptString(originalText, String.valueOf(index)),index))){
                System.out.println("Index "+index+" does not work!\n" +
                        "Original Text: "+originalText+"\n" +
                        "Output Text: "+decryptString(encryptString(originalText, String.valueOf(index)),index)+"\n"
                );
            }
        }
    }
    // Below is the MainBranch.java
    // final char[] that stores the most common characters you can see on a keyboard
    public final static char[] dictionary={'#','y','F','7','Z','L','r','X','j','0','s',(char)92,')','a','+','~','f','h','`','>','t','%','8','K','Ø','9','k','l','i','A','*','J','T','S','6','m','@','}','o','M','C','_',(char)39,'z','E','&','G','b','R','W','g','I','^','H','|','=','P','O','{','N','Y','2','.','v','u','5','D','"','，','。','？','！','《','》','（','）','￥',']','?','[','$',':','(','V','<','n','c','U','!','π','B','-','x','；','：','“','”','‘','’',' ',';','q','、','w','p',',','e','Q','d','3','1','4','/','','¿'};
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
