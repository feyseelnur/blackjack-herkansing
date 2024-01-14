package nl.hu.bep2.casino.common.exceptions;

import nl.hu.bep2.casino.blackjack.domain.exception.AlreadyRunningGameException;
import nl.hu.bep2.casino.blackjack.domain.exception.GameNotFoundException;
import nl.hu.bep2.casino.blackjack.presentation.controller.BlackJackController;
import nl.hu.bep2.casino.blackjack.presentation.dto.GameResponse;
import nl.hu.bep2.casino.chips.domain.exception.NegativeNumberException;
import nl.hu.bep2.casino.chips.domain.exception.NotEnoughChipsException;
import nl.hu.bep2.casino.emails.application.EmailService;
import nl.hu.bep2.casino.emails.application.factories.EmailServiceFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice(assignableTypes = BlackJackController.class)
public class BlackJackControllerAdvice {

    private final EmailService emailService;

    public BlackJackControllerAdvice(EmailServiceFactory emailServiceFactory) {
        this.emailService = emailServiceFactory.createEmailService();
    }

    private void sendExceptionEmail() {
        emailService.sendExceptionEmail();
    }
    @ExceptionHandler(value = { NegativeNumberException.class, NotEnoughChipsException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public GameResponse handleChipExceptions(Exception ex, WebRequest request) {
        sendExceptionEmail();
        return new GameResponse("failure", ex.getMessage(), null);
    }

    @ExceptionHandler(value = { GameNotFoundException.class })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public GameResponse handleGameNotFoundException(GameNotFoundException ex, WebRequest request) {
        sendExceptionEmail();
        return new GameResponse("failure", ex.getMessage(), null);
    }
    @ExceptionHandler(value = { AlreadyRunningGameException.class })
    @ResponseStatus(HttpStatus.CONFLICT)
    public GameResponse handleAlreadyRunningGameException(AlreadyRunningGameException ex, WebRequest request) {
        sendExceptionEmail();
        return new GameResponse("failure", ex.getMessage(), null);
    }

    @ExceptionHandler(value = { UsernameNotFoundException.class })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public GameResponse handleUsernameNotFoundException(UsernameNotFoundException ex, WebRequest request) {
        sendExceptionEmail();
        return new GameResponse("failure", ex.getMessage(), null);
    }
}