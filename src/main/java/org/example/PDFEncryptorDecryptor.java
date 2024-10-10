package org.example;

import javax.swing.*;
import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
import org.apache.pdfbox.pdmodel.encryption.StandardProtectionPolicy;

public class PDFEncryptorDecryptor {

    public static void main(String[] args) {
        // Asks the user to select a .pdf file
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String filePath = selectedFile.getAbsolutePath();

            // Encrypting the PDF
            String password = JOptionPane.showInputDialog(null, "Enter password for encryption:", "Password", 1);
            encryptPDF(filePath, password);

        }
    }

    private static void encryptPDF(String filePath, String password) {
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
            int result = fileChooser.showSaveDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                File outputFile = fileChooser.getSelectedFile();
                document.save(outputFile+".pdf");
                JOptionPane.showMessageDialog(null, "PDF Encrypted Successfully!");
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage()+";\nTry contacting zjhazjw@163.com and reporting this issue!");
        }
    }

    private static void decryptPDF(String filePath, String password) {
        try (PDDocument document = Loader.loadPDF(new File(filePath), password)) {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showSaveDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                File outputFile = fileChooser.getSelectedFile();
                document.save(outputFile+".pdf");
                JOptionPane.showMessageDialog(null, "PDF Decrypted Successfully!");
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }
}