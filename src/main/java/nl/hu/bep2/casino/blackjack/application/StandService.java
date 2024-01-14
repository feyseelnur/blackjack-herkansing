package nl.hu.bep2.casino.blackjack.application;
import nl.hu.bep2.casino.blackjack.domain.BlackJack;
import nl.hu.bep2.casino.blackjack.presentation.dto.Game;
import nl.hu.bep2.casino.emails.application.factories.EmailServiceFactory;
import nl.hu.bep2.casino.security.application.UserService;
import nl.hu.bep2.casino.security.domain.User;
import org.springframework.stereotype.Service;

@Service
public class StandService {

    private final UserService userService;
    private final GameService gameService;
    private final EmailServiceFactory emailServiceFactory;

    public StandService(UserService userService, GameService gameService, EmailServiceFactory emailServiceFactory) {
        this.userService = userService;
        this.gameService = gameService;
        this.emailServiceFactory = emailServiceFactory;

    }

    public Game stand(String username) {
        User user = userService.loadUserByUsername(username);

        BlackJack game = gameService.getActiveGame(user.getId());

        int playerWon = gameService.dealersTurn(game);
        if (playerWon == 1 || playerWon == 0 || playerWon == 5) { //
            gameService.processChips(playerWon, game, username);
        }
        return gameService.buildGame(game, playerWon, emailServiceFactory);

    }


}
