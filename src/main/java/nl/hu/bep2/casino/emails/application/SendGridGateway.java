package nl.hu.bep2.casino.emails.application;

import nl.hu.bep2.casino.emails.application.EmailService;

public class SendGridGateway extends EmailService {

    @Override
    public void sendEmail() {
        System.out.println("Email is being sent via SendGrid");
    }

    @Override
    public void sendExceptionEmail() {

        System.out.println("Exception Email is being sent to " + exceptionEmail +" via SendGrid");
    }
}