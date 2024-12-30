package com.boancionut.cashflow.ejb;

import jakarta.ejb.ActivationConfigProperty;
import jakarta.ejb.MessageDriven;
import jakarta.ejb.EJB;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.MessageListener;
import jakarta.jms.TextMessage;

@MessageDriven(activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "jms/EmailQueue"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "jakarta.jms.Queue"),
        @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge")
})
public class EmailMDB implements MessageListener {

    @EJB
    private AsyncEmailSender asyncEmailSender;

    @Override
    public void onMessage(Message message) {
        try {
            if (message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) message;
                String[] emailData = textMessage.getText().split(";");
                String recipient = emailData[0];
                String subject = emailData[1];
                String body = emailData[2];

                asyncEmailSender.sendEmailAsync(recipient, subject, body);
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}