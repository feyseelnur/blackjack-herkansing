package nl.hu.bep2.casino.blackjack.application;

import nl.hu.bep2.casino.blackjack.data.SpringDeckRepository;
import nl.hu.bep2.casino.blackjack.data.SpringGameRepository;
import nl.hu.bep2.casino.blackjack.domain.*;
import nl.hu.bep2.casino.blackjack.presentation.dto.Game;
import nl.hu.bep2.casino.blackjack.presentation.dto.GameResult;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BlackJackGameBuilder {

    private final SpringDeckRepository deckRepository;
    private final SpringGameRepository gameRepository;

    public BlackJackGameBuilder(SpringDeckRepository deckRepository, SpringGameRepository gameRepository) {
        this.deckRepository = deckRepository;
        this.gameRepository = gameRepository;
    }

    private BlackJack blackJack;
    private Integer playerWon;
    private Game game;

    public BlackJackGameBuilder initialize(BlackJack blackJack, Integer playerWon) {
        this.blackJack = blackJack;
        this.playerWon = playerWon;
        game = new Game();
        game.setId(blackJack.getId());
        game.setChips(blackJack.getChips());
        game.setStatus(blackJack.getStatus());
        return this;
    }

    public BlackJackGameBuilder finishGame() {

        if (playerWon == null) return this;

        blackJack.setStatus(Status.END);
        gameRepository.save(blackJack);

        return this;
    }

    public BlackJackGameBuilder getResult() {
        if (playerWon == null) return this;

        game.setResult(getResult(blackJack, playerWon));
        return this;
    }

    public Game build() {
        return game;
    }

    public BlackJackGameBuilder loadDecks() {

        List<String> userDeck = new ArrayList<>();
        List<String> dealerDeck = new ArrayList<>();

        List<Deck> decks = deckRepository.findAllDecksByGameId(blackJack.getId());
        for (Deck deck : decks) {
            String card = Cards.cardsArray[deck.getCardNumber()];
            if (deck.isPlayer()) {
                userDeck.add(card);
            } else {
                dealerDeck.add(card);
            }
        }
        game.setUserDeck(userDeck);
        game.setDealerDeck(dealerDeck);

        return this;
    }

    private GameResult getResult(BlackJack game, int playerWon) {
        Result result;
        long chips;
        if (playerWon == 0) {
            result = Result.DRAW;
            chips = game.getChips();
        } else if (playerWon == 5) {
            result = Result.JACKPOT;
            chips = game.getChips() * 5;
        } else if (playerWon == 1) {
            result = Result.WIN;
            chips = game.getChips() * 2;
        } else if (playerWon == -2) {
            result = Result.GIVE_UP;
            chips = (game.getChips() / 2) * -1;
        } else {
            result = Result.LOSS;
            chips = game.getChips() * -1;
        }
        return new GameResult(chips, result);
    }
}