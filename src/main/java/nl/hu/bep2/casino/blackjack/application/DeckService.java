package nl.hu.bep2.casino.blackjack.application;
import nl.hu.bep2.casino.blackjack.data.SpringDeckRepository;
import nl.hu.bep2.casino.blackjack.domain.*;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

@Service
public class DeckService {

    private final SpringDeckRepository deckRepository;

    public DeckService(SpringDeckRepository deckRepository) {
        this.deckRepository = deckRepository;
    }

    public void dealInitialCards(BlackJack game) {
        final int[] newCardsArray = ThreadLocalRandom.current().ints(0, 52).distinct().limit(3).toArray();
        for (int i = 0; i < 3; i++) {
            if (i < 2)
                deckRepository.save(new Deck(newCardsArray[i], game, true)); // This is for the player's cards
            else
                deckRepository.save(new Deck(newCardsArray[i], game, false)); // This is for the dealer's card
        }
    }
}