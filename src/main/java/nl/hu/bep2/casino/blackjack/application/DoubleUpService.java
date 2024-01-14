package nl.hu.bep2.casino.blackjack.application;

import nl.hu.bep2.casino.blackjack.data.SpringGameRepository;
import nl.hu.bep2.casino.blackjack.domain.BlackJack;
import nl.hu.bep2.casino.blackjack.presentation.dto.Game;
import nl.hu.bep2.casino.chips.application.ChipsService;
import nl.hu.bep2.casino.emails.application.factories.EmailServiceFactory;
import nl.hu.bep2.casino.security.application.UserService;
import nl.hu.bep2.casino.security.domain.User;
import org.springframework.stereotype.Service;

@Service
public class DoubleUpService {

    private final UserService userService;
    private final GameService gameService;
    private final ChipsService chipsService;
    private final SpringGameRepository gameRepository;
    private final EmailServiceFactory emailServiceFactory;

    public DoubleUpService(UserService userService, GameService gameService, ChipsService chipsService, SpringGameRepository gameRepository, EmailServiceFactory emailServiceFactory) {
        this.userService = userService;
        this.gameService = gameService;
        this.chipsService = chipsService;
        this.gameRepository = gameRepository;
        this.emailServiceFactory = emailServiceFactory;
    }

    public Game doubleUp(String username) {
        User user = userService.loadUserByUsername(username);
        BlackJack game = gameService.getActiveGame(user.getId());

        chipsService.withdrawChips(username, game.getChips());
        game.setChips(game.getChips() * 2);
        gameRepository.save(game);
        int playerHit = gameService.playerHit(game) == 1? 1: -1;
        return gameService.buildGame(game, playerHit, emailServiceFactory);
    }

}
