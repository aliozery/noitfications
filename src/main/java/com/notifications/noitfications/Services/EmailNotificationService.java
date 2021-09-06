package com.notifications.noitfications.Services;
import com.notifications.noitfications.Config.QueueConfig;
import com.notifications.noitfications.Models.BroadcastMessage;
import com.notifications.noitfications.Models.IndividualMessage;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.stream.Stream;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Service
public class EmailNotificationService {

    //
    //Configure sender email to vodafone "DoNotReply" email instead of placeholder email
    //



    //Sends a broadcast message to multiple users (EMAIL)
    public void broadcastEmail(List<String> emails, String messageString){
        // Mention the Recipient's email address
        String to = emails.get(0);
        // Mention the Sender's email address
        String from = "no.reply.testing.bot@gmail.com";
        // Mention the SMTP server address. Below Gmail's SMTP server is being used to send email
        String host = "smtp.gmail.com";
        // Get system properties
        Properties properties = System.getProperties();
        // Setup mail server
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");
        // Get the Session object.// and pass username and password
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("no.reply.testing.bot@gmail.com", "Emailpassword1");
            }
        });
        // Used to debug SMTP issues
        session.setDebug(true);
        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);
            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));
            // Set To: header field of the header.
            for(int i=0;i<emails.size();i++){
                to= emails.get(i);
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
                // Set Subject: header field
                message.setSubject("Broadcast Message");
                // Now set the actual message
                message.setText(messageString);
                System.out.println("sending...");
                // Send message
                Transport.send(message);
                System.out.println("Sent message successfully...."); //ADD MESSAGE SENT CONFIRMATION HERE

            }
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }


    @RabbitListener(queues = QueueConfig.QUEUE)
    public void helper_individual_email(IndividualMessage individualMessage){
        userNotification(individualMessage.getEmail(), individualMessage.getMessage(), individualMessage.getSubject());
    }

    @RabbitListener(queues = QueueConfig.QUEUE_BC)
    public void helper_broadcast_message(BroadcastMessage bc){
        List <String> emails= bc.getEmails();
        String message= bc.getMessage();
        broadcastEmail(emails, message);;
    }

    //Send individual message to single user (EMAIL)
    public void userNotification(String recipient, String message_content, String messageSubject){
        // Mention the Recipient's email address
        String to = recipient;
        // Mention the Sender's email address
        String from = "no.reply.testing.bot@gmail.com";
        // Mention the SMTP server address. Below Gmail's SMTP server is being used to send email
        String host = "smtp.gmail.com";
        // Get system properties
        Properties properties = System.getProperties();
        // Setup mail server
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");
        // Get the Session object.// and pass username and password
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("no.reply.testing.bot@gmail.com", "Emailpassword1");
            }
        });
        // Used to debug SMTP issues
        session.setDebug(true);
        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);
            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));
            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            // Set Subject: header field
            message.setSubject(messageSubject);
            // Now set the actual message
            message.setText(message_content);
            System.out.println("sending...");
            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
        
    }


    //Testing only
    public static void main(String[]args){
        List<String> emails = new ArrayList<>();
        emails.add("ali.ozery@gmail.com");
        emails.add("ali.ozery2@gmail.com");
        emails.add("ali.ozery3@gmail.com");
        String message= "This is a Broadcast Message";


        //
        //make methods static if you test in this class only
        //

        // broadcastEmail(emails,message);

        //userNotification(emails.get(0), "Individual message", "Subject Individual");



    }
}
