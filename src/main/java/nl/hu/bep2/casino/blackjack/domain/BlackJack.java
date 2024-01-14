package nl.hu.bep2.casino.blackjack.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import nl.hu.bep2.casino.security.domain.User;

import java.util.List;

@Entity
@Table(name = "games")
public class BlackJack {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private boolean playersTurn;
    private long chips;
    @Enumerated(EnumType.STRING)
    private Status status;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public BlackJack() {
    }

    public BlackJack(User user,long chips) {
        super();
        this.chips = chips;
        this.user = user;
    }
    public BlackJack(boolean playersTurn, long chips, Status status, User user) {
        super();
        this.playersTurn = playersTurn;
        this.chips = chips;
        this.status = status;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isPlayersTurn() {
        return playersTurn;
    }

    public void setPlayersTurn(boolean playersTurn) {
        this.playersTurn = playersTurn;
    }

    public long getChips() {
        return chips;
    }

    public void setChips(long chips) {
        this.chips = chips;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int calculatePoints(List<Deck> decks, boolean isPlayer) {
        int points = 0;
        for (Deck deck : decks) {
            if (deck.isPlayer() == isPlayer) {
                points += Cards.getPoints(deck.getCardNumber(), points);
            }
        }
        return points;
    }
    public int whoWins(int userCount, int dealerCount) {
        if (userCount < 21) {
            if (userCount > dealerCount || dealerCount > 21) {
                return 1; // Player Wins
            } else if (userCount == dealerCount) {
                return 0; // Draw
            }
        } else if (userCount == 21) {
            if (dealerCount != 21) {
                return 5; // User BlackJack
            } else {
                return 0; // Both BlackJack
            }
        } else if (dealerCount > 21) {
            return 0; // Draw
        }
        return -1; // Dealer Win
    }
}