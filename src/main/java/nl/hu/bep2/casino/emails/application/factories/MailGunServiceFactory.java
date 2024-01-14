package nl.hu.bep2.casino.emails.application.factories;

import nl.hu.bep2.casino.emails.application.EmailService;
import nl.hu.bep2.casino.emails.application.MailGunGateway;

public class MailGunServiceFactory extends EmailServiceFactory {
    @Override
    public EmailService createEmailService() {
        return new MailGunGateway();
    }

}