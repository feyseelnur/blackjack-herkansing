package nl.hu.bep2.casino.blackjack.presentation.dto;

import jakarta.validation.constraints.Positive;

public class BetParam {
    @Positive
    public Long chips;
}