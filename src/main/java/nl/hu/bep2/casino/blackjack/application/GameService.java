package nl.hu.bep2.casino.blackjack.application;

import jakarta.transaction.Transactional;
import nl.hu.bep2.casino.blackjack.data.SpringDeckRepository;
import nl.hu.bep2.casino.blackjack.data.SpringGameRepository;
import nl.hu.bep2.casino.blackjack.domain.*;
import nl.hu.bep2.casino.blackjack.domain.exception.AlreadyRunningGameException;
import nl.hu.bep2.casino.blackjack.domain.exception.GameNotFoundException;
import nl.hu.bep2.casino.blackjack.presentation.dto.Game;
import nl.hu.bep2.casino.chips.application.	ChipsService;
import nl.hu.bep2.casino.emails.application.EmailService;
import nl.hu.bep2.casino.emails.application.factories.EmailServiceFactory;
import nl.hu.bep2.casino.security.application.UserService;
import nl.hu.bep2.casino.security.domain.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;


@Service
public class GameService {

    private final SpringGameRepository gameRepository;
    private final SpringDeckRepository deckRepository;
    private final ChipsService chipsService;
    private final UserService userService;
    private final DeckService deckService;
    private final BlackJackGameBuilder blackJackGameBuilder;
    private final EmailServiceFactory emailServiceFactory;


    public GameService(ChipsService chipsService, UserService userService, DeckService deckService, BlackJackGameBuilder blackJackGameBuilder, SpringGameRepository gameRepository, EmailServiceFactory emailServiceFactory, SpringDeckRepository deckRepository) {
        this.chipsService = chipsService;
        this.gameRepository = gameRepository;
        this.blackJackGameBuilder = blackJackGameBuilder;
        this.deckRepository = deckRepository;
        this.emailServiceFactory = emailServiceFactory;
        this.userService = userService;
        this.deckService = deckService;
    }

    public int drawANewCard(BlackJack game) {
        boolean newCardFound = false;
        int newCard = 0;
        List<Deck> decks = deckRepository.findAllDecksByGameId(game.getId());
        while (!newCardFound) {
            newCardFound = true;
            newCard = ThreadLocalRandom.current().ints(0, 52).distinct().limit(1).toArray()[0];
            for (Deck deck : decks) {
                if (deck.getCardNumber() == newCard) {
                    newCardFound = false;
                    break;
                }
            }
        }
        return newCard;
    }


    @Transactional
    public int dealersTurn(BlackJack game) {
        List<Deck> decks = deckRepository.findAllDecksByGameId(game.getId());

        int userCount = game.calculatePoints(decks, true);
        int dealerCount = game.calculatePoints(decks, false);

        int newCard;
        while (dealerCount <= 21) {
            newCard = drawANewCard(game);
            Deck dealerDeck = new Deck(newCard, game, false);
            deckRepository.save(dealerDeck);
            dealerCount += Cards.getPoints(newCard, dealerCount);
            int result = game.whoWins(userCount, dealerCount);
            if (result == -1) {
                return -1; // Dealer Wins
            } else if (result == 0 && dealerCount == 21) {
                return 0; // Both are BlackJack
            }
        }

        return game.whoWins(userCount, dealerCount); // Player Wins (Normal or BlackJack)
    }

    public int playerHit(BlackJack game) {
        List<Deck> decks = deckRepository.findAllDecksByGameId(game.getId());
        int userCount = game.calculatePoints(decks, true);

        int newCard = drawANewCard(game);
        Deck userDeck = new Deck(newCard, game, true);
        deckRepository.save(userDeck);

        userCount += Cards.getPoints(newCard, userCount);
        if (hasPlayerBusted(userCount)) {
            return -1;
        } else {
            return 1;
        }
    }
    public boolean hasPlayerBusted(int points) {
        return points > 21;
    }

    /**
     *
     * @param playerWon    (1) -> Won (0) -> Draw (2) -> Withdraw (5) -> BlackJack
     * @param game game
     * @param username username
     */
    public void processChips(int playerWon, BlackJack game, String username) {
        if (playerWon == 1) {
            chipsService.depositChips(username, game.getChips() * 2);
        } else if (playerWon == 0) {
            chipsService.depositChips(username, game.getChips());
        } else if (playerWon == 2) {
            chipsService.depositChips(username, game.getChips() / 2);
        } else if (playerWon == 5) {
            chipsService.depositChips(username, game.getChips() * 5);
        }
    }

    public Game getCurrentGameStatus(String username) {
        User user = userService.loadUserByUsername(username);
        BlackJack game = getActiveGame(user.getId());

        return buildGame(game, null, emailServiceFactory);
    }

    @Transactional
    public Game newGame(String username, long chips) {
        User user = userService.loadUserByUsername(username);
        checkIfAnyGameIsAlreadyRunning(user);

        BlackJack game = new BlackJack(user, chips); // Create new game with business behavior constructor
        chipsService.withdrawChips(user.getUsername(), chips);

        gameRepository.save(game); // Persist the new game

        deckService.dealInitialCards(game);

        return buildGame(game, null, emailServiceFactory);
    }

    private void checkIfAnyGameIsAlreadyRunning(User user) {
        BlackJack game = gameRepository.findLastGame(user.getId());
        if(game != null && game.getStatus() != Status.END) {
            throw new AlreadyRunningGameException(Constants.GAME_ALREADY_RUNNING);

        }
    }

    public BlackJack getActiveGame(Long userId) {

        BlackJack game = gameRepository.findLastGame(userId);
        if(game == null || game.getStatus() == Status.END) {
            throw new GameNotFoundException(Constants.NO_RUNNING_GAME);
        }
        return game;
    }

    public Game buildGame(BlackJack game, Integer playerWon, EmailServiceFactory emailServiceFactory) {
        if (game == null) return null;

        Game blackJackGame = blackJackGameBuilder
                .initialize(game, playerWon)
                .loadDecks()
                .finishGame()
                .getResult()
                .build();

        EmailService emailService = emailServiceFactory.createEmailService();
        emailService.sendEmail();
        return blackJackGame;
    }
}