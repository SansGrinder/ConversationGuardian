package org.example;

import javax.swing.*;
import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
import org.apache.pdfbox.pdmodel.encryption.StandardProtectionPolicy;

public class PDFEncryptDecrypt {
    private static final JFileChooser fileChooser = new JFileChooser();
    public static void start(){
        Runner.programRunProgress=3;
        // 3 is for before PDF encryption
        SwingUtilities.invokeLater(() -> {
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
                encryptPDF(filePath, password);
                Runner.programRunProgress=4;
                // 4 for after PDF Encryption
            } else {
                Runner.programEndedExpectedly =true;
                System.exit(0);
            }
        });
    }
    private static void encryptPDF(String filePath, String password){
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
                document.save(outputFile+".pdf");
                JOptionPane.showMessageDialog(null, "PDF Encrypted Successfully!");
            }
            Runner.programEndedExpectedly=true;
            System.exit(0);
        } catch (IOException e){

            GUIs.programCrashed(e);
        }
    }
}