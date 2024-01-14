package nl.hu.bep2.casino.blackjack.presentation.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import nl.hu.bep2.casino.blackjack.domain.Status;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Game {

    private Long id;
    private Long chips;
    private Status status;
    private List<String> userDeck;
    private List<String> dealerDeck;
    private GameResult result;

    public Game(){}

    public Game(Long id, Long chips, Status status, List<String> userDeck, List<String> dealerDeck) {
        this.id = id;
        this.chips = chips;
        this.status = status;
        this.userDeck = userDeck;
        this.dealerDeck = dealerDeck;
    }

    public Long getChips() {
        return chips;
    }

    public void setChips(Long chips) {
        this.chips = chips;
    }

    public List<String> getUserDeck() {
        return userDeck;
    }

    public void setUserDeck(List<String> userDeck) {
        this.userDeck = userDeck;
    }

    public List<String> getDealerDeck() {
        return dealerDeck;
    }

    public void setDealerDeck(List<String> dealerDeck) {
        this.dealerDeck = dealerDeck;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public GameResult getResult() {
        return result;
    }

    public void setResult(GameResult result) {
        this.result = result;
    }
}