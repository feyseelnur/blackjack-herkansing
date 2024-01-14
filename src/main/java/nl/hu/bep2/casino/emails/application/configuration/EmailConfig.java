package nl.hu.bep2.casino.emails.application.configuration;

import nl.hu.bep2.casino.emails.application.SendGridGateway;
import nl.hu.bep2.casino.emails.application.factories.EmailServiceFactory;
import nl.hu.bep2.casino.emails.application.factories.MailGunServiceFactory;
import nl.hu.bep2.casino.emails.application.factories.SendGridServiceFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmailConfig {

    @Value("${email.factoryType}")
    private String factoryType;
    public static final String MAILGUN_FACTORY_TYPE = "mailgun";
    public static final String SENDGRID_FACTORY_TYPE = "sendgrid";
    @Bean
    public EmailServiceFactory emailServiceFactory() {
        return switch (factoryType.toLowerCase()) {
            case MAILGUN_FACTORY_TYPE -> new MailGunServiceFactory();
            case SENDGRID_FACTORY_TYPE -> new SendGridServiceFactory();
            default -> throw new IllegalArgumentException("Invalid email factory type: " + factoryType);
        };
    }
}