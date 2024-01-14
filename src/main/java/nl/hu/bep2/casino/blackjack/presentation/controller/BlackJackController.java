package nl.hu.bep2.casino.blackjack.presentation.controller;
import nl.hu.bep2.casino.blackjack.application.*;
import nl.hu.bep2.casino.blackjack.domain.Constants;
import nl.hu.bep2.casino.blackjack.presentation.dto.BetParam;
import nl.hu.bep2.casino.blackjack.presentation.dto.GameResponse;
import nl.hu.bep2.casino.security.domain.UserProfile;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/game")
public class BlackJackController {

    private final GameService gameService;

    private final StandService standService;
    private final HitService hitService;
    private final GiveUpService giveUpService;
    private final DoubleUpService doubleUpService;

    public BlackJackController(GameService gameService,
                               StandService standService,
                               HitService hitService,
                               GiveUpService giveUpService,
                               DoubleUpService doubleUpService
    ) {
        this.gameService = gameService;
        this.standService = standService;
        this.hitService = hitService;
        this.giveUpService = giveUpService;
        this.doubleUpService = doubleUpService;
    }

    @PostMapping("")
    public GameResponse newGame(Authentication authentication, @Validated @RequestBody BetParam param) {
        UserProfile userProfile = (UserProfile) authentication.getPrincipal();
        Object responseData = gameService.newGame(userProfile.getUsername(), param.chips);
        return new GameResponse("success", Constants.NEW_GAME, responseData);
    }

    @RequestMapping("/stand")
    public GameResponse stand(Authentication authentication) {
        UserProfile userProfile = (UserProfile) authentication.getPrincipal();
        Object responseData = standService.stand(userProfile.getUsername());
        return new GameResponse("success", "Stand", responseData);
    }

    @RequestMapping("/hit")
    public GameResponse hit(Authentication authentication) {
        UserProfile userProfile = (UserProfile) authentication.getPrincipal();
        Object responseData = hitService.hit(userProfile.getUsername());
        return new GameResponse("success", "Hit", responseData);
    }

    @RequestMapping("/giveup")
    public GameResponse giveUp(Authentication authentication) {
        UserProfile userProfile = (UserProfile) authentication.getPrincipal();
        Object responseData = giveUpService.giveUp(userProfile.getUsername());
        return new GameResponse("success", "Give Up", responseData);
    }

    @RequestMapping("/doubleup")
    public GameResponse doubleUp(Authentication authentication) {
        UserProfile userProfile = (UserProfile) authentication.getPrincipal();
        Object responseData = doubleUpService.doubleUp(userProfile.getUsername());
        return new GameResponse("success", "Double Up", responseData);
    }

    @GetMapping("/current-game")
    public GameResponse getCurrentGameStatus(Authentication authentication) {
        UserProfile userProfile = (UserProfile) authentication.getPrincipal();
        Object responseData = gameService.getCurrentGameStatus(userProfile.getUsername());
        return new GameResponse("success", "Get Active Game", responseData);
    }
}