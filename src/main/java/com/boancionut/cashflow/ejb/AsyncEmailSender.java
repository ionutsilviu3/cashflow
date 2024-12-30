package com.boancionut.cashflow.ejb;

import jakarta.ejb.Asynchronous;
import jakarta.ejb.Stateless;
import jakarta.mail.Message.RecipientType;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import jakarta.annotation.Resource;

@Stateless
public class AsyncEmailSender {

    @Resource(name = "jboss/mail/CashFlow") // Your mail session
    private Session mailSession;

    @Asynchronous
    public void sendEmailAsync(String to, String subject, String body) {
        try {
            MimeMessage mimeMessage = new MimeMessage(mailSession);
            mimeMessage.setRecipients(RecipientType.TO, InternetAddress.parse(to));
            mimeMessage.setSubject(subject);
            mimeMessage.setText(body);

            Transport.send(mimeMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}