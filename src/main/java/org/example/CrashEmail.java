package org.example;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

public class CrashEmail {
    private final String title;
    private final String content;

    public CrashEmail(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void sendAnEmail() {
        // Setting properties
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.163.com"); // 163's SMTP address (given by mail.163.com)
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.ssl.enable", "true");
        // New Session
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                        return new javax.mail.PasswordAuthentication("IBCSIAPDFBot@163.com", "DQfBq83PFbK9tTDf");
                        // Uses the code provided by mail.163.com to replace password
                    }
                });
        boolean doNotStop=true;
        while (doNotStop) {
            try {
                // Create the mail:
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress("IBCSIAPDFBot@163.com","Gordon's encryption bot")); // Sender address
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("jiahua.zhang@student.keystoneacademy.cn")); // Recipient address
                message.setSubject(title); // Set mail title to passed parameter

                // Create a MimeBodyPart for content
                MimeBodyPart textPart = new MimeBodyPart();
                textPart.setText(content); // Set mail content to passed parameter

                // Create a MimeBodyPart for attachments
                MimeBodyPart attachmentPart = new MimeBodyPart();
                attachmentPart.attachFile(System.getProperty("user.home") + "/Downloads/crash-log-at-" + CrashExport.formattedTime + ".log");

                // Crating a MimeMultipart
                MimeMultipart multipart = new MimeMultipart();
                multipart.addBodyPart(textPart); // Adding text
                multipart.addBodyPart(attachmentPart); // Adding attachment

                // Set the content to what's been built
                message.setContent(multipart);
                // Send the message
                Transport.send(message);
                // Print a message for testing/debugging purposes
                System.out.println("Sent message successfully...");
                doNotStop=false;
            } catch (Exception e) {
                // Logic
                int option = GUIs.optionPopUp("Please check your internet connection and click Try Again\n" +
                        "A crash log has been saved to your Downloads folder. You can also send the crash log to Gordon manually",
                        "Failed to send email!",new String[]{"Try again","Send the log manually"});
                doNotStop = option==0;
                GUIs.expectedProgramCrash(e);
            }
        }
    }
}