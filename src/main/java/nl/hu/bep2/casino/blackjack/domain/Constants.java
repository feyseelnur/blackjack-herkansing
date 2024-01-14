package nl.hu.bep2.casino.blackjack.domain;

public class Constants {
    public static final String DECK = "Deck: ";
    public static final String USER = "Player";
    public static final String DEALER = "Dealer";
    public static final String SPACE = " ";
    public static final String NEW_LINE = "\n";
    public static final String NEW_GAME = "New game is started";
    public static final String NO_RUNNING_GAME = "No running game is present";
    public static final String GAME_ALREADY_RUNNING = "A Game is already running";
    public static final String GAME_WON = "You have won the game";
    public static final String GAME_DRAW = "The game is a draw";
    public static final String GAME_LOST = "You have lost the game";
    public static final String GAME_GIVEN_UP = "You have given up the game";
    public static final String CHIPS = "chips";
    public static final String DOUBLE_UP = "You have doubled the chips";
    public static final String INSUFFICIENT_BALANCE = "You have insufficient balance";
    public static final String BLACK_JACK = "BlackJack";

    public static String displayWin(long chips) {
        return GAME_WON + NEW_LINE + "You have won " + chips + SPACE + CHIPS;
    }

    public static String displayLoss(long chips) {
        return GAME_LOST + NEW_LINE + "You have lost " + chips + SPACE + CHIPS;
    }

    public static String displayDraw(long chips) {
        return GAME_DRAW + NEW_LINE + "You have got " + chips + SPACE + CHIPS;
    }

    public static String displayGiveUp(long chips) {
        return GAME_GIVEN_UP + NEW_LINE + "You have got " + chips + SPACE + CHIPS;
    }
}