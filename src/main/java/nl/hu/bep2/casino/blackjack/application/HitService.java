package nl.hu.bep2.casino.blackjack.application;

import nl.hu.bep2.casino.blackjack.domain.BlackJack;
import nl.hu.bep2.casino.blackjack.presentation.dto.Game;
import nl.hu.bep2.casino.emails.application.factories.EmailServiceFactory;
import nl.hu.bep2.casino.security.application.UserService;
import nl.hu.bep2.casino.security.domain.User;
import org.springframework.stereotype.Service;

@Service
public class HitService {

    private final UserService userService;
    private final GameService gameService;
    private final EmailServiceFactory emailServiceFactory;

    public HitService(UserService userService, GameService gameService, EmailServiceFactory emailServiceFactory) {
        this.userService = userService;
        this.gameService = gameService;
        this.emailServiceFactory = emailServiceFactory;

    }


    public Game hit(String username) {
        User user = userService.loadUserByUsername(username);
        BlackJack game = gameService.getActiveGame(user.getId());
        int playerHit = gameService.playerHit(game) == 1? 1: -1;
        return gameService.buildGame(game, playerHit, emailServiceFactory);
    }
}
