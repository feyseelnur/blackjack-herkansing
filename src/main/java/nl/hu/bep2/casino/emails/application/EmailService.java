package nl.hu.bep2.casino.emails.application;

import org.springframework.beans.factory.annotation.Value;

public abstract class EmailService {

    @Value("${application.exceptionEmail}")
    public String exceptionEmail;

    public abstract void sendEmail();
    public abstract void sendExceptionEmail();

}