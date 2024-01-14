package nl.hu.bep2.casino.blackjack.application;

import nl.hu.bep2.casino.blackjack.domain.BlackJack;
import nl.hu.bep2.casino.blackjack.presentation.dto.Game;
import nl.hu.bep2.casino.emails.application.factories.EmailServiceFactory;
import nl.hu.bep2.casino.security.application.UserService;
import nl.hu.bep2.casino.security.domain.User;
import org.springframework.stereotype.Service;

@Service
public class GiveUpService {
    private final UserService userService;
    private final GameService gameService;
    private final EmailServiceFactory emailServiceFactory;

    public GiveUpService(UserService userService, GameService gameService, EmailServiceFactory emailServiceFactory) {
        this.userService = userService;
        this.gameService = gameService;
        this.emailServiceFactory = emailServiceFactory;

    }

    public Game giveUp(String username) {
        User user = userService.loadUserByUsername(username);
        BlackJack game = gameService.getActiveGame(user.getId());
        gameService.processChips(2, game, username);
        return gameService.buildGame(game, -2, emailServiceFactory);
    }
}
