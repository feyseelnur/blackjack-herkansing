package nl.hu.bep2.casino.blackjack.domain.exception;

public class AlreadyRunningGameException extends RuntimeException {
    public AlreadyRunningGameException(String message) {
        super(message);
    }
}