package com.genkey.foodmgt.services.impl;

import com.genkey.foodmgt.model.impl.PasswordResetToken;
import com.genkey.foodmgt.model.impl.Users;
import com.genkey.foodmgt.repository.dao.api.UserDAO;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

@Async
@Component
public class MailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private VelocityEngine velocityEngine;

    @Autowired
    UserDAO userDAO;

//    public void sendPasswordResetEmail(Users user, PasswordResetToken passwordResetToken) {
//        String subject = "Password Reset Request";
//        String text = "Hello " + user.getUsername() + ",\n\nYou have requested to reset your password. Please copy the reset token and click the link below to reset your password:\n\n" +

//              "https://80.246.199.24:9449/reset-password?token=" + passwordResetToken.getToken() + "\n\n" +
//                "This link will expire in 30 minutes.\n\n" +
//                "If you did not request this password reset, please ignore this email.\n\n" +
//                "Best regards,\n" +
//                "Genkey foodmgt App";
//        sendSimpleMessage(user.getEmail(), subject, text);
//    }

    private void sendSimpleMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }

    public void sendEmail(String toEmail, Users users) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        String subject = "Genkey food Management App";
        String message = "You have been successfully registered to our Food App, your username is "+ " " + users.getUsername() +
                " and your password is " + users.getPassword() + "\n"
                + "click on this link to reset your password http://foodmgtdeployment-env.eba-3bpy2wp2.us-east-1.elasticbeanstalk.com ";
        mailMessage.setTo(toEmail);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);

        mailMessage.setFrom("Genkey@gmail.com");

        mailSender.send(mailMessage);
    }

    public void sendEmailReminder(String toEmail) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        String subject = "Genkey food Management App";
        String message = "Hello!\n" +
                "The menu for this week has been uploaded, kindly place your orders via this link:\n " +
                "http://foodmgtdeployment-env.eba-3bpy2wp2.us-east-1.elasticbeanstalk.com";
        mailMessage.setTo(toEmail);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);

        mailMessage.setFrom("Genkey@gmail.com");

        mailSender.send(mailMessage);
    }

    public void sendEmailUsingVelocityTemplate() {
        List<Users> users = userDAO.findAll();
        for (Users u : users) {
            MimeMessagePreparator preparator = new MimeMessagePreparator() {
                public void prepare(MimeMessage mimeMessage) throws Exception {
                    MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
                    VelocityContext velocityContext = new VelocityContext();
                    velocityContext.put("name", u.getFirstname());
                    String subject = "Genkey food Management App";
                    message.setTo(u.getEmail());
                    message.setFrom(new InternetAddress("GenkeyAfrica@gmail.com"));

                    StringWriter stringWriter = new StringWriter();

                    velocityEngine.mergeTemplate("velocity/new-menu-email-template.vm", "UTF-8", velocityContext, stringWriter);

                    message.setSubject(subject);
                    message.setText(stringWriter.toString(), true);
                }
            };
            try {
                mailSender.send(preparator);

                System.out.println("Email sending complete for menu to " + u.getEmail());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void sendUpdatedMenuEmails() {
        List<Users> users = userDAO.findAll();
        for (Users u : users) {
            MimeMessagePreparator preparator = new MimeMessagePreparator() {
                public void prepare(MimeMessage mimeMessage) throws Exception {
                    MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
                    VelocityContext velocityContext = new VelocityContext();
                    velocityContext.put("name", u.getFirstname());
                    String subject = "Genkey food Management App";
                    message.setTo(u.getEmail());
                    message.setFrom(new InternetAddress("GenkeyAfrica@gmail.com"));

                    StringWriter stringWriter = new StringWriter();

                    velocityEngine.mergeTemplate("velocity/update-menu-email-template.vm", "UTF-8", velocityContext, stringWriter);

                    message.setSubject(subject);
                    message.setText(stringWriter.toString(), true);
                }
            };
            try {
                mailSender.send(preparator);

                System.out.println("Email sending complete for updated menu to " + u.getEmail());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }



    public void sendEmailUsingVelocityTemplate1(String email, String password, String username) {

        MimeMessagePreparator preparator = new MimeMessagePreparator() {
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
                VelocityContext velocityContext = new VelocityContext();

                message.setTo(email);
                    velocityContext.put("name", username);
                    velocityContext.put("password",password);

                String subject = "Genkey food Management App";
                message.setFrom(new InternetAddress("GenkeyAfrica@gmail.com"));


                StringWriter stringWriter = new StringWriter();

                velocityEngine.mergeTemplate("velocity/email-template12.vm", "UTF-8", velocityContext, stringWriter);

                message.setSubject(subject);
                message.setText(stringWriter.toString(), true);
            }
        };
        try {
            mailSender.send(preparator);

            System.out.println("Email sending complete for user creation.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void sendPasswordResetEmail(Users user, PasswordResetToken passwordResetToken) {

        MimeMessagePreparator preparator = new MimeMessagePreparator() {
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
                VelocityContext velocityContext = new VelocityContext();

                message.setTo(user.getEmail());
                velocityContext.put("name", user.getFirstname());
                velocityContext.put("passwordResetToken",passwordResetToken.getToken());

                String subject = "Genkey food Management App";
                message.setFrom(new InternetAddress("GenkeyAfrica@gmail.com"));


                StringWriter stringWriter = new StringWriter();

                velocityEngine.mergeTemplate("velocity/password-reset-email-template.vm", "UTF-8", velocityContext, stringWriter);

                message.setSubject(subject);
                message.setText(stringWriter.toString(), true);
            }
        };
        try {
            mailSender.send(preparator);

            System.out.println("Email sending complete for password reset!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

