package nl.hu.bep2.casino.emails.application;


public class MailGunGateway extends EmailService {

    @Override
    public void sendEmail() {
        System.out.println("Email is being sent via Mail Gun");
    }

    @Override
    public void sendExceptionEmail() {
        System.out.println("Exception Email is being sent to " + exceptionEmail +" via MailGun");
    }
}