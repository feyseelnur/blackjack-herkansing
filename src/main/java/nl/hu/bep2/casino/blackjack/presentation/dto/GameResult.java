package nl.hu.bep2.casino.blackjack.presentation.dto;

import nl.hu.bep2.casino.blackjack.domain.Result;


public class GameResult {

    private Long chips;
    private Result result;
    private String reward;

    public GameResult(Long chips, Result result) {
        this.chips = chips;
        this.result = result;
    }

    public Long getChips() {
        return chips;
    }

    public void setChips(Long chips) {
        this.chips = chips;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public String getReward() {
        if(chips < 0) { return "Loss " + chips + " chips";}
        else if(chips > 0) { return "Won " + chips + " chips";}
        else {return "No Reward";}
    }
}