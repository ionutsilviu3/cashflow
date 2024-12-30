package com.boancionut.cashflow.ejb;

import com.boancionut.cashflow.ejb.model.Notification;
import com.boancionut.cashflow.ejb.model.User;
import jakarta.annotation.Resource;
import jakarta.ejb.Asynchronous;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.jms.JMSContext;
import jakarta.jms.Queue;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Stateless
public class EmailProducer {

    @Inject
    private JMSContext context;

    @Resource(lookup = "jms/EmailQueue")
    private Queue queue;

    @PersistenceContext
    private EntityManager entityManager;

    @Asynchronous
    public void sendEmail(User user, String subject, String body) {
        String message = user.getEmail() + ";" + subject + ";" + body;
        context.createProducer().send(queue, message);
        saveNotification(user, body);
    }

    private void saveNotification(User user, String message) {
        Notification notification = new Notification(user, message);
        entityManager.persist(notification);
    }
}