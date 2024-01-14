package nl.hu.bep2.casino.emails.application.factories;

import nl.hu.bep2.casino.emails.application.EmailService;

public abstract class EmailServiceFactory {
    public abstract EmailService createEmailService();
}