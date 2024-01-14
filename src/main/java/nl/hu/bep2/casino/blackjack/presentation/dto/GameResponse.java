package nl.hu.bep2.casino.blackjack.presentation.dto;


public class GameResponse{

    public static final String INVALID = "invalid";
    public static final String VALID = "valid";

    public String status;
    public String message;
    public Object data;

    public GameResponse(String status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public static GameResponse INVALID(String message) {
        return new GameResponse(INVALID,message, null);
    }

    public static GameResponse VALID(String message) {
        return new GameResponse(VALID,message, null);
    }

    public static GameResponse VALID(String message, Object data) {
        return new GameResponse(VALID,message, data);
    }


}