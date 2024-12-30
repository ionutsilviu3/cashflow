package com.boancionut.cashflow.standaloneClient;

import com.boancionut.cashflow.ejb.EmailProducer;
import com.boancionut.cashflow.ejb.model.User;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;

@Named("emailBean")
@ViewScoped
public class EmailBean implements Serializable {
    private static final long serialVersionUID = 1L;

    @Inject
    private EmailProducer emailProducer;

    private String subject;
    private String body;

    public String sendEmail(User user) {
        emailProducer.sendEmail(user, subject, body);
        return "index";
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}